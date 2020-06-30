<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
		<div class="col-md-2">
			<img class="rounded-circle usr-img" src="${ctx}/resources/images/icon.png" alt=""></img>
		</div>
		<div class="col-md-10">
			<h3 class="lead font-weight-bold">${user.name}</h3>
			<ul class="list-unstyled head">
				<li class="list-item">${ppsn}</br><span class="sup">创作</span></li>
				<li class="list-item">${tn}</br><span class="sup">考试</span></li>
				<li class="list-item">${coln}</br><span class="sup">收藏</span></li>
				<li class="list-item">${msgn}</br><span class="sup">消息</span></li>
			</ul>
		</div>
	</body>
</html>