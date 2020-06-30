$().ready(function() {
	
	let nav = $.parseJSON(sessionStorage['nav']);
	let key = findKey(nav, listType);///113
	let ppkey = parseInt(Number(key) / 100) * 100;//100
	
	let hasMore = JSON.parse(eval("hasNext"));
	/*高亮*/
	let hl = nav[ppkey];
	sessionStorage['highlight'] = hl;
	//上一页
	let url = document.URL.substr(0, document.URL.indexOf("page=") + "page=".length);
	$("#lastpage").find("a").click(function(){
		if(page != 1){
			window.location.href = url.concat(--page);
		}
	});
	//下一页
	$("#nextpage").find("a").click(function(){
		if(hasMore){
			window.location.href = url.concat(++page);
		}
	});
});
