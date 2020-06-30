/**注册*/
var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/user/register", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		//console.log(s);
		if(s.success == true && s.data.state == 1){
			$("#succ").css('display', 'inline');
			setTimeout(function() {
				window.location.href = ctx + '/dorest';
			}, 3000);
		}
		else{
			$("#regis").removeAttr('disabled');
			$("#fail").css('display', 'inline');
			$("#fail").text("提交失败!失败信息：" + s.msg);
		}
	},
	error : function(xhr, status, err) {
		$("#regis").removeAttr('disabled');
		$("#fail").css('display', 'inline');
		$("#fail").text("提交失败!失败代码" + xhr.status);
	},
	clearForm : true, // 成功提交后，清除表单填写内容
	resetForm : true
// 成功提交后，重置表单填写内容
};

/**重复用户名*/
$.validator.addMethod("repeatUsername", function(value, element, params) {
	var deferred = $.Deferred();//创建一个延迟对象
	$.ajax({
		type : "POST",
		url : ctx + "/amane/user/ckUid",
		async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
		data : {username : value},
		dataType : "json",
		success : function(s) {
			//console.log(s);
			if(s.success == true && s.data.state == 1){
				deferred.resolve();
			}
			else{
				deferred.reject();
			}
		},
		error : function(xhr){
			deferred.reject();
		}
	});
	//deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
	return deferred.state() == "resolved" ? true : false;
}, "用户名已存在");

/***/
$().ready(function() {
	/**获得ID*/
	$.ajax({
		type : "POST",
		url : ctx + "/amane/user/getUid",
		dataType : "json",
		success : function(s) {
			if(s.success == true && s.data.state == 1){
				$("#uid").val(s.data.data);
			}
			else{
				$("#uid").val("ID获取错误，" + s.msg);
				$("#uid").css('color', '#FFC0CB');
			}
		},
		error : function(xhr){
			$("#uid").val("ID获取错误，错误代码" + xhr.status);
			$("#uid").css('color', '#FFC0CB');
		}
	});
	
	/**检测表单*/
	$("#registerForm").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#regis").attr('disabled', 'disabled');
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
			name : {
				required : true,
				maxlength : 6,
				repeatUsername : true,
			},
			pswd : {
				required : true,
				minlength : 8,
				maxlength : 12
			},
			confirm_pswd : {
				required : true,
				minlength : 8,
				maxlength : 12,
				equalTo : "#pswd"
			}
		},
		messages : {
			name : {
				required : "[请填写昵称]",
				maxlength : "[昵称的长度不多于6位]",
			},
			pswd : {
				required : "[请填写密码]",
				minlength : "[密码的长度不少于8位]",
				maxlength : "[密码的长度不多于12位]"
			},
			confirm_pswd : {
				required : "[请再次输入密码]",
				minlength : "[密码的长度不少于8位]",
				maxlength : "[密码的长度不多于12位]",
				equalTo : "[两次输入密码需一致]"
			}
		}
	});
});