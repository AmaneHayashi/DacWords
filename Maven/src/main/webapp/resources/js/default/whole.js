function findKey (obj,value, compare = (a, b) => a === b) {
	return Object.keys(obj).find(k => compare(obj[k], value));
}

$().ready(function(){
	$("input").attr("autocomplete", "off");
});

window.onload = function (){   
	document.body.onkeydown=function(event){
		if(event.keyCode==13){
	    	event.keyCod=0; return false;
		}
	}
};

String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$.format = function (source, params) {
    if (arguments.length == 1)
        return function () {
            var args = $.makeArray(arguments);
            args.unshift(source);
            return $.format.apply(this, args);
        };
    if (arguments.length > 2 && params.constructor != Array) {
        params = $.makeArray(arguments).slice(1);
    }
    if (params.constructor != Array) {
        params = [params];
    }
    $.each(params, function (i, n) {
        source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
    });
    return source;
};

$.chain = function (obj, defaultIncrement, papaId, childId, papaIncrement, childIncrement) {
	$("#" + childId).val(0);
	$("#" + childId).change();
	while($("#" + childId + " option:last").val() != 0){
		$("#" + childId +" option:last").remove();
	}
	child = new Array("");
	if($("#" + papaId).val() != 0){
		// 开始分割
		let i = 0;
		let ii = $("#" + papaId).val() * papaIncrement + defaultIncrement;// 200
		while(obj[ii + childIncrement * (++i)] != undefined){
			$("#" + childId).append("<option value='" + (i) + "'>" + obj[ii + childIncrement * i] + "</option>")
		}
	}
	return child;
}

/** 将表单写入草稿(未加密) */
$.draft = function(formId, suffix){
	let obj = {};
	$("#" + formId).find("select,input,textarea").each(function(){
	//	console.log($(this).attr('id'));
		if($(this).attr('type') == "checkbox"){
			obj[$(this).attr('id')] = $(this).is(':checked');
		}
		else if($(this).attr('type') == "file"){
			let v = $(this).val();
			obj[$(this).attr('id')] = v.substr(v.indexOf(".") + 1);
		}
		else{
			obj[$(this).attr('id')] = $(this).val();
		}
	});
	$("#draft_saving").css('display', 'inline');
	$("#draft_saving").text('草稿保存中...');
	sessionStorage[formId + suffix] = JSON.stringify(obj);
	setTimeout(function(){$("#draft_saving").text('草稿已保存');}, 500);
}

$.draftTable = function(tableId){
	var table = $("#" + tableId)[0];
	var MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
	var MutationObserverConfig={
	    childList: true,
	    subtree: true,
	    characterData: true
	};
	var observer=new MutationObserver(function(mutations){
	//	console.log('a');
		sessionStorage[tableId] = $("#" + tableId).parent().html();
	});
	observer.observe(table, MutationObserverConfig);
}

/** 将草稿写入表单 */
$.redraft = function(formId, suffix, imgFormData){
	var obj = sessionStorage[formId + suffix];
	if(obj != undefined){
		obj = $.parseJSON(obj);
		$("#" + formId).find("input,textarea,select").each(function(){
			if($(this).attr('type') == "checkbox"){
				$(this).prop("checked", obj[$(this).attr('id')]);
			}
			else if($(this).attr('type') == "file"){
				$(this)[0].files[0] = imgFormData.get(suffix);
			}
			else{
				$(this).val(obj[$(this).attr('id')]);
				if($(this)[0].tagName == "SELECT"){
					$(this).change();
				}
			}
		});
	}
}

$.redraftTable = function(tableId){
	$("#" + tableId).parent().html(sessionStorage[tableId]);
}

$.value = function(k){
	var node = $("#" + k);
	if('INPUT+TEXTAREA'.indexOf(node[0].tagName) > -1){
		return node.val();
	}
	else if(node[0].tagName == 'SELECT'){
		return node.find("option:selected").text();
	}
	else{
		return node.text();
	}
}

$.o2iForm = function(formId){
	let appender = "<input type='hidden' name='{0}' value='{1}'>";
	$("#" + formId).find("select").each(function(){
		//console.log($(this).attr('name'));
		$("#" + formId).append($.format(appender, $(this).attr('name'), $.value($(this).attr('id'))));
		$(this).removeAttr('name');
	});
}

$.o2iObject = function(obj){
	for(let i in obj){
		let node = $("#" + i);
		//console.log(node);
		//console.log(node[0].tagName);
		if(node[0] != undefined && node[0].tagName == 'SELECT'){
			//console.log(obj[i]);
			obj[i] = node.find("option").eq(obj[i]).text();
		}
	}
	return obj;
}

$.Obj2Formdata = function(obj){
	var formData = new FormData();
	Object.keys(obj).forEach((key) => {
		formData.append(key, encodeURI(obj[key]));
	});
	return formData;
}

$.Formdata2Obj = function(formData){
	var obj = {};
	formData.forEach((value, key) => obj[key] = value);
	return obj;
}

$.numberFormat = function (num, length) {
    return (Array(length).join('0') + num).slice(-length);
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

$.json2url = function (object){
	var params = Object.keys(object).map(function (key) {
        // body...
        return encodeURI(encodeURI(key)) + "=" + encodeURI(encodeURI(object[key]));
    }).join("&");
	return params;
}

// 生成UUID
function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/* 对节点的值进行自加自减操作 */
function selfAdd(id, t){
	$("#" + id).text(function(n, v){
		return parseInt(v) + parseInt(t);
	});
}

function requestFullScreen(element) {
    var docElm = element;
    if (docElm.requestFullscreen) {
        docElm.requestFullscreen();
    }else if (docElm.msRequestFullscreen) {
        docElm.msRequestFullscreen();
    }else if (docElm.mozRequestFullScreen) {
        docElm.mozRequestFullScreen();
    }else if (docElm.webkitRequestFullScreen) {
        docElm.webkitRequestFullScreen();
	}
} 

function getClock(sec){
	return $.format("{0}:{1}", $.numberFormat(parseInt(sec / 60), 2), $.numberFormat(parseInt(sec % 60), 2));
}

function goDiv(div, offset) {
	var a = $("#" + div).offset().top - offset;
	$("html,body").animate({scrollTop:a}, 'slow'); 
}

