<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
	<html lang="ch">
	<head>
		<title>触言 - ${user.name}</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/my/my.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div id="nav"></div>
			<div class="row" style="padding-top:90px;"><hr/>
				<div class="col-md-1">
				</div>
				<div class="col-md-7">
					<div class="row data" id="infos">
						<!--资料中心(数据不可点击)-->
					</div>
					<div id="editable"></div>
				</div>
				<div class="col-md-3">
					<div class="row intro">
						<!--个人简介(即时更新)-->
						<div class="col-md-12">
							<p class="float-left">个人介绍</p>
							<button class="btn btn-sm btn-outline-secondary rounded-16 float-right">
							<span class="glyphicon glyphicon-pencil"></span>&nbsp;编辑</button>
						</div>
						<div class="col-md-12 text">
							Hello world !
						</div>
					</div><hr/>
					<div class="row mylike">
						<!--中心(消息中心,考试中心)-->
						<div class="col-md-12">
							<a href="${ctx}/user/myspace"><p>
							<span class="glyphicon glyphicon-tint"></span>&nbsp;&nbsp;个人主页</p></a>
						</div>
						<div class="col-md-12">
							<a href="${ctx}/user/msgcenter"><p>
							<span class="glyphicon glyphicon-send"></span>&nbsp;&nbsp;消息中心</p></a>
						</div>
						<div class="col-md-12">
							<a href="${ctx}/user/testcenter"><p>
							<span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;考试中心</p></a>
						</div>
					</div><hr/>
				</div>
				<div class="col-md-1">
				</div>
			</div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	var loadable = '${path}';
	/**后期执行文件*/
	sessionStorage['highlight'] = '${user.name}';
	$("#nav").load(ctx + "/nav");
	$("#infos").load(ctx + "/amane/user/getParams?uid=${user.uid}");
	$("#editable").load(ctx + "/user/" + loadable + "/dtl", function(){
		$.getScript(ctx + "/resources/js/my/items/" + loadable + ".js", function(){
			$.getScript(ctx + "/resources/js/my/mode.js");
		});
	});
	$.getScript(ctx + "/resources/js/default/highlight.js");
	</script>
</html>