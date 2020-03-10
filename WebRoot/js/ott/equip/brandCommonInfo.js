$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'brandId': {
				required: true
			},
			'machType':{
			 required: true	
			},
			'sequence':{
			 	digits: true
			}
		},
		messages: { // 出错提示信息
			'brandId': {
				required:'请选择所属品牌'
			},
			'machType':{
			    required:'请选择硬件类型'
			},
			'sequence':{
			 	digits: "排序只能是数字"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveBrandCommon').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/equip/addBrandCommon.do';
			if($('#id').val() !=''){
				url = window.ctx+'/equip/updateBrandCommon.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findBrandCommon.do';
					}else{
						alert(feedback.message)
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})
