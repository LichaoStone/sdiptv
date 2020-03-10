$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'remark': {
				required: true
			},
			'status': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'remark': {
				required:'请填写反馈描述'
			},
			'status': {
				required: "请选择反馈状态"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveFeedback').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/equip/updateFeedbackOperators.do';
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findFeedbackOperators.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})
