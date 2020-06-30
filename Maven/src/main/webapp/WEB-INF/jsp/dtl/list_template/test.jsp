<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="container-fluid">
			<c:forEach items="${list}" var="t" end="${hasNext ? fn:length(list) - 2 : fn:length(list)}">
				<h5><a class="a-detail" href="${ctx}/dorest/${templateType}/detail/${t.id}" target="_blank">${t.id}（${t.name}）</a></h5>
				<p class="p0">本试卷共[<b>${t.nums}</b>]题，满分<b>${t.fullmark}</b>分，考试时间<b>${t.duration}</b>分钟。<br/>
				难度：<b>${t.diff}</b>/9<br/>
				发布时间：<b>${t.ppstime}</b></p>
				<hr/>	
			</c:forEach>
		</div>
	</body>
	<script>var hasNext = '${hasNext}';</script>
</html>