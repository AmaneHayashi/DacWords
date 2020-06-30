<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="usr" value='<%=(User)request.getSession().getAttribute("user") %>'/>
<c:set var="subtitle" value="创作·基本信息(Step 1)"/>
<!DOCTYPE html>
<html>
	<head>
		<title>触言 - 创作</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.form.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/creation/creation.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div id="header"></div>
			<div class="row" style="padding-top: 90px;"><hr>
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<form role="form" id="cform" method="post" accept-charset="UTF-8">
						<div class="form-group">
							<label for="opus-field-child">请选择创作的作品方向(依次选择主类别、子类别、作品方向)：</label> 
							<div class="form-inline">
								<select class="form-control float-left rounded-16" id="opus_grandpa" name="opus"> 
									<option value="0" selected="selected">选择主类别(一级目录)</option> 
								</select>
								<select class="form-control mx-auto rounded-16" id="opus_papa" name="opus"> 
									<option value="0" selected="selected">选择子类别(二级目录)</option> 
								</select>
								<select class="form-control float-right rounded-16" id="opus_child" name="opus"> 
									<option value="0"selected="selected">选择作品方向(三级目录)</option> 
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="author-info">请核对作者的个人信息：</label><br/>
							<input type="text" class="form-control rounded-16" id="author_info" name="author_info" readonly="readonly"
							value="${usr.name}（${user.uid}）"/>
						</div>
						<div class="checkbox">
							<label> <input id="author_checked" name="author_checked" type="checkbox"/>&nbsp;我已核对作者信息，并确认无误</label>
						</div>
						<span class="glyphicon glyphicon-remove-circle" id="fail" style="color:#FFC0CB;">&nbsp;提交失败！</span>
						<button id="nextstep" type="submit" class="btn btn-lg btn-outline-primary float-right rounded-16">下一步</button>
					</form>
				</div>
				<div class="col-md-2"></div>
			</div>
			 <div id="footer" class="col-md-12"></div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	var subtitle = '${subtitle}';
	var uname = '${usr.name}';
	/**后期执行文件*/
	$("#footer").load(ctx + "/footer");
	$("#header").load(ctx + "/creation_nav", function(){
		$.getScript(ctx + "/resources/js/creation/step1.js");
	});
	</script>
</html>