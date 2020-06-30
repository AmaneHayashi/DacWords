<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="usr" value='<%=(User)request.getSession().getAttribute("user") %>'/>
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
						<a class="navbar-brand" href="${ctx}/dorest">
						<img class="nav-img" src="${ctx}/resources/images/xiashi.png" alt="" ></img></a>
					</div>
					<div class="col-md-11">
						<div class="collapse navbar-collapse">
							<ul class="nav nav-pills" >
								<li class="nav-item active">
									<a class="nav-link" href="${ctx}/dorest" id="发现">发现</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="${ctx}/message" id="消息">消息
									<span class="badge badge-info badge-pill" id="new">new!</span></a>
								</li>
							</ul>
							<form class="form-inline combined-search" >
								<input class="ipt-search" type="text" id="search" placeholder="搜索" maxlength="20">
								<a class="btn-search" id="scope" href="javascript:void(0)"></a>
							</form>
							<div class="menuDiv ml-md-auto">
								<ul class="navbar-nav" id="ul_menu">
									<li class="nav-item" id="li_intro">
										<a class="nav-link dropdown-toggle f-nav-a" id="${usr.name}">${usr.name}</a> 
										<div class="nav nav-list">
											<a class="dropdown-item" href="${ctx}/user/myspace">个人中心</a> 
											<a class="dropdown-item" href="${ctx}/feedback">建议与反馈</a> 
											<a id="logout" class="dropdown-item" href="javascript:void(0)">退出登录</a>
										</div>
									</li>
								</ul>
							</div> 
							<a class="btn btn-success rounded-16" href="javascript:void(0)" target="_blank" id="create"
							data-toggle="modal">
							<span class="glyphicon glyphicon-leaf"></span>&nbsp;创作</a>
						</div>
					</div>
				</nav>
			</div>
		</div>
	  	<!-- 预览题目模态框 -->
	  	<!-- 增加题目模态框 -->
	  	<div class="modal fade" id="createModal"></div>
	</body>
	<script>
	var creator = '${creator}';
	var uid = '${user.uid}';
	</script>
	<script>$("#createModal").load(ctx + "/modals/createModal");</script>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script src="${ctx}/resources/js/part/nav.js"></script>
</html>