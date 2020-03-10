$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'name': {
				required: true
			},
			'channelId' : {
						required : true
			},
			'basicName':{
				required: true
			},
			'playTime':{
				required: true
			},
			'timeLongth':{
				required: true,
				digits: true
			},
			'queue':{
				digits: true
			}
		},
		messages: { // 出错提示信息
			'name': {
				required:'请填写节目名称'
			},
			'channelId' :{
				required:"请选择频道"
			},
			'playTime':{
				required: "请选择播放时间"
			},
			'basicName':{
				required: '请填写基础名称'
			},
			'timeLongth':{
				required: '请填写播放时长',
				digits: '播放时长只能是数字'
			},
			'queue':{
				digits: '节目剧集只能是数字'
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	var isSaving = false;
	
	// 单击保存按钮提交表单事件
	$('#savePrograms').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			if($('#queue').val()==''){
				$('#queue').val(0)
			}
			isSaving = true;
			
			var url= window.ctx+ '/programs/updatePrograms.do';
			if($('#id').val() == ''){
				 url= window.ctx+ '/programs/addPrograms.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					location.href=window.ctx+ '/programs/findPrograms.do'
				}else{
					$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
					isSaving = false;
				}
			});
		}
	});
	
	$('#saveLogoImg').click(function(e){
		ajaxFileUpload("logoFile", $(this), "logoImgUrl")
	});
	
	$('#saveDescImg').click(function(e){
		ajaxFileUpload("descImgFile", $(this), "descImgUrl")
	});
	
	$('#logoFile').change(function(e){
		new uploadPreview(this,{  DivShow: "preview", ImgShow: "imghead" }).show();
	});
	
	$('#descImgFile').change(function(e){
		new uploadPreview(this,{  DivShow: "preview2", ImgShow: "imghead2" }).show();
	});
})	
	
// 上传文件
function ajaxFileUpload(fileId, $this, imgId) {
	var f = $('#'+fileId).val();
	if (f == '') {
		$('#top-message').text('请选择文件!').fadeIn('slow').delay(5000)
				.fadeOut('slow');
		return;
	}
	$('#uploadImg').show();
	$this.prop('disabled', true).removeClass('bt-primary')
			.addClass('bt-secondary').text('上传中...');
	$.ajaxFileUpload({
				url : window.ctx + '/bprogram/uploadBasicProgramFile.do?channelId=999999&fileId='+fileId, // 需要链接到服务器地址
				fileElementId : fileId, // 文件选择框的id属性
				dataType : 'text',
				success : function(data, status, e) {
					var fb =$.parseJSON(data);
					$('#uploadImg').hide();
					if(fb.result=='1') {
						$('#'+imgId).val(fb.msg);
						$this.prop('disabled', true).removeClass('bt-primary')
							.addClass('bt-secondary').text('上传完成');
					}else{
						$('#top-message').text(fb.msg).fadeIn('slow').delay(5000)
							.fadeOut('slow');
						$this.prop('disabled', false).removeClass('bt-primary')
							.addClass('bt-secondary').text('上传');
					}
				},
				error : function(data, status, e) {
					$('#top-message').text("图片上传出错").fadeIn('slow').delay(5000)
							.fadeOut('slow')
					$('#uploadImg').hide();
					$this.prop('disabled', false).removeClass('bt-primary')
							.addClass('bt-secondary').text('上传');
				}
	});
}