<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title>触言 - 考试</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="${ctx}/resources/js/default/jquery.form.js"></script>
		<!-- 心跳检测 -->
		<script src="${ctx}/resources/js/default/heartbeat-master2.js"></script>
		<script type="text/x-mathjax-config">
			MathJax.Hub.Config({
				tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]},
 				"HTML-CSS": { linebreaks: { automatic: true } },
         		SVG: { linebreaks: { automatic: true } }
			});
		</script>
		<script type="text/javascript"
			src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
		</script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/dtl/tester.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div class="row fixed-top bg-white" style="padding-top:30px;">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<h3 class="text-center"><span></span>
					<button id="clock_test" class="btn btn-outline-success rounded-16 float-left">00:00</button>
					<button id="time_reload" class="btn btn-outline-info rounded-16 float-left" style="margin-left:45px;">时间校准</button>
					<button id="submit_test" class="btn btn-outline-danger rounded-16 float-right">提交</button>
					<button id="math_reload" class="btn btn-outline-info rounded-16 float-right" style="margin-right:45px;">公式重载</button></h3>
					
					<hr class="hr0"/>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10" style="padding-top:110px;padding-bottom:40px;">	
					<div id="pre-test">
						<div class="alert alert-warning alert-dismissable">
							<h4 class="text-center">————————&nbsp;&nbsp;&nbsp;&nbsp;WARNING&nbsp;&nbsp;&nbsp;&nbsp;————————</h4> 
							<p>To ensure that the test can be loaded successfully, we prepare a half-minute free for you before the test. <br/>
							Now you can wait for loading the test paper or browse the words below.
							But <strong>be cautious to turn-off the full-screen since that you are in the test now</strong> .</p>
						</div>
						<div class="alert alert-success alert-dismissable">
							<h4 class="text-center">————————&nbsp;&nbsp;&nbsp;&nbsp;A QUICK START&nbsp;&nbsp;&nbsp;&nbsp;————————</h4> 
							<ul><i>Before this test, I hope you can again read the principles below and obey them.</i>
								<li>1</li>
								<li>2</li>
								<li>3</li>
							</ul>
						</div>
						<div class="alert alert-info alert-dismissable">
							<h4 class="text-center">————————&nbsp;&nbsp;&nbsp;&nbsp;AUTHOR MESSAGE&nbsp;&nbsp;&nbsp;&nbsp;————————</h4> 					
							<blockquote class="blockquote">
								<p><i class="mb-1 text-center">
									Hello, Haitang. It is my great honour to see you participating in my test. This is a challenging test, preparing for those
									who are both itelligent and brave. Hoping you will do your best effort.
								</i></p>
								<footer class="blockquote-footer float-right">
									<cite>Amane Hayashi</cite>
								</footer>
							</blockquote>
							<br/>
						</div>
						<div class="float-right">
							<button id="start-test" class="btn btn-lg btn-outline-success rounded-16 buttonNext" disabled="disabled"><span id="start-span">&nbsp;&nbsp;GET STARTED&nbsp;&nbsp;</span></button>
						</div>
					</div>
					<div id="test" style="display:none"></div>
				</div>
				<div class="col-md-1" id="sider" style="display:none"></div>
			</div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	/**后期执行文件*/
	var tester = $.parseJSON('${test}');
	$("h3").find("span").text(tester['id']);
	$("#test").load(ctx + "/amane/question/tester?id=" + tester['id']);
	$.getScript(ctx + "/resources/js/dtl/tester.js");
	//MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
	</script>
	
</html>