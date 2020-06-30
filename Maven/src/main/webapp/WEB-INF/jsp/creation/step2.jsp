<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="subtitle" value="创作·作品属性(Step 2)"/>
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
						<div id="formContents"></div>
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
	var kind = '${creator.kind}';
	var type = '${creationType}';
	/**后期执行文件*/
	$("#header").load(ctx + "/creation_nav", function(){
		$.getScript(ctx + "/resources/js/creation/step2.js");
	});
	$("#footer").load(ctx + "/footer");
	$("#formContents").load(ctx + "/amane/part/creation/step2/pretask?kind=${creator.kind}&type=${creationType}", function(){
		$.getScript(ctx + "/resources/js/creation/step2_template/${creationType}.js");
	});
	</script>
</html>