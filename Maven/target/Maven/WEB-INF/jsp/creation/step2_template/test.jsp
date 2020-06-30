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
				<div class="form-group">
					<label for="c-duration">考试时长：</label>
					<input type="number" class="form-control rounded-16" id="c-duration" name="duration"/>
					<div>单位为时间，建议在30~75分钟间取值</div>
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
				<div class="form-group">
					<label for="c-startime">考试上线时间：</label> 
					<button type="button" class="btn btn-sm btn-outline-success rounded-16" id="edit_ppstime">设置为当前时间</button>
					<input type="datetime-local" class="form-control rounded-16" id="c-ppstime" name="ppstime"/>
					<div>考试从考试上线时间开始</div>
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