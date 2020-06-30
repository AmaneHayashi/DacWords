<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.service.UserService,java.util.UUID" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<title>触言 - 登录</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.form.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/user/sign.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div id='container'>
	        <div class='middle'>
	            <div class='inner'>
	            	<h5>
	            		<a class="active" id="sign-in-btn" href="javascript:void(0)">登录</a>
	            		<a class="unactive" id="sign-up-btn" href="${ctx}/register">注册</a>
	            	</h5>
	            	<form accept-charset="UTF-8" method="post" id="loginForm">
						<span class="glyphicon glyphicon-user"></span>
	            		<input type="text" id="name" name="name" placeholder="用户名"><br/>
						<span class="glyphicon glyphicon-lock"></span> 
						<input type="password" id="pswd" name="pswd" placeholder="密码"><br/>
						<button id="login" type="submit">登录</button><br/>
	            	</form>
	            	<span class="glyphicon glyphicon-remove-circle" id="fail" style="color:#FFC0CB;">&nbsp;登录失败</span>
					<span class="glyphicon glyphicon-ok-circle" id="succ" style="color:#00C000;">&nbsp;登录成功！页面将于3秒后跳转...</span>
				</div>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	</script>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script src="${ctx}/resources/js/user/login.js"></script>
</html>