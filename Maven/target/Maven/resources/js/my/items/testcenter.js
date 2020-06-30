//mark Depart 成绩科
//grade Depart 阅卷科
//Sugg Depart 诉讼科

function load(id){
	$.post(ctx + "/amane/test/testcenter/" + id, {name : username}, function(s){
		//返回的是一个列表
		if(s.success == true && s.data.state == 1){
			for(let f of s.data.data){
				let str = $.format(eval(id), 
						f['uname'], 
						f['answer_time'], 
						$.format(tc_addr, document.URL, f['id'], f['uid'], f['mark'] > 0, username == f['ppsr']),
						$.format(tc_intro, f['tname'], f['id'], 
								f['mark'] > 0 ? 
								$.format(tc_grade, "success", $.format("{0}/100", f['mark'] / f['fullmark'] * 100)) :
								$.format(tc_grade, "danger", "尚未评分")));
				$("div.item").append(str);
			}
		}
		else if(s.success == false && s.data == null){
			$("div.item").append($.format(noActivity, id));
		}
	});
}