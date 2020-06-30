<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
	<html lang="ch">
	<meta charset="UTF-8">
	<link href="${ctx}/resources/css/part/nav.css" rel="stylesheet" type="text/css">
	<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
	<body>
		<div class="row">
			<div class="col-md-12">
				<nav class="navbar navbar-expand-lg navbar-light bg-white fixed-top" >
					<div class="col-md-1">
						<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
							<span class="navbar-toggler-icon"></span>
						</button> 
						<a class="navbar-brand" href="./index.jsp">
						<img class="nav-img" src="${ctx}/resources/images/xiashi.png" alt="" ></img></a>
					</div>
					<div class="col-md-3"><div class="nav-link mx-auto" id="subtitle">创作 - %s(Step %s)</div></div>
					<div class="col-md-2"><div class="float-left" id="draft_saving" style="display:none;">草稿保存中...</div></div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<button type="button" id="read_draft" class="btn btn-outline-secondary rounded-16 mx-auto">
						<span class="glyphicon glyphicon-tasks"></span>&nbsp;读草稿</button>
						<button class="btn btn-outline-info rounded-16 float-right" onclick="window.location.href='${ctx}/dorest'">
						<span class="glyphicon glyphicon-home"></span>&nbsp;返回主页</button>
					</div>
				</nav>
			</div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
</html>