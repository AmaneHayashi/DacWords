var intervalObj;
var restSecond = 3;
var testSecond = tester['duration'] * 60;
var initTestSecond = tester['duration'] * 60 + 8;
const DEFALUT_TEXT = '&nbsp;&nbsp;GET STARTED&nbsp;&nbsp;';

var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/question/submitTest", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		window.clearInterval(intervalObj);
		if(s.success == true && s.data.state == 1){
			//console.log(s);
			alert('交卷成功！');
			$(document.body).fadeOut("slow");
		}
		else{
			alert('交卷失败！失败原因:' + s.msg);
		}
	},
	error : function(xhr, status, err) {
		alert('交卷失败！失败代码' + xhr.status);
	}
// 成功提交后，重置表单填写内容
};

$().ready(function(){
	$("input").attr('maxlength', '4');
	/**设置考试开始FLAG*/
	$.post(ctx + "/amane/test/tester/init?id=" + tester['id'], function(s){
		if(s.success == true && s.data.state == 1){
			let websocketHeartbeatJs;
		//	let windowResizedTimes = 0;
			intervalObj = window.setInterval(setRestRemainTime, 1000); //间隔函数，1秒执行
			loadButtonGroup();
			/**开始考试**/
			$("#start-test").click(function(){
				$("#pre-test").fadeOut("slow");
				requestFullScreen(document.documentElement);
				setTimeout(function(){
					//开始显示
					$("#test").fadeIn("slow");
					$("#sider").fadeIn("slow");
					websocketHeartbeatJs = new WebsocketHeartbeatJs({
					    url: 'ws://www.amanehayashi.top/websocket',
					    pingTimeout: 8000,
					    pongTimeout: 4000,
					    repeatLimit: 5
					});		
				    websocketHeartbeatJs.onopen = function () {
				    //	//console.log('connect success');
				        websocketHeartbeatJs.send(tester['duration']);
				        intervalObj = window.setInterval(setTestRemainTime, 1000); //间隔函数，1秒执行
				    }
				    websocketHeartbeatJs.onmessage = function (e) {
				    //	//console.log(e.data);
				    	if(isNaN(e.data)){
				    		//console.log("e.data:" + e.data);
				    		//返回为字母
				    		if(e.data.indexOf('TIMEOUT') > -1){
				    			websocketHeartbeatJs.close();
				    			$("#tform").ajaxSubmit(options);
				    		}else{
				    			testSecond = (initTestSecond -= 8);
				    		}
				    	}
				    	else{
				    		initTestSecond = e.data / 1000;
				    	}
				    }
				    websocketHeartbeatJs.onreconnect = function () {
				    //	//console.log('reconnecting');
				    	websocketHeartbeatJs.close();
				    }
					/**时间校准*/
					$("#time_reload").click(function(){
						$(this).attr('disabled', 'disabled');
						websocketHeartbeatJs.send(testSecond);
						setTimeout(function(){$("#time_reload").removeAttr('disabled');}, 30 * 1000);
					});
				}, 3000);
			});
			/**公式重置*/
			$("#math_reload").click(function(){
				$(this).attr('disabled', 'disabled');
				$.getScript("http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML");
				MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
				setTimeout(function(){$("#math_reload").removeAttr('disabled');}, 10 * 60 * 1000);
			});
			/**提交试卷*/
			$("#submit_test").click(function(){
				$("#tform").ajaxSubmit(options);
			});
		/*	$(window).resize(function(){
				windowResizedTimes ++;
				if(windowResizedTimes & 1 == 0){
					//此时有恶意
				}
			});*/
		}
		else{
			$("button").attr('disabled', 'disabled');
			alert('你已参加过本场考试');
		}
	});
});

function loadButtonGroup(){
	let strleft = "<div id='q-group-left' class='btn-group-vertical'>";
	let strright = "<div id='q-group-right' class='btn-group-vertical'>";
	let nums = tester['nums'];
	let OFFSET = 105;
	let btnstr = "<button type='button' class='btn btn-sm btn-outline-primary rounded-circle' style='margin-bottom:5px;' onclick=goDiv('q{0}',{1})>{0}</button>";
	let clickFunc = ""
	for(let i = 1; i <= parseInt(nums / 2); i ++){
		strleft += $.format(btnstr, $.numberFormat(i, 2), 105);
		strright += $.format(btnstr, $.numberFormat(i + parseInt(nums / 2), 2), 105);
	}
	strleft += "</div>";
	strright += "</div>";
	//console.log((strleft));
	$("#sider").append(strleft + strright);
}

function setRestRemainTime() {
	if (restSecond > 0) {
		restSecond -- ;
		$("#start-span").html(DEFALUT_TEXT + $.format("({0} seconds after)", restSecond));
	} 
	else {//剩余时间小于或等于0的时候，就停止间隔函数
		window.clearInterval(intervalObj);
		//这里可以添加倒计时时间为0后需要执行的事件
		$("#start-test").removeAttr('disabled');
		$("#start-span").html(DEFALUT_TEXT);
	}
}

function setTestRemainTime() {
	if (testSecond > 0) {
		testSecond -- ;
		$("#clock_test").text(getClock(testSecond));
		if(testSecond == 600){
			$("#clock_test").removeClass("btn-outline-success");
			$("#clock_test").addClass("btn-outline-warning");
		}
		else if(testSecond == 300){
			$("#clock_test").removeClass("btn-outline-warning");
			$("#clock_test").addClass("btn-outline-danger");
		}
	}
	else {
		//这里可以添加倒计时时间为0后需要执行的事件
		//$("#time_reload").click();
	}
}