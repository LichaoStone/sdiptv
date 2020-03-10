$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	        'appName': {
				required: true
			},
			'marketName': {
				required: true
			},
			'grade': {
				required: true
			},
			'marketAttr': {
				required: true
			},
			'site': {
				required: true
			},
			'partnerQQ':{
				digits:true
			},
			'partnerTel':{
				phoneCN:true
			},
			'partnerEmail':{
				email:true
			},
			'version': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'appName': {
				required:'请填写应用名称'
			},
			'marketName': {
				required:'请填写应用市场名称'
			},
			'grade': {
				required:'请选择平台等级'
			},
			'marketAttr': {
				required:'请填写平台属性'
			},
			'site': {
				required:'请填写网址'
			},
			'partnerQQ':{
				digits:"QQ号只能是数字"
			},
			'partnerTel':{
				phoneCN:"电话格式不正确"
			},
			'partnerEmail':{
				email:"邮箱格式不正确"
			},
			'version': {
				required:'请填写发布版本号'
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveAppMarket').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/market/addAppMarket.do';
			if($('#id').val() !=''&& $('#isUp').val() ==''){
				url = window.ctx+'/market/updateAppMarket.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/market/findAppMarket.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
})
