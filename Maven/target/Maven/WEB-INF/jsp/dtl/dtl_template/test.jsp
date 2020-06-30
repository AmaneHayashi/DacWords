<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="container-fluid">
			<h3>${dtl.id}(<strong>${dtl.name}</strong>)
			</h3>
			<hr class="hr0"/>
			<p>考试说明：
				<button id="tipoff" class="float-right glyphicon glyphicon-question-sign" style="color:#ffc107;"></button>
		      	<button id="like" class="float-right glyphicon glyphicon-heart-empty" style="color:#dc3545;"><span></span></button>
	      	</p>
	      	<ul class="statement">
				<li>本次考试从进入考试开始计时，答题完毕后，可在个人中心查看考试成绩、试题分析等结果。</li>
				<li>考试内容全屏显示。考试过程中不允许退出全屏，否则本次考试按零分处理。</li>
				<li>本次考试需要用到MathJax插件，请根据右边公式的显示情况，判断您的浏览器是否支持MathJax插件：$\lim_{x\to \infty}\frac{x^{3}}{x-\sin x}=6$。</li>
				<li>您可点击右上角第一个小按钮查看考试具体信息。核对无误后，点击上方右侧的按钮，进入考试。</li>
			</ul>
			<div id="card">
				<div class="card slim-card">
					<div class="card-header">
						 <a class="card-link" data-toggle="collapse" data-parent="#card" href="#card-u">
						 <b>考生信息</b></a>
					</div>
					<div id="card-u" class="collapse hide">
						<ul class="statement circle row" >
				      		<div class="col-md-6">
				      			<li>姓名：${user.name}</li>
				      		</div>
				      		<div class="col-md-6">
				      			<li>ID：${user.uid}</li>
				      		</div>
			      		</ul>
					</div>
				</div>
				<div class="card slim-card">
					<div class="card-header">
						 <a class="card-link" data-toggle="collapse" data-parent="#card" href="#card-t">
						 <b>考试信息</b></a>
					</div>
					<div id="card-t" class="collapse hide">
						<ul class="statement circle row" >
				      		<div class="col-md-6">
				      			<li>名称：${dtl.name}</li>
				      			<li>标识：${dtl.id}</li>
				      			<li>学科：${dtl.field}</li>
				      			<li>科目：${dtl.subject}</li>
							</div>
				      		<div class="col-md-6">
				      			<li>难度：${dtl.diff}/9</li>
				      			<li>题数：${dtl.nums}</li>
				      			<li>时长：${dtl.duration}分钟</li>
				      			<li>总分：${dtl.fullmark}分</li>
				      		</div>
			      		</ul>
					</div>
				</div>	
			</div>      	
	      	<div class="col-md-12 btn btn-outline-info rounded-16" style="font-size:14px;">
	     	作者：<b>${dtl.ppsr}</b>&nbsp;&nbsp;
	     	发布时间：<b>${dtl.ppstime}</b>
	     	</div>
		</div>
	</body>
	<script type="text/javascript">
	var cid = '${dtl.id}';
	var isCollected = '${dtl.collect_time}'.length != 0;
	sessionStorage['highlight'] = '「题·夏试」';
	/**开始考试逻辑*/
	$(document).attr('title', function(k, v){return v + '${dtl.name}';});
	const startTestText = "<button id='start_test' class='btn btn-outline-success rounded-16 float-right'>开始考试</button>";
	const previewText = "<button id='preview_test' class='btn btn-outline-info rounded-16 float-right'>预览考试</button>";
	if('${dtl.ppsr}' == '${user.name}'){
		$("h3").append(previewText);
		$(document).on("click", "#preview_test", function(){
			window.location.href = $.format('/user/{0}/testcenter/judge?id={1}&uid={2}&judged={3}&ppsr={4}', '${user.uid}', '${dtl.id}', '${user.uid}', false, true);
		});
	} 
	else if(new Date().getTime() >= new Date('${dtl.ppstime}').getTime()){
		$("h3").append(startTestText);
		$(document).on("click", "#start_test", function(){
			window.location.href = $.format("{0}/dorest/start_test?{1}", ctx, $.json2url($.parseJSON('${dtl}')));
		});
	}
	</script>
</html>