const blockquote = 
	"<p class='mb-0'>{0}</p>" +
	"<footer class='blockquote-footer text-right'>{1}" +
		"<cite>{2}</cite>" +
	"</footer>";

const hotspot = 					
	"<hr/>" +
	"<div class='row text-secondary' style='margin:25px 5px 25px 5px'>" +
		"<div class='col-md-12'>" +
			"<a class='text-dark' href='{0}' target='_blank'><h5>{1}</h5></a>"  +
			"<div>{2}</div>"  +
			"<small class='float-right' style='margin-top:5px;'>作者：{3}&nbsp;&nbsp;&nbsp;创作时间：{4}</small>" + 
		"</div>" +
	"</div>";

const hs_intro = "{0}({1})";

const hs_addr = "/dorest/{0}/detail/{1}";

const hs_test = "本试卷共<b>{0}</b>题，考试时间<b>{1}</b>分钟，平均难度为<b>{2}/9</b>。";

const hs_question = "本题为<b>{0}</b>，主要考察了<b>{1}</b>，难度为<b>{2}/9</b>。";

const alert_1 = 
	"<div class='alert alert-info'>" +
		"<h5>提示：</h5> " +
		"距离考试<b>{0}</b>开始还有<b>{1}</b>天。" +
	"</div>";

const alert_2 = 
	"<div class='alert alert-primary'>" +
		"<h5>提示：</h5> " +
		"您的考试作品<b>{0}</b>有了新的提交记录。" +
		"<a href='{2}' class='alert-link'>进入考试中心</a> " +
	"</div>";

const alert_3 = 
	"<div class='alert alert-danger'>" +
		"<h5>警告！</h5> " +
		"您参与的考试<b>{0}</b>已经公布了您的成绩。" +
		"<a href='{2}' class='alert-link'>进入考试中心</a> " +
	"</div>";

const alert_4 = 
	"<div class='alert alert-success'>" +
		"<h5>恭喜！</h5> " +
		"您的作品<b>{0}</b>有了新的收藏记录。" +
	"</div>";

$().ready(function() {
	/* 高亮 */
	sessionStorage['highlight'] = '发现';
	// 设置热点
	$.post(ctx + "amane/part/hotspot", function(s){
		if(s.success == true && s.data.state == 1){
			for(let f of s.data.data){
				let hs = $.format(
						hotspot,
						$.format(hs_addr, f['type'], f['id']),
						$.format(hs_intro, f['name'], f['id']), 
						$.format(eval("hs_" + f['type']), f['hs0'], f['hs1'], f['hs2']), 
						f['ppsr'],
						f['ppstime']);
				$("#view_more").before(hs);
			}
			$("div.line").next().remove();
			$("#view_more").css('display', 'block');
		}
	});
	// 设置座右铭
	if((d = sessionStorage['blockquote']) != undefined){
		setBlockquote($.parseJSON(d));
	}
	else{
		$.post(ctx + "/amane/part/blockquote", function(s){
			if(s.success == true && s.data.state == 1){
				setBlockquote(s.data.data);
				sessionStorage['blockquote'] = JSON.stringify(s.data.data);
			}
		});
	}
	// 设置提示
	$.post(ctx + "/amane/part/alert", function(s){
		if(s.success == true && s.data.state == 1){
			for(let f of s.data.data){
				let al = $.format(
						eval("alert_" + f['type']),
						$.format(hs_intro, f['NAME'], f['id']), 
						getRestDay(f['time']),
						f['type'] % 3 == 1 ? 
								$.format(hs_addr, f['type'], f['id']) : 
								ctx + "/user/testcenter");
				$("#alerts").append(al);
			}
		}
	});
	// 其余小内容
	$("b[name='rest_time']").text(getRestDay("2019-12-21 08:30:00"));
	$("div.text-secondary").find("a").css({'text-decoration':'none'});
});

function getRestDay(targetTime){
	return parseInt((new Date(targetTime).getTime() - new Date().getTime()) / 86400000);
}

function setBlockquote(f){
	let bq = $.format(blockquote, f['content'], f['author'], f['cite']);
	$("blockquote").append(bq);
}