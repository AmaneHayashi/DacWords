<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<!-- 左侧布局 -->
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="c-field">领域：</label> 
					<select class="form-control rounded-16" id="c-field" name="field"> 
						<option value="0" selected="selected">选择领域</option> 
					</select>
				</div>								
			</div>
			<!-- 右侧布局 -->
			<div class="col-md-6">
				<div class="form-group">
					<label for="c-subject">学科：</label> 
					<select class="form-control rounded-16" id="c-subject" name="subject"> 
						<option value="0" selected="selected">选择学科</option> 
					</select>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	/**全局变量定义文件*/
	var abbrMap = $.parseJSON('${abbr}');
	var qtMap = $.parseJSON('${qtMap}');
	var formatterMap = $.parseJSON('${formatterMap}');
	</script>
</html>