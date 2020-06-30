$().ready(function() {
	//field与subject的联动菜单
	selectify(qtMap);
	//ppstime的修改
	$("#edit_ppstime").click(function(){
		$("#c-ppstime").val(new Date().Format("yyyy-MM-ddThh:mm:ss"));
		$("#c-ppstime").change();
	});
	/**检测表单*/
	$("#cform").validate({
		onkeyup:false,
		submitHandler : function(form) {
			$("#nextstep").attr('disabled', 'disabled');
			/**根据表单内容手动添加部分项*/
			$.o2iForm("cform");
			//console.log($(form));
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
			duration : {
				required:true,
				digits:true,
				min:30,
				max:75
			},
			subject : {
				min:1
			},
			ppstime : {
				required:true,
				date:true /**此处有问题,需要后期更改*/
			}
		},
		messages : {
			field : {
				min : '请选择领域'
			},
			duration : {
				required:'请输入整数',
				digits:'请输入正确的整数',
				min:'考试时长不能少于30分钟',
				max:'考试时长不能多于75分钟'
			},
			subject : {
				min:'请选择学科'
			},
			ppstime : {
				required:'请输入日期',
				date:'请输入格式正确的日期'
			}
		}
	});
});