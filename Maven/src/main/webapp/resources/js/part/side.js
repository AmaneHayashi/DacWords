var s = "<a class='btn btn-secondary rounded-16' href='{0}{1}' id='{2}'>{2}</a>";

$().ready(function() {
	
	let nav = $.parseJSON(sessionStorage['nav']);
	let key = findKey(nav, listType);///113
	let pkey = parseInt(Number(key) / 10) * 10;//110
	/**显示左侧按钮组，规定格式*/
	let i = 1;
	let str = "";
	while(nav[pkey + i] != undefined){
		str += $.format(s, document.URL.substring(0, document.URL.indexOf("=") + 1), encodeURI(encodeURI(nav[pkey + i])), nav[pkey + i]);
		i++;
	}

	////console.log(str);
	$("#button-group").append(str);
	$("#" + nav[key]).addClass('btn-outline-secondary');
	$("#" + nav[key]).removeClass('btn-secondary');
	$("#" + nav[key]).addClass('btn-active');
	/**异步post得到card内容*/
});
