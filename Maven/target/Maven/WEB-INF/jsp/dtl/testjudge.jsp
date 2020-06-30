<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title>触言 - 成绩单</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.form.js"></script>
		<script type="text/x-mathjax-config">
			MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
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
					<button id="auto_judge" class="btn btn-outline-info rounded-16 float-left disabled">自动评分</button>
					<button id="submit_judge" class="btn btn-outline-danger rounded-16 float-right disabled">提交评分</button></h3>
					<hr class="hr0"/>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10" style="padding-top:110px;padding-bottom:40px;">	
					<div id="test"></div>
				</div>
				<div class="col-md-1"></div>
			</div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	/**后期执行文件*/
	var jid = '${id}';//$.cookie('judge_id');
	var juid = '${uid}'//$.cookie('judge_uid');
	var isPpsr = '${ppsr}'//$.cookie('ppsr_' + jid);
	var isJudged = '${judged}'//$.cookie('judged_' + jid);
	$("h3").find("span").text(jid);
	$("#test").load($.format("{0}/amane/question/testjudge?uid={1}&id={2}", ctx, juid, jid), function(){
		$.getScript(ctx + "/resources/js/dtl/testjudge.js");
		MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
	});
	</script>
</html>