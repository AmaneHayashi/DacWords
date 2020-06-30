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
	<script type="text/x-mathjax-config">
		MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
	</script>
	<script type="text/javascript"
		src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
	</script>
	</head>
	<body>
    	<div class="modal-dialog modal-lg">
      		<div class="modal-content">
	       		<!-- 模态框头部 -->
	        	<div class="modal-header">
	          		<h4 class="modal-title">添加题目</h4>
	          		<button type="button" class="close" data-dismiss="modal" name="shutdown">&times;</button>
	        	</div>
	        	<!-- 模态框主体 -->
	        	<div class="modal-body">
	        		<form role="form" id="qform" method="post" accept-charset="UTF-8">
	        			<div class="row">
	        				<div class="col-md-1"></div>
							<!-- 左侧布局 -->
							<div class="col-md-5">
								<div class="form-group">
									<label for="subject">学科：</label> 
									<!-- 简化情形 -->
									<select class="form-control rounded-16" id="subject" name="subject"> 
										<option value="0" selected="selected">选择学科</option> 
									</select>
								</div>			       				
								<div class="form-group">
									<label for="type">题型：</label> 
									<select class="form-control rounded-16" id="type" name="type"> 
										<option value="0" selected="selected">选择题型</option> 
										<option value="1">选择题</option> 
										<option value="2">填空题</option> 
									</select>
								</div>	
							</div>
							<!-- 右侧布局 -->
							<div class="col-md-5">
								<div class="form-group">
									<label for="point">考点：</label> 
									<select class="form-control rounded-16" id="point" name="point"> 
										<option value="0" selected="selected">选择考点</option> 
									</select>
								</div>	
								<div class="form-group">
									<label for="diff">难度：</label>
									<input type="number" class="form-control rounded-16" id="diff" name="diff"/>
									<label class="help-block">请输入在1~9之间的整数</label>
								</div>
							</div>		
							<div class="col-md-1"></div>					
						</div>
						<div class="row">
							<div class="col-md-1"></div>	
							<div class="col-md-10">				
								<div class="form-group">
									<label for="content">内容：</label>
									<textarea class="form-control rounded-16" id="content" name="content" rows="10"></textarea>
									<span></span>
								</div>	
								<div class="options">
									<c:forEach items='<%="ABCD".split("")%>' var="ch">
										<c:if test="${!empty fn:trim(ch)}">
											<div class="form-group">
												<label for="key">${ch}选项：</label>
												<input type="text" class="form-control rounded-16" id="option${ch}" name="option${ch}"/>
												<span></span>
											</div>
										</c:if>
									</c:forEach>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-md-9">
											<label for="picpath">图片：</label>
											<input class="form-control rounded-16" type="file" id="pic" name="pic"/>
											<span class="help-block">jpg/png格式，10~500kb<b>（可不填）</b></span>
										</div>
										<div class="col-md-3">
											<img class="bg-img"></img>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="key">答案：</label>
									<input type="text" class="form-control rounded-16" id="key" name="key"/>
									<span></span>
									<label class="help-block">请输入题目答案<b>（选择题请输入选项）</b></label>
								</div>
								<div class="form-group">
									<label for="solution">解析：</label>
									<textarea class="form-control rounded-16" id="solution" name="solution" rows="5"></textarea>
									<label class="help-block">请在文本框中输入答案解析<b>（可不填）</b></label>
								</div>
							</div>	
							<div class="col-md-1"></div>	
						</div>
					</form>
	        	</div>
	        	<!-- 模态框底部 -->
	        	<div class="modal-footer">
				<!-- 	<button type="button" id="tempModal" data-dismiss="modal" style="display:none;"></button> -->
	        		<button type="submit" class="btn btn-outline-success" id="saveModal">保存</button>
	          		<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" name="shutdown">关闭</button>
	        	</div>
      		</div>
    	</div>
	</body>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var qtMap = $.parseJSON('${qtMap}');
	var subject = '${creator.subject}';
	</script>
	<script src="${ctx}/resources/js/default/whole.js"></script>
	<script src="${ctx}/resources/js/modals/addModal.js"></script>
</html>