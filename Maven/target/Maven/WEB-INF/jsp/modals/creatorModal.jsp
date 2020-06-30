<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title></title>
	</head>
	<body>
    	<div class="modal-dialog">
      		<div class="modal-content">
	       		<!-- 模态框头部 -->
	        	<div class="modal-header">
	          		<h4 class="modal-title">创作提示</h4>
	          		<button type="button" class="close" data-dismiss="modal" name="shutdown">&times;</button>
	        	</div>
	        	<!-- 模态框主体 -->
	        	<div class="modal-body">
					你当前有作品正在创作中，请继续您的创作。如果不想继续创作上一个作品，您可以点击重新创作键，开始新的创作。
	        	</div>
	        	<!-- 模态框底部 -->
	        	<div class="modal-footer">
	        		<button type="button" class="btn btn-outline-success" data-dismiss="modal" id="recreate">重新创作</button>
	          		<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" name="shutdown">取消</button>
	        	</div>
      		</div>
    	</div>
	</body>
	<script type="text/javascript">
	$().ready(function() {
		$("#recreate").click(function(){
			window.open('${ctx}/creation/warmup');
		});
	});
	</script>
	<script src="${ctx}/resources/js/modals/creatorModal.js"></script>
</html>