<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
	<html lang="ch">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/main/dorest.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/part/nav.css" rel="stylesheet" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<nav class="navbar navbar-expand-lg navbar-light bg-white fixed-top" >
						<div class="col-md-1">
							<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
								<span class="navbar-toggler-icon"></span>
							</button> 
							<a class="navbar-brand" href="${ctx}/dorest">
							<img class="nav-img" src="${ctx}/resources/images/xiashi.png" alt="" ></img></a>
						</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10" style="padding-top:90px;">
					<h1 class="text-center"></h1>
					<div>
						<table class="table table-striped table-hover text-center">
						<thead class="thead-dark">
							<tr>
								<th>状态码</th>
								<th>意义</th>
								<th>解释</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${errors}" var="error">
								<tr id="${error.code}">
									<td>${error.code}</td>
									<td><i>${error.definition}</i></td>
									<td>${error.explanation}</td>
								</tr>	
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div id="footer" class="col-md-12"></div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	var code = '${errorcode}';
	/**后期执行文件*/
	//$("#nav").load(ctx + "/nav");
	$("#footer").load(ctx + "/footer");
	document.title = '触言 - ' + code;
	$("h1").html($.format("<b>{0}</b> - <i>{1}</i>", code, $("#" + code).find("td").eq(1).text()));
	$("#" + code).addClass('table-danger font-weight-bold');
	$("#200").removeClass('table-danger').addClass('table-success');
	</script>
</html>