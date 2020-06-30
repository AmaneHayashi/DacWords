const noActivity = "<div class='mx-auto text-center'><p>您还没有这儿的动态~</p></div>";

const unis = 
	"<div class='col-md-12 element'>" + 
	"	<div class='header'>" +
	"		<img class='rounded-circle usr-img-sm' src='" + ctx + "/resources/images/icon.png' alt=''></img>" +
	"		<a href=''>{0}</a>{1}" +
	"		<span class='appendix'>{2}</span>" +
	"	</div>" +
	"	<div class='contents'>" +
	"		<a href='{3}' target='_blank'>" +
	"		<h5>{4}</h5></a>" +	
	"	</div>" +
	"</div>";

/**(0) -> 用户名;
 * (1) -> 动态名;
 * (2) -> 动态时间;
 * (3) -> 动态链接;
 * (4) -> 动态简介;
 * (5) -> 动态描述*/

/**testcenter主要部分*/
const gradeDepart = $.format(unis, "{0}", "完成了考试", "{1}", "{2}","{3}");

const markDepart = $.format(unis, "{0}", "参与了考试", "{1}", "{2}","{3}");

/**testcenter辅助部分*/
//分数
const tc_grade = "<b class='text-{0}'>{1}</b>";
//地址
const tc_addr = "{0}/judge?id={1}&uid={2}&judged={3}&ppsr={4}";
//描述
const tc_intro = "{0}({1})<small id='grade'>[{2}]</small>";
	
/**myspace主要部分*/
const creations = $.format(unis, "{0}", "完成了{1}类作品", "{2}", "{3}", "{4}");

const collections = $.format(unis, "{0}", "收藏了{1}类作品", "{2}", "{3}", "{4}");

const activities = "";

/**myspace辅助部分*/
//地址
const ms_addr = "{0}/dorest/{1}/detail/{2}";
//描述
const ms_intro = "{0}({1})";

var username = sessionStorage['highlight'];

$().ready(function(){
	$("ul.head").children("li").first().css("border", "none");
	load(getId());
	$("li.list-item").children("a").click(function(){
		let p = $(this).parent();
		p.siblings().removeClass('active');
		p.addClass('active');
		$("#editable").find("div.item").empty();
		load(getId());
	});
});

function getId(){
	return $("ul.menu").find("li.active").attr('id');//gradeDepart
}