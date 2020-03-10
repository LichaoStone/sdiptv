$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'virtualNumber': {
				required: true,
				digits: true
			}
		},
		messages: { // 出错提示信息
			'virtualNumber': {
				required:"请输入干预频道值",
				digits: "干预频道值只能是整数数字"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving = false;
	// 单击保存按钮提交表单事件
	$('#saveNumber').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = url= window.ctx+ '/hot/updateChannelNumber.do';
			isSaving = true;
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					window.returnValue = true;
					window.close();
				}else{
					isSaving = false;
				}
			});
		}
	});
	
})	