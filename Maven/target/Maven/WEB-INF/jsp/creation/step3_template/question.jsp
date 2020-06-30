<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="form-group">
			<label for="c-content">内容：</label> 
			<button type="button" class="btn btn-outline-danger float-right border-0 rounded-16" id="correct">
			<span class="glyphicon glyphicon-pencil"></span>&nbsp;统一处理</button>
			<button type="button" class="btn btn-outline-success float-right border-0 rounded-16" id="add" 
			data-toggle="modal" data-target="#addModal"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加题目</button>
			<table class="table table-hover table-striped .table-responsive-md">
				<thead class="thead-dark mx-auto">
					<tr>
						<th>题号</th>
						<th>考点</th>
						<th>题型</th>
						<th>难度</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
		</div>
		<div class="form-group">
			<label for="c-nums">本次更新的总题数：</label> 
			<input type="text" class="form-control rounded-16" id="c-nums" name="nums" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label for="c-diff">本次更新的题目平均难度：</label> 
			<input type="text" class="form-control rounded-16" id="c-diff" name="diff" readonly="readonly"/>
			<div>无需填写，由题目的难度加权</div>
		</div>	
	</body>
	<script type="text/javascript">
	var oid = '${creator.id}';
	</script>
</html>