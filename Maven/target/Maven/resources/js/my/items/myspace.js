//dacwords 文手记
//dorests 题夏试
//activities 动态

function load(id){
	$.post(ctx + "/amane/part/myspace/" + id, {name : username}, function(s){
		//返回的是一个列表
		if(s.success == true && s.data.state == 1){
			for(let f of s.data.data){
			//	console.log(eval(id));
				let str = $.format(eval(id), 
						username,
						f['kind'],
						f['time'], 
						$.format(ms_addr, ctx, f['type'], f['id']),
						$.format(ms_intro, f['name'], f['id']));
				$("div.item").append(str);
			}
		}
		else if(s.success == false && s.data == null){
			$("div.item").append($.format(noActivity, id));
		}
	});
}