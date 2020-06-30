<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<title>触言 - ${listType}</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script type="text/x-mathjax-config">
			MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
		</script>
		<script type="text/javascript"
			src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
		</script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/dtl/list.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div id="header"></div>
			<div class="row" style="padding-top: 90px;"><hr>
				<div class="col-md-2"></div>
				<div class="col-md-8" id="details"></div>
				<div class="col-md-2"></div>
			</div>
			 <div id="footer" class="col-md-12"></div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	var id = '${id}';
	var uid = '${user.uid}';
	/**后期执行文件*/
	$("#header").load(ctx + "/nav");
	$("#details").load($.format("{0}/amane/part/detail?type={1}&id={2}", ctx, '${templateType}', id), function(response, status, xhr){
		if(xhr.status == 200){
			$.getScript(ctx + "/resources/js/dtl/dtl.js", function(){
				$.getScript(ctx + "/resources/js/default/highlight.js");
				MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
			});
		}
		else{
			$(document.body).load($.format("{0}/amane/part/error/{1}", ctx, xhr.status));
		}
	});
	$("#footer").load(ctx + "/footer");
	</script>
</html>