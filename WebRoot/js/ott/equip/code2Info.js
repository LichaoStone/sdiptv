$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	        'areaId': {
				required: true
			},
			'operators':{
			 	required: true	
			},
			'type':{
			 	required: true	
			},
			'keyName':{
			 	required:true
			},
			'sequence':{
			 	digits: true
			},
			'allCode':{
			 	required: true
			}
		},
		messages: { // 出错提示信息
			'areaId': {
				required: "请选择所属区域"
			},
			'operators':{
			 	required: "请选择运营商"	
			},
			'type':{
			 	required: "请选择设备类型"	
			},
			'keyName':{
			 	required: "请输入键值名"
			},
			'sequence':{
			 	digits: "排序只能是数字"
			},
			'allCode':{
			 	required: "请输入全码"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveCode').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/equip/addOperatorsCode.do';
			if($('#id').val() !=''){
				url = window.ctx+'/equip/updateOperatorsCode.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findOperatorsCode.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})

