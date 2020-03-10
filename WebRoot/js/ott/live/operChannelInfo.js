$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
			'operators':{
			 	required: true	
			},
			'channelId':{
			 	required: true	
			},
			'channelName':{
			 	required: true	
			},
			'playChannelId':{
			 	required:true,
			 	digits: true
			},
			'sequence':{
			 	digits: true
			}
		},
		messages: { // 出错提示信息
			'operators':{
			 	required: "请选择运营商"	
			},
			'channelId':{
			 	required: "请选择频道"	
			},
			'channelName':{
			 	required: "频道名称不能为空"	
			},
			'playChannelId':{
			 	required: "请输入地方频道号",
			 	digits: "频道号只能是数字"
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
	$('#saveOperChannel').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/oc/addOperatorsChannel.do';
			if($('#id').val() !=''){
				url = window.ctx+'/oc/updateOperatorsChannel.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/oc/findOperatorsChannel.do?operators='+$('select#operators option:selected').val();
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
	$('#operators').change(function(e){
		var operatorsName=$('select#operators option:selected').text();
		$('#operatorsName').val(operatorsName);
	})
	
	$('#channelId').change(function(e){
		var channelName=$('select#channelId option:selected').attr("data");
		$('#channelName').val(channelName);
	})
})

