const LIKE_BEFORE_CLASS = 'glyphicon-heart-empty';
const LIKE_AFTER_CLASS = 'glyphicon-heart';
const VIEW_BEFORE_CLASS = 'glyphicon-eye-close';
const VIEW_AFTER_CLASS = 'glyphicon-eye-open';

$().ready(function() {
	//设置tipoff
	$("#tipoff").css('color', '#ffc107');
	$("[name='solu']").hide();
	//设置like
	if(isCollected){
		$("#like").removeClass(LIKE_BEFORE_CLASS).addClass(LIKE_AFTER_CLASS);
	}
	$("#like").css('color', '#dc3545');
	$(document).on("click", "#like", function(){
		//防止频繁点击
		$("#like").attr('disabled', 'disabled');
		setTimeout(() => {$("#like").removeAttr('disabled');}, 30000);
		let obj = {};
		let th = $(this);
		obj['uid'] = uid;
		obj['cid'] = cid;
		obj['time'] = new Date().Format("yyyy-MM-dd HH:mm:ss");
		if($(this).hasClass(LIKE_BEFORE_CLASS)){
			//当前为未收藏,点击后为收藏
			$.post(ctx + "/amane/part/collect?type=Increase", obj, function(s){
				if(s.success == true && s.data.state == 1){
					th.removeClass(LIKE_BEFORE_CLASS).addClass(LIKE_AFTER_CLASS);
				};
			});
		}
		else{
			//当前为收藏,点击后为未收藏
			$.post(ctx + "/amane/part/collect?type=Decrease", obj, function(s){
				if(s.success == true && s.data.state == 1){
					th.removeClass(LIKE_AFTER_CLASS).addClass(LIKE_BEFORE_CLASS);
				};
			});
		}
	});
	$("#view").click(function(){
		if($(this).hasClass(VIEW_AFTER_CLASS)){
			//当前为睁眼
			$(this).removeClass(VIEW_AFTER_CLASS);
			$(this).addClass(VIEW_BEFORE_CLASS);
			$("[name='solu']").hide();
		}
		else{
			$(this).removeClass(VIEW_BEFORE_CLASS);
			$(this).addClass(VIEW_AFTER_CLASS);
			
			$("[name='solu']").show();
		}
	})
});
