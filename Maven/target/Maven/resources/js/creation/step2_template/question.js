$().ready(function() {
	//field与subject的联动菜单
	selectify(qtMap);
	//ppstime的修改
	/**检测表单*/
	$("#cform").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#nextstep").attr('disabled', 'disabled');
			/**根据表单内容手动添加部分项*/
			$.o2iForm("cform");
			$(form).append("<input type='hidden' name='ppstime' id='c-ppstime' value='" + new Date().Format("yyyy-MM-dd hh:mm:ss") + "'>");
			$(form).append("<input type='hidden' name='name' value='" + generateName() + "'>");
			$(form).append("<input type='hidden' name='id' value='" + generateID() + "'>");
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
			field : {
				min : 1
			},
			subject : {
				min:1
			}
		},
		messages : {
			field : {
				min : '请选择领域'
			},
			subject : {
				min:'请选择学科'
			}
		}
	});
});