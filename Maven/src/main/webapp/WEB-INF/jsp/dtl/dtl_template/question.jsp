<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="container-fluid">
			<h3>${dtl.id}(<strong>${dtl.point}</strong>)</h3>
			<hr class="hr0"/>
	      	<p><b>[${dtl.type}，难度${dtl.diff}/9]</b>
				<button id="tipoff" class="float-right glyphicon glyphicon-question-sign" style="color:#ffc107;"></button>
		      	<button id="like" class="float-right glyphicon glyphicon-heart-empty" style="color:#dc3545;"></button>
		      	<button id="view" class="float-right glyphicon glyphicon-eye-close text-dark"></button>
	      	</p>
	      	<div class="mx-auto">$$${dtl.content}$$</div>
    		<c:if test="${not empty fn:trim(dtl.picpath)}">
				<img class="bg-img" id="img${tag}" name="${dtl.picpath}" src="${ctx}/resources/images/uploads/${dtl.picpath}"/>
			</c:if>
	      	<div>
				<c:if test ='${not empty fn:trim(dtl.optionA)}'>
					<label>$$\mathbb{(A).}${dtl.optionA}$$</label>
					<label>$$\mathbb{(B).}${dtl.optionB}$$</label>
					<label>$$\mathbb{(C).}${dtl.optionC}$$</label>
					<label>$$\mathbb{(D).}${dtl.optionD}$$</label>
				</c:if>
	      	</div>
	      	<p>题目答案：<b name="solu">$$${dtl.key}$$</b></p>
	      	<p>题目解析：<b name="solu">${dtl.solution}</b></p>
	     	<button class="btn"></button>
	     	<div class="col-md-12 btn btn-outline-info rounded-16" style="font-size:14px;">
	     	作者：<b>${dtl.ppsr}</b>&nbsp;&nbsp;
	     	发布时间：<b>${dtl.ppstime}</b>
	     	</div>
		</div>
	</body>
	<script type="text/javascript">
	$(document).attr('title', function(k, v){return v + '${dtl.point}';});
	var cid = '${dtl.id}';
	var isCollected = '${dtl.collect_time}'.length != 0;
	sessionStorage['highlight'] = '「题·夏试」';
	</script>
</html>