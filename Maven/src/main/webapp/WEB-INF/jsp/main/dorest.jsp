<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.amane.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
	<html lang="ch">
	<head>
		<title>触言 - 随笔，音乐，文艺小鉴</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${ctx}/resources/css/default/bootstrap.css" type="text/css">
		<script src="${ctx}/resources/js/default/jquery-3.4.1.min.js"></script>
		<script src="${ctx}/resources/js/default/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/default/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="${ctx}/resources/css/default/whole.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/main/dorest.css" type="text/css">
		<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container-fluid">
			<div id="nav"></div>
			<div class="row" style="padding-top:90px;"><hr/>
				<div class="col-md-1">
				</div>
				<div class="col-md-6">
					<div class="row" id="carousel">
						<div class="col-md-12">
							<div class="carousel slide" id="carousel-567146">
								<ol class="carousel-indicators">
									<li data-slide-to="0" data-target="#carousel-567146" class="active"></li>
									<li data-slide-to="1" data-target="#carousel-567146"></li>
								</ol>
								<div class="carousel-inner">
									<div class="carousel-item active">
										<img class="d-block w-100" alt="Carousel Bootstrap First" src="${ctx}/resources/images/coursel1.jpg" />
										<div class="carousel-caption">
											<h4><i>Welcome to Dacwords!</i></h4>
											<p><i>You can fulfill yourself here by figuring out questions, accomplishing tests, appreciating articles and digesting essays.
											 you can also click the <b>CREATION BUTTON</b> right above and start a new assignment trip. Enjoy yourself!</i></p>
										</div>
									</div>
									<div class="carousel-item">
										<img class="d-block w-100" alt="Carousel Bootstrap Second" src="${ctx}/resources/images/coursel2.jpg" />
										<div class="carousel-caption">
											<h4><i>National Entrance Examination for Postgraduate(NEEP)</i></h4>
											<p><i>There are only <b name="rest_time"></b> days for you to prepare for the NEEP. Fighting!</i></p>
										</div>
									</div>
								</div> 
								<a class="carousel-control-prev" href="#carousel-567146" data-slide="prev">
								<span class="carousel-control-prev-icon"></span> 
								<span class="sr-only">Previous</span></a> 
								<a class="carousel-control-next" href="#carousel-567146" data-slide="next">
								<span class="carousel-control-next-icon"></span> 
								<span class="sr-only">Next</span></a>
							</div>
							<div class="line">
								<span class="text-secondary text">&nbsp;&nbsp;新&nbsp;作&nbsp;速&nbsp;递&nbsp;&nbsp;</span>
							</div>
							<div id="view_more" style="display:none">
								<button class="col-md-12 btn btn-light text-dark">VIEW MORE >></button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="row">
						<div class="col-md-12" >
							<blockquote class="blockquote"></blockquote>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" id="alerts">
							<div class='alert alert-primary'>
								<h5>公告：</h5> 
								距离<b>2020年全国硕士研究生招生考试</b>还有<b name="rest_time"></b>天。
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-1">
				</div>
			</div>
			 <div id="footer" class="col-md-12"></div>
		</div>
	</body>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var ctx = '${ctx}';
	/**后期执行文件*/
	$("#nav").load(ctx + "/nav");
	$("#footer").load(ctx + "/footer");
	$.getScript(ctx + "/resources/js/main/dorest.js", function(){$.getScript(ctx + "/resources/js/default/highlight.js");});
	</script>
</html>