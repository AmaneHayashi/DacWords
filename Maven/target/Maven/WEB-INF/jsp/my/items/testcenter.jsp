<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>触言- 考试中心</title>
	</head>
	<body>
		<div class="row menu">
			<!--菜单(文手记，题夏试，动态)-->
			<ul class="col-md-12 list-unstyled menu">
				<li id="markDepart" class="list-item"><a href="javascript:void(0)">
				<span class="glyphicon glyphicon-tint"></span>&nbsp;成绩科</a></li>
				<li id="gradeDepart" class="list-item active"><a href="javascript:void(0)">
				<span class="glyphicon glyphicon-adjust"></span>&nbsp;阅卷科</a></li>
			</ul>
		</div>
		<div class="row item">
			<!--列表-->
		</div>
	</body>
	<script type="text/javascript">
		sessionStorage['highlight'] = '{user.name};
	</script>
</html>