//用于存储表格行与标识符的映射
//let gmap = new Map();
//gmap.set("210", "test").set("220", "question");
//+ gmap.get(findKey($.parseJSON(sessionStorage['nav']), $.value("opus_papa"))) + 
var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/part/creation/step1", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		//console.log(s);
		if(s.success == true && s.data.state == 1){
			window.location.replace(ctx + '/creation/infos');
		}
	},
	error : function(xhr, status, err) {
		$("#nextstep").removeAttr('disabled');
		$("#fail").css('display', 'inline');
		$("#fail").text("提交失败!失败代码：" + xhr.status);
	}
// 成功提交后，重置表单填写内容
};

$().ready(function() {
	/**高亮显示*/
	let obj = {};
	if(sessionStorage['nav'] == undefined){
		$.post(ctx + "/amane/part/getNav", function(s){
			sessionStorage['nav'] = JSON.stringify(s.data.data);
			obj = s.data.data;
		});
	}
	else{
		obj = $.parseJSON(sessionStorage['nav']);
	}
	selectify(obj);
	$("#subtitle").text(subtitle);
	$("#cform").change(function(){
		$.draft("cform", 1);
	});
	$("#read_draft").click(function(){
		$.redraft("cform", 1, null);
	})
	/**检测表单*/
	$("#cform").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#nextstep").attr('disabled', 'disabled');
			/**根据表单内容手动添加部分项*/
			$(form).append("<input type='hidden' name='kind' value='" + $.value("opus_child") + "'>");
			$(form).append("<input type='hidden' name='ppsr' value='" + uname + "'>");
			$(form).append("<input type='hidden' name='creationType' value='" + findKey(obj, $.value("opus_papa")) + "'>");
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
			opus : {
				min : 1
			},
			author_checked : {
				required : true,
			}
		},
		messages : {
			opus : {
				min : "请选择一个选项"
			},
			author_checked : {
				required : "请确认作者信息正确",
			}
		}
	});
	
});

/**BUG：设置session存储的话，会出现creation的覆盖*/
function selectify(obj){
	let i = 0;
	//console.log(obj);
	//第一步,添加爷爷列表
	while(obj[100 * (++i)] != undefined){
		$("#opus_grandpa").append("<option value='" + (i) + "'>" + obj[100 * i] + "</option>")
	}
	//第二步,设置父亲
	$('#opus_grandpa').change(function(){
		$.chain(obj, 0, "opus_grandpa", "opus_papa", 100, 10);
	});
	//第三步,设置孩子
	$('#opus_papa').change(function(){
		$.chain(obj, 100 * $("#opus_grandpa").val(), "opus_papa", "opus_child", 10, 1);
	});
}