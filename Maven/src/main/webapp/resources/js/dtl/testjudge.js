const hidInput = "<input type='hidden' name='{0}' value='{1}'/>";

$().ready(function(){
	$(validform());
	if(isJudged){
		//已批改
		$("input").attr('disabled', 'disabled');
		$("button").remove();
	}
	else if(isPpsr){
		$("input,button").remove();
	}
	else{
		//观察者为改题者,且尚未批改
		/**检测表单*/
		$("#submit_judge").click(function(){
			if(validform().form()){
				$(this).attr('disabled', 'disabled');
				//add something
				let sum = 0;
				$("#tform").find("input.text-success").each(function(k, v){
					sum += Number($(this).val());
					$(this).attr('name', function(kk, vv){return eval(vv) + $.numberFormat(k + 1, 2);});
				});
				//$("#tform").append($.format(hidInput, "mark", sum)).append($.format(hidInput, "juid", juid));
				//提交表单
				let formObj = JSON.stringify($("#tform").serializeObject());
				$.post(ctx + "/amane/question/submitJudge", {form : formObj, juid : juid}, function(s, xhr){
					if(s.success == true && s.data.state == 1){
						//console.log(s);
						alert('试题分数更新成功！');
					}
					else{
						alert('交卷失败！失败原因:' + s.msg);
					}
				});
				let markObj = {id : jid,uid : juid, marks : sum};
				$.post(ctx + "/amane/test/tester/submitMark", markObj, function(s, xhr){
					if(s.success == true && s.data.state == 1){
						//console.log(s);
						alert('试卷分数更新成功！');
						$(document.body).fadeOut("slow");
					}
					else{
						alert('交卷失败！失败原因:' + s.msg);
					}
				})
			}
		});
		$("#auto_judge").click(function(){
			$("p").each(function(){
				let v = $(this).children("span").find("input").val();
				let v_ans = v.substring(0, v.indexOf('<'));
				let v_key = v.substring(v.indexOf("：") + 1, v.indexOf(">"));
				$(this).find("input:first").val((v_ans == v_key ? 4 : 0) + "分");
			});
		});
	}
})

function validform(){
  /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/ 
  return $("#tform").validate({
		onkeyup:false,
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
			jid : {
				required:true,
				digits:true,
				min : 0,
				max : 4
			}
		},
		messages : {
			jid : {
				required:'请输入分数',
				digits:'分数必须为整数',
				min : '分数必须不小于0',
				max : '分数必须不大于4'
			}
		}
	});
}
