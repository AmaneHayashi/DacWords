var options = {
	type : 'POST', // 设置表单提交方式
	url : ctx + "/amane/part/creation/step2", // 设置表单提交URL,默认为表单Form上action的路径
	async : false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
	dataType : 'json', // 返回数据类型
	success : function(s) { // 成功后的回调函数(返回数据由responseText获得)
		/* 成功后的操作 */
		//console.log(s);
		if(s.success == true && s.data.state == 1){
			window.location.replace(ctx + '/creation/contents');
		}
		else{
			$("#nextstep").removeAttr('disabled');
			$("#fail").css('display', 'inline');
			$("#fail").text("提交失败!失败原因:" + s.msg);
		}
	},
	error : function(xhr, status, err) {
		$("#nextstep").removeAttr('disabled');
		$("#fail").css('display', 'inline');
		$("#fail").text("提交失败!失败代码" + xhr.status);
	}
// 成功提交后，重置表单填写内容
};

$().ready(function() {
	/**获得ID命名规则*/
	$("#subtitle").text(subtitle);
	$("#cform").change(function(){
		$.draft("cform", 2);
	});
	$("#read_draft").click(function(){
		$.redraft("cform", 2, null);
	})
});

/**BUG：设置session存储的话，会出现creation的覆盖*/

/**BUG：设置session存储的话，会出现creation的覆盖*/
function selectify(obj){
	let i = 0;
	//第一步,添加爷爷列表
	while(obj[100 * (++i)] != undefined){
		$("#c-field").append("<option value='" + (i) + "'>" + obj[100 * i] + "</option>")
	}
	//第二步,设置父亲
	$('#c-field').change(function(){
		$.chain(obj, 0, "c-field", "c-subject", 100, 10);
	});
}

function generateName(){
	//种类（2位）·领域（2位）·科目（8位）
	//kind·field·subject
	return $.format("{0}·{1}·{2}", kind, $.value("c-field"), $.value("c-subject"));
}

function generateID(){
	//we set a pseudo common formatter as tformatEN to ensure the formats of each kinds
	let formatter = formatterMap['tformatEN'].split('·');
	let str = abbrMap[kind];
	for(let i = 1; i < formatter.length - 1; i ++){
		str += abbrMap[$.value("c-" + formatter[i])];
	}
	//如果最后一项不是数字
	let last = formatter[formatter.length - 1];
	let dv = new Date($("#c-ppstime").val());
	if(kind == '考研'){
		dv.setFullYear(dv.getFullYear() + 1);
	}
	str += isNaN(last) ? dv.Format(last) : "";
	return str;
}