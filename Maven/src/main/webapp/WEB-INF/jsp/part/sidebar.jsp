<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<meta charset="UTF-8">
	<head>
	</head>
	<link href="${ctx}/resources/css/part/side.css" rel="stylesheet" type="text/css">
	<body>
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-12">
					<div id="button-group"></div>
					<div id="card">
						<div class="card">
							<div class="card-header">
								 <a class="card-link" data-toggle="collapse" data-parent="#card" href="#card-1">
								 什么是<b>${listType}</b>？</a>
							</div>
							<div id="card-1" class="collapse hide">
								<div class="card-body"><b>${listType}</b>是指：</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="${ctx}/resources/js/part/side.js"></script>
</html>