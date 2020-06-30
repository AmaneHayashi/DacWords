/***/
var s1 = "<li class='nav-item'><a class='nav-link dropdown-toggle f-nav-a' id='{0}'>{0}</a><div>";
var s2 = "<a class='dropdown-item disabled-item' id='{0}'>{0}</a>";
var s3 = "<a class='dropdown-item' href='javascript:void(0)' name='item'>{0}</a>";
var s4 = "<div class='dropdown-divider'></div>";
var s5 = "</div></li>";

$().ready(function() {
	/**高亮显示*/
	if(sessionStorage['nav'] == undefined){
		$.post(ctx + "/amane/part/getNav", function(s){
			if(s.success == true && s.data.state == 1){
				//console.log(s.data.data);
				sessionStorage['nav'] = JSON.stringify(s.data.data);
				let str = navify(s.data.data);//100,110
				$("#li_intro").before(str);
			}
		});
	}
	else{
		$("#li_intro").before(navify($.parseJSON(sessionStorage['nav'])));
	}

	/**搜索框**/
	$("#search").click(function(){
		$(this).animate({width:'18em'}, "slow");
	});

	$("#search").blur(function(){
		if($(this).val().length == 0){
			$(this).animate({width:'10em'}, "slow");
		}
	});
	/*
	$("#scope").click(function(){
		console.log('a');
		var search = $("#search").val().trim();
		if(search.length != 0){
			window.location.href = ctx + "/search?content=" + encodeURI(encodeURI(search));
		}
	});
	*/
	$("#create").click(function(){
		if(creator.length == 0){
			window.open(ctx + "/creation/warmup");
		}
		else{
			$(this).attr('data-target', '#createModal');
		}
	});

	$("#ul_menu").on("click", "a[name='item']", function(){
		var text = $(this).text();
		$.post(ctx + "/amane/part/getType", {listType: text}, function(s){
			if(s.success == true && s.data.state == 1){
				let t = s.data.data;
				window.location.href = $.format('{0}/dorest/list/{1}?type={2}&page=1', ctx, t, encodeURI(encodeURI(text)));
			}
		});
	});

	$("#logout").click(function(){
		//清空sessionStorage
		sessionStorage.clear();
		//清空cookies
		if(document.cookie.length > 0){
			for(let key of document.cookie.match(new RegExp(/[^ =;]+(?=\=)/g))){
				$.removeCookie(key);
			}
		}
		window.location.href = ctx + '/logout';
	});
});

function navify(data){
	var str = "";
	let pp = 100;
	let p = 10;
	let g = 1;
	while(data[pp] != undefined){
		str += $.format(s1, data[pp]);
		while(data[pp + p] != undefined){
			str += $.format(s2, data[pp + p]);
			while(data[pp + p + g] != undefined){
				str += $.format(s3, data[pp + p + g]);
				g += 1;
			}
			p += 10;
			g = 1;
			str += data[pp + p] == undefined ? "" : s4;
		}
		str += s5;
		pp += 100;
		p = 10;
	}
	return str;
}