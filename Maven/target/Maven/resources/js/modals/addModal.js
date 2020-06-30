/**UUID来自外部，需要通过guid()函数实现*/
/**imgFormData来自外部，存储img的信息*/

$().ready(function() {
	$("#saveModal").click(function(){
		$("#qform").submit();
	})
	/**获得ID命名规则*/
	$("#qform").change(function(){
		$.draft("qform", uuid);
	});
	$.draftTable("form-table");
	/**获得图片*/
	$("#pic").change(function(){
	//	console.log(uuid);
		let v1 = $("#pic")[0].files[0];
		let v2 = imgFormData.get(uuid);
		let v = v1 != null ? v1 : v2 != null ? v2 : null;
		if(v != null){
			$("#pic").siblings().first().html(function(k, v){return v + "<b class='text-danger'>(您已上传过图片)</b>"});
			 var reads = new FileReader();
			 reads.readAsDataURL(v);
			 reads.onload = function(e) {
				 $("img.bg-img").attr('src', this.result);
			 };
		}
		else {
			$("img.bg-img").removeAttr('src');
			$("#pic").siblings().first().find("b").remove();
		}
	//	console.log('a');
	});
	/**选择题*/
	//$("div.options").hide();
	$("#type").change(function(){
		if($.value($(this).attr('id')) != '选择题'){
			$("div.options").hide();
		}
		else{
			$("div.options").show();
		}
	})
	$("#addModal").find("input[type='text'], textarea").change(function(){
		let th = $(this);
		if(th.val().length != 0){
			th.siblings("span").text((k, v) => {return $.format("$${0}$$", th.val());});
			MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
		}
		else{
			th.siblings("span").text("");
		}
	})
	selectify(qtMap);
	/**检测表单*/
	$("#qform").validate({
		/**新增功能(输入答案与选择题挂钩+选择题是否必填与选择题挂钩)*/
		submitHandler : function(form) {
		//	//console.log('addModal');
			$("#saveModal").attr('data-dismiss', 'modal');
			
		//	$(form).ajaxSubmit();//提交时拦截
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
			subject : {
				min : 1
			},
			type : {
				min : 1
			},
			point:{
				min : 1
			},
			diff:{
				required:true,
				digits:true,
				min:1,
				max:9
			},
			content:{
				required:true
			},
			optionA:{
				optionRequired : true
			},
			optionB:{
				optionRequired : true
			},
			optionC:{
				optionRequired : true
			},
			optionD:{
				optionRequired : true
			},
			pic:{
				fileFormat : ["jpg,png "],
				fileSize : ["10", "500"]
			},
			key:{
				required:true,
				answerOptions:true
			}
		},
		messages : {
			subject : {
				min : '请选择学科'
			},
			type : {
				min : '请选择题型'
			},
			point:{
				min : '请选择考点'
			},
			diff:{
				required:'请输入难度',
				digits:'请输入整数',
				min:'请输入不小于1的整数',
				max:'请输入不大于9的整数'
			},
			content:{
				required:'请输入题目内容'
			},
			key:{
				required:'请输入答案'
			}
		}
	});
	//$("#type").change();
});

/**重复用户名*/
$.validator.addMethod("optionRequired", function(value, element, params) {
	if($.value($("#type").attr('id')) != '选择题'){
		return true;
	}
	else{
		return value.length == 0 ? false : true;
	}
}, "请填写选项内容");

/**答案与选项挂钩*/
$.validator.addMethod("answerOptions", function(value, element, params) {
	if($.value($("#type").attr('id')) != '选择题'){
		//此时非必须
		return true;
	}
	else{
		return "ABCD".indexOf(value) > -1 ? true : false;
	}
}, "请输入选项");

/**上传文件限定格式.(jpg,.png)***/
$.validator.addMethod("fileFormat", function(value, element, params) {
	return params[0].indexOf(value.substr(value.indexOf(".") + 1))  != -1;
}, "上传的文件必须为指定格式");

/**上传文件限定大小.10~500kb***/
$.validator.addMethod("fileSize", function(value, element, params) {
	return element.files[0] == undefined || 
	(element.files[0].size >= params[0] * 1000 && element.files[0].size <= params[1] * 1000);
}, "上传的文件必须为指定大小");

function selectify(obj){
	//subject 综合 -> all-required
	let key = findKey(obj, subject);
	//console.log(key);
	//d为总体增量,需设置局部增量
	//let d = parseInt(key / 10) * 10 - 10;
	//第一步,添加爷爷列表
	if(subject.indexOf('综合') > -1){//140
		let i = 0;
		while(obj[d + 10 * (++i)] != undefined && obj[d + 10 * i].indexOf('综合') == -1){
			$("#subject").append("<option value='" + (i) + "'>" + obj[d + 10 * i] + "</option>");
		}
	}
	else{
		$("#subject").append("<option value='1'>" + obj[key] + "</option>");
	}
	//第二步,设置父亲
	$('#subject').change(function(){
		$.chain(obj, key - 10, "subject", "point", 10, 1);
	});
}