<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	</head>
	<body>
		<form role="form" id="tform" method="post" accept-charset="UTF-8">
			<div id="test">
				<c:forEach items='${qList}' var='q'>
					<c:set var="tag" value="${fn:substring(q.id, fn:length(q.id) - 2, fn:length(q.id))}"/>
					<div id="q${tag}" name="question">
						<c:if test="${not empty fn:trim(q.picpath)}">
							<img class="bg-img" id="img${tag}" name="${q.picpath}" src="${ctx}/resources/images/uploads/${q.picpath}"/>
						</c:if>
						<p><span>[<input type="text" name="${q.id}" id="ans${tag}" width="50px"/>]</span>${tag}.
						$${q.content}$</p>
						<c:if test ='${not empty fn:trim(q.optionA)}'>
							<label>$$\mathbb{(A).}${q.optionA}$$</label>
							<label>$$\mathbb{(B).}${q.optionB}$$</label>
							<label>$$\mathbb{(C).}${q.optionC}$$</label>
							<label>$$\mathbb{(D).}${q.optionD}$$</label>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</form>
	</body>
	<script type="text/javascript">
		$().ready(function(){
			let qDivs = $("#test").find("div[name='question']");
			let xztlen = qDivs.has("label").length;
			let tktlen = qDivs.not(":has(label)").length;
			const xzts = "<h5>一、选择题：1~{0}题，每小题4分，共{1}分。下列每题给出的四个选项中，只有一个选项是符合题目要求的。</h5>";
			const tkts = "<h5>二、填空题：{0}~{1}题，每小题4分，共{2}分。</h5>";
			qDivs.has("label").first().before($.format(xzts, xztlen ,4 * xztlen));
			qDivs.not(":has(label)").first().before($.format(tkts, xztlen + 1 ,xztlen + tktlen, 4 * tktlen));
			//设置标签宽度
			$("label").css({'width':'23%'});//'margin-left':'80px','margin-right':'80px', 
			//设置
			if('${ansList}'.length > 0){
				let ansObj = $.parseJSON('${ansList}');
				const grader = "<input class='bg-light text-success rounded-16' type='text' name='{0}' value='{1}'></input>";
				$("input").attr('disabled', 'disabled');
				for(let obj of ansObj){
					$("#ans" + obj['id']).val($.format("{0}<答案：{1}>", obj['answer'], obj['key']))
					.parent().before($.format(grader, 'jid', obj['grade'] > -1 ? obj['grade'] + "分" : ""));
				}
			}
		});
	</script>
</html>