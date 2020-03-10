$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'name': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'name': {
				required:'请填写区域名称'
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveArea').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/equip/addArea.do';
			if($('#id').val() !=''){
				url = window.ctx+'/equip/updateArea.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findArea.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})
