<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
		<h5>${qe.id}(<strong>${qe.point}</strong>)</h5>
      	<p><b>[${qe.type}，难度${qe.diff}/9]</b>
      	</p>
      	<div>$$${qe.content}$$</div>
      	<div>
			<c:if test ='${not empty fn:trim(q.optionA)}'>
				<label>$$\mathbb{(A).}${q.optionA}$$</label>
				<label>$$\mathbb{(B).}${q.optionB}$$</label>
				<label>$$\mathbb{(C).}${q.optionC}$$</label>
				<label>$$\mathbb{(D).}${q.optionD}$$</label>
			</c:if>
		</div>
      	<p>题目答案：<b>$${qe.key}$</b></p>
      	<p>题目解析：<b>${qe.solution}</b></p>
	</body>
	<script>MathJax.Hub.Queue(["Typeset",MathJax.Hub]);</script>
</html>