$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'recordTime': {
				required: true,
				remote: {
				 	url: window.ctx+'/market/checkAppLoadsByDate.do',
				 	data:{
						marketId: function(){
							return $('#marketId').val()
						}
					}
				 }
			},
			'dayDownLoads': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'recordTime': {
				required:'请选择日期',
				remote:"此日期下已有下载量信息，请重新选择日期"
			},
			'dayDownLoads': {
				required:'请填写下载量'
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveAppLoads').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/market/addAppLoads.do';
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/market/findAppLoads.do?marketId='+$('#marketId').val();
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
})
