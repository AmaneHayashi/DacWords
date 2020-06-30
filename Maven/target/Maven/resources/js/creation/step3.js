const _EDIT = "_edit@";
const _DELETE = "_delete@";
const _SAVE = "_save@";
const _PREVIEW = "_preview@";

const ORIGINAL_CLASS = "table-info";
const UPLOADED_CLASS = "table-success";
const ERRXHR_CLASS = "table-danger";
const ERRMSG_CLASS = "table-warning";

//用于存储表格行与标识符的映射
var gmap = new Map();
gmap.set("选择题", "1").set("填空题", "2");

var uuid = guid();

var imgFormData = new FormData();

var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/" + type + "/creation/step3", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		if(s.success == true){
			alert('创作成功！');
			$(document.body).fadeOut("slow");
			/*清除SESSIONSTORAGE*/
			sessionStorage.clear();
			setTimeout(()=>{window.location.href = ctx + '/dorest';}, 3000);
		}
		else{
			$("#nextstep").removeAttr('disabled');
			$("#fail").css('display', 'inline');
			$("#fail").text("提交失败!失败原因:" + s.msg);
		}
	},
	error : function(xhr, status, err) {
		$("#nextstep").removeAttr('disabled');
		$("#fail").css('display', 'inline');
		$("#fail").text("提交失败!失败代码" + xhr.status);
	}
// 成功提交后，重置表单填写内容
};

$().ready(function() {
	/*获得ID命名规则*/
	$("#subtitle").text(subtitle);
	//表格的draft
	$("#cform").change(function(){
		$.draft("cform", 3);
	});
	
	$("#read_draft").click(function(){
		$.redraft("cform", 3, null);
		$.redraftTable("form-table");
	});
	/*检测表单*/
	$("#cform").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#nextstep").attr('disabled', 'disabled');
			/*根据表单内容手动添加部分项*/
			$.o2iForm("cform");
			$(form).ajaxSubmit(options);//提交时拦截
		},
		errorElement : 'div',
		errorPlacement : function(error, element) {
			error.addClass('tooltip tooltip-inner rounded-16');
			if (element.is(":radio")) {
				error.appendTo(element.parent().next().next());
			} else if (element.is(":checkbox")) {
				error.appendTo(element.next());
			} else {
				element.after(error);
			}
		},
		rules : {
			nums : {
				required : true
			},
			diff : {
				required:true,
				groundCheck:true
			}
		},
		messages : {
			nums : {
				required : '请输入题目数量'
			},
			diff : {
				required:'请输入试题难度'
			}
		}
	});
	//打开模态框按钮
	$(document).on("click", "#add", function(){
		//生成UUID
		uuid = guid();
		//初始化
		$("#qform")[0].reset();
		$("#saveModal").removeAttr('data-dismiss');
		$("#qform").find("input,select, textarea").change();
	});

	//取消模态框按钮
	$(document).on("click", "button[name='shutdown']", function(){
		if($("#" + uuid)[0] == undefined){
			sessionStorage.removeItem("qform" + uuid);
		}
	});

	//保存模态框按钮
	$(document).on("click", "#saveModal", function(){
		//此时为新建
		setTimeout(function(){
			if($("#" + uuid)[0] == undefined && $("#addModal").is(":hidden")){
				//题号考点题型难度操作
				str = "";
				str += "<tr class='"+ ORIGINAL_CLASS +"' id=" + uuid + ">";
				str += "<td></td>";
				str += "<td class='point'>" + $.value("point") + "</td>";
				str += "<td class='type'>" + $.value("type") + "</td>";
				str += "<td class='diff'>" + $.value("diff") + "</td>";
				str += "<td>"+ getBtnGroups() + "</td></tr>";
				$("#tbody").append(str);
			}
			//此时为更新
			else{
				$("#" + uuid).find(".point").text($.value("point"));
				$("#" + uuid).find(".type").text($.value("type"));
				$("#" + uuid).find(".diff").text($.value("diff"));
			}
			imgFormData.set(uuid, getFile());
		}, 500);
	});
	
	//编辑按钮(动态生成)
	$(document).on("click", ".glyphicon-wrench", function(){
		uuid = $(this).attr('id').replace(_EDIT, "");
		$.redraft("qform", uuid, imgFormData);
	});

	//删除按钮(动态生成)
	$(document).on("click", ".glyphicon-remove", function(){
		//移除sessionStorage内容
		uuid = $(this).attr('id').replace(_DELETE, "");
		//console.log(uuid);
		sessionStorage.removeItem("qform" + uuid);
		sessionStorage.removeItem("obj" + uuid);
		//表格内容调整(题号变化)
		let grandpa = $("#" + uuid);
		while(grandpa.attr('class') != undefined){
			let t = parseInt(grandpa.find("td").first().text());
			grandpa.find("td").first().text(t - 1);
			grandpa = grandpa.next();
		}
		//移除表格内容
		$("#" + uuid).remove();
	})

	//保存按钮(动态生成)
	$(document).on("click", ".glyphicon-ok", function(){
		//防止重复点击
		let th = $(this);
		th.attr('disabled', 'disabled');
		//获得uuid
		uuid = th.attr('id').replace(_SAVE, "");
		//获得传输对象(并存入session)
		let objs = objectify(uuid);
		//成功后变色
		$("#" + uuid).removeClass(ORIGINAL_CLASS).removeClass(ERRMSG_CLASS).addClass(UPLOADED_CLASS);
		//修改题数
		$("#t-" + objs['type']).text(function(k, v){return Number(v) + 1});
		$("#t-0").text(function(k, v){return Number(v) + 1});
		//设置不可编辑不可删除不可保存
		th.siblings(".glyphicon-tasks").css('display','inline');
		th.parent().children().not(".glyphicon-tasks").remove();
		//更改题序
		xchg();
	});
	
	//预览按钮(动态生成)
	$(document).on("click", ".glyphicon-tasks", function(){
		uuid = $(this).attr('id').replace(_PREVIEW, "");
		//对SESSION的值进行处理，方便传输需要
		let objs = objectify(uuid);
		//console.log(objs);
		$("#previewModal").load(ctx + "/amane/question/preview", objs);
	});
	
	//生成难度,生成分数,生成题号
	$(document).on("click", "#correct", function(){
		//创建了至少一题且已经全部提交(绿色且只有一个按钮)
		if(checkTable()){
			//设置不可重复点击
			$(this).attr('disabled', 'disabled');
			//移除兄弟按钮
			$("#correct").siblings("button").remove();
			//生成表格题号
			$("tbody").children().children("td:first-child").text(function(k,v){return k+1});
			//获取起始增量与尾号位数
			let increment = 0, suffix = 0;
			$.ajax({	
				type : 'POST', // 设置表单提交方式
				url : ctx + "/amane/question/getQs", // 设置表单提交URL,默认为表单Form上action的路径
				data : {oid : oid, kind : kind},
				async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
				dataType : 'json', // 返回数据类型
				success : function(s) {
				//	console.log(s);
					if(s.success == true && s.data.state == 1){
						increment = Number(s.data.data['increment']);
						suffix = Number(s.data.data['suffix']);
					}
				}
			});
			//生成实际题号及其对象
			let obj = {};
			$("tbody").find("tr").each(function(){
				obj = $.parseJSON(sessionStorage['obj' + $(this).attr('id')]);
				obj.id = oid + $.numberFormat(Number($(this).children().first().text()) + increment, suffix);
				let f = imgFormData.get($(this).attr('id'));
				imgFormData.set(obj.id, f);
				for(let k of Object.keys(obj)){
					obj[k] = encodeURI(obj[k].replaceAll("\\\\", "\\\\"));
				}
				imgFormData.set($(this).attr('id'), JSON.stringify(obj));
			});
			//开始异步传输
			$.ajax({ 
				type:"POST", 
				url:ctx + "/amane/question/batchInsert", 
				data: imgFormData, 
				async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
				cache : false,//上传文件无需缓存
			    processData: false, // 此处是关键：告诉jQuery不要去处理发送的数据
			    contentType: false, // 此处是关键：告诉jQuery不要去设置Content-Type请求头
				dataType : 'json', // 返回数据类型
				success:function(s){
					//console.log(s);
					if(s.success == true && s.data.state == 1){
						//生成难度
						let m = 0, wd = 0;
						m = 4 * $("tr.table-success").length;
						$("tr.table-success").each(function(){wd += 4 * $(this).children("td.diff").text();});
						$("#c-diff").val(wd / m);
						$("#c-nums").val(m / 4);
					}
					else{
						th.removeAttr('disabled');
						$("#" + uuid).removeClass(ORIGINAL_CLASS).addClass(ERRMSG_CLASS);
					}
				}, 
				error:function(xhr,errorText,errorType){
					th.removeAttr('disabled');
					$("#" + uuid).removeClass(ORIGINAL_CLASS).addClass(ERRMSG_CLASS);
				}
			});
		}
	});
});

/*表格内容*/
$.validator.addMethod("groundCheck", function(value, element, params) {
	return checkTable() && $("#add")[0] == undefined;
}, "请完善表格内容以保证表格信息与输入框信息一致");

function getFile(){
	return $("#pic")[0].files[0] != undefined ? $("#pic")[0].files[0] : new File([''],'a.txt',{
		  type: 'text/plain'
	});
}

//name,Kind,Field,Subject,ppsr,ppstime与考试相同
function xchg(){
	//根据题目类型和题目难度放置(仅对上传的题目有效)
	//插入到第一个蓝色前面(最后一个绿色后面)
	let mmtb = $("#" + uuid);
	let preb = mmtb.prevAll(".table-info").last();//向上最后一个蓝色
	if(preb[0] != undefined){
		preb.before(mmtb.prop("outerHTML"));
		mmtb.remove();
	}
	/*绿色部分的处理不包括KYAN*/
	if($("tr.table-success").children("td").eq(1).text() != '综合'){
		//处理绿色部分
		let mmtg = $("#" + uuid);
		let prev = mmtg.prevAll(".table-success").first();//向上第一个绿色
		//若当前绿色题序比上一绿色题题序小,或难度更低,则交换次序
		while(prev[0] != undefined && (gmap.get(mmtg.find(".type").text()) < gmap.get(prev.find(".type").text()) || 
				mmtg.find(".diff").text() < prev.find(".diff").text())){
			let nowHTML = mmtg.prop("outerHTML");
			mmtg.replaceWith(prev.prop("outerHTML"));
			prev.replaceWith(nowHTML);
			mmtg = $("#" + uuid);
			prev = mmtg.prev();
		};
	}
}

function getBtnGroups(){
	return "<button type='button' class='glyphicon glyphicon-wrench border-0' id='" + _EDIT + uuid + "' data-toggle='modal' data-target='#addModal'></button>" +//编辑
	"<button type='button' class='glyphicon glyphicon-remove border-0' id='" + _DELETE + uuid + "'></button>" +//删除
	"<button type='button' class='glyphicon glyphicon-ok border-0' id='" + _SAVE + uuid + "'></button>" +//保存
	"<button type='button' class='glyphicon glyphicon-tasks border-0' id='" + _PREVIEW + uuid + "'" +
		"data-toggle='modal' data-target='#preModal'></button>" ;//预览
}

function checkTable(){
	return $("tr").length > 1 && $("tr.table-info").length == 0 && $("table").find("button").length == $("tr").length - 1;
}

function objectify(uuid){
	let objs = sessionStorage['obj' + uuid];
	//编辑预览(按钮存在,第一次->有按钮,无定义;第若干次->有按钮,有定义),上传预览(从未预览过->无按钮,无定义;预览过->无按钮,有定义)
	if($("#" + uuid).find("button").length > 1){
		//编辑预览或从未预览过的上传预览
		objs = $.parseJSON(sessionStorage['qform' + uuid]);
		//处理值第一步:将option转化为input
		objs = JSON.stringify($.o2iObject(objs));
		//处理值第二步:移除原有qform
	//	sessionStorage.removeItem('qform' + uuid);
		sessionStorage['obj' + uuid] = objs;
		console.log(objs);
	}
	return $.parseJSON(objs);
}