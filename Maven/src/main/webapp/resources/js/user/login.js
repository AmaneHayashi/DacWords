/**登录*/
var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/user/login", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		//console.log(s);
		if(s.success == true && s.data.state == 1){
			$("#succ").css('display', 'inline')
			setTimeout(function() {
				window.location.replace(ctx + '/dorest');
			}, 3000);
		}
		else{
			$("#login").removeAttr('disabled');
			$("#fail").css('display', 'inline');
			$("#fail").text("提交失败!失败原因：" + s.msg);
		}
	},
	error : function(xhr, status, err) {
		$("#login").removeAttr('disabled');
		$("#fail").css('display', 'inline');
		$("#fail").text("提交失败!失败代码" + xhr.status);
	},
	clearForm : true, // 成功提交后，清除表单填写内容
	resetForm : true
// 成功提交后，重置表单填写内容
};

/***/
$().ready(function() {
	$.post(ctx + "/amane/user/test", function(s){
		if(s.success == true && s.data.state == 1){
			//console.log('welcome to dacwords!');
		}
	});
	/**检测表单*/
	$("#loginForm").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#login").attr('disabled', 'disabled');
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
				maxlength : 6
			},
			pswd : {
				required : true,
				minlength : 8,
				maxlength : 12
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
			}
		}
	});
});