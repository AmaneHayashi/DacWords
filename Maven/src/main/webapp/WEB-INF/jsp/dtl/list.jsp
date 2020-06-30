<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<title>触言 - ${type}</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/dtl/list.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div id="header"></div>
			<div class="row" style="padding-top: 90px;"><hr>
				<div class="col-md-1"></div>
				<div id="side" class="col-md-3"></div>
				<div class="col-md-7">
					<div id="lists"></div>
					<ul class="pagination float-right">
						<li class="page-item" id="lastpage"><a class="page-link" href="javascript:void(0);"><</a></li>
						<li class="page-item" id="nextpage"><a class="page-link" href="javascript:void(0);">></a></li>
					</ul>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div id="footer" class="static-bottom"></div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	var listType = '${type}';
	var page = '${page}';
	var templateType = '${templateType}'.toLocaleLowerCase();
	
	/**后期执行文件*/
	$("#header").load(ctx + "/nav");
	$("#lists").load($.format("{0}/amane/part/list?type={1}&kind={2}&uid={3}&page={4}", ctx, templateType, listType, '${user.uid}', page), function(response, status, xhr){
		if(xhr.status == 200){
			$.getScript(ctx + "/resources/js/dtl/list.js", function(){
				$.getScript(ctx + "/resources/js/default/highlight.js");
			});
		}
		else{
			$(document.body).load($.format("{0}/amane/part/error/{1}", ctx, xhr.status));
		}
	});
	$("#footer").load(ctx + "/footer");
	$("#side").load(ctx + "/sidebar?listType=" + listType);
	</script>
</html>