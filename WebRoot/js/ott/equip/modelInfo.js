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
			},
			'number':{
			 required:true,
			 unique: {
				 	url: window.ctx+'/equip/checkModelByNumber.do',
				 	data:{
						brandId: function(){
							return $('select#brandId option:selected').val()
						},
						machType: function(){
							return $('select#machType option:selected').val()
						},
						oldNumber: function(){
							return $('#oldNumber').val()
						}
					}
				 }	
			}
			
		},
		messages: { // 出错提示信息
			'brandId': {
				required:'请选择所属品牌'
			},
			'machType':{
			    required:'请选择型号类型'
			},
			'sequence':{
			 	digits: "排序只能是数字"
			},
			'number':{
			     required:'请填写型号',
			     remote:"此型号在所属品牌已经正在使用，请填写新的型号"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveModel').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/equip/addHardModel.do';
			if($('#id').val() !=''){
				url = window.ctx+'/equip/updateHardModel.do';
			}
			var $this = $(this);
				$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findHardModel.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})
