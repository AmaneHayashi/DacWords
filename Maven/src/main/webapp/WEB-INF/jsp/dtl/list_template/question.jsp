<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="container-fluid">
			<c:forEach items="${list}" var="q" end="${hasNext ? fn:length(list) - 2 : fn:length(list)}">
				<h5><a class="a-detail" href="${ctx}/dorest/${templateType}/detail/${q.id}" target="_blank">${q.point}（${q.id}）</a></h5>
				<p class="p0">本题为<b>${q.type}</b>，来自<b>${q.name}</b>。<br/>
				难度：<b>${q.diff}</b>/9<br/>
				作者：<b>${q.ppsr}</b><br/>
				发布时间：<b>${q.ppstime}</b></p>			
				<hr/>
			</c:forEach>
		</div>
	</body>
	<script>var hasNext = '${hasNext}';</script>
</html>