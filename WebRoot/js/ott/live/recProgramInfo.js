$(function() {

	var validator = $("#form1").validate({
				onsubmit : false,
				rules : {
					'indexSearch' : {
						required : true,
						maxlength: 16
					},
					'operators' : {
						required : true
					},
					'type' : {
						required : true
					},
					'channelId' : {
						required : true
					},
					'sequence' : {
						required : true,
						digits: true
					}
				},
				messages : { // 出错提示信息
					'indexSearch' : {
						required : "请填写节目名称",
						maxlength: "节目名称字数不能超过{0}"
					},
					'operators':"请选择运营商",
					'type' : "请选择类型",
					'channelId' : "请选择频道",
					'sequence' : {
						required:"请填写排列顺序",
						digits: "排序只能是数字"
					}
				},
				errorPlacement : $.handleError,
				success : $.handleSuccess,
				invalidHandler : $.invalidAlertHandler
			});
	var isSaving = false;
	// 单击保存按钮提交表单事件
	$('#saveRec').click(function(e) {
				if (isSaving) {
					return false;
				}
				if (validator.form()) {
					var url = '';
					isSaving = true;
					if ($('#id').val() == '') {
						url = window.ctx + '/rec/addRecProgram.do';
					} else {
						url = window.ctx + '/rec/updateRecProgram.do';
					}
					var $this = $(this);
					$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			
					var data = $('#form1').serializeArray();
					$.post(url, data, function(feedback) {
								alert(feedback.message);
								if (feedback.successful) {
									location.href=window.ctx+'/rec/findRecProgram.do'
								} else {
									$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
									isSaving = false;
								}
							});
				}
			});

	$('#logoFile').change(function(e) {
				new uploadPreview(this,{  DivShow: "preview", ImgShow: "imghead" }).show();
			});
	$('#saveLogoImg').click(function(e) {
				ajaxFileUpload("logoFile", $(this), "logoUrl")
			});
			
	$('#wlogoFile').change(function(e) {
				new uploadPreview(this,{  DivShow: "preview2", ImgShow: "imghead2" }).show();
			});
	$('#saveLogoImg2').click(function(e) {
				ajaxFileUpload("wlogoFile", $(this), "wlogoUrl")
			});
			
	$('#hlogoFile').change(function(e) {
				new uploadPreview(this,{  DivShow: "preview3", ImgShow: "imghead3" }).show();
			});
	$('#saveLogoImg3').click(function(e) {
				ajaxFileUpload("hlogoFile", $(this), "hlogoUrl")
			});

})

// 上传文件
function ajaxFileUpload(fileId, $this, imgId) {
	var f = $('#' + fileId).val();
	if (f == '') {
		$('#top-message').text('请选择文件!').fadeIn('slow').delay(5000)
				.fadeOut('slow');
		return;
	}
	$('#uploadImg').show();
	$this.prop('disabled', true).removeClass('bt-primary')
			.addClass('bt-secondary').text('上传中...');
	$.ajaxFileUpload({
				url : window.ctx
						+ '/programs/uploadProgramsFile.do?channelId=999999&fileId='
						+ fileId, // 需要链接到服务器地址
				fileElementId : fileId, // 文件选择框的id属性
				dataType : 'text',
				success : function(data, status, e) {
					var fb = $.parseJSON(data);
					$('#uploadImg').hide();
					if (fb.result == '1') {
						$('#' + imgId).val(fb.msg);
						$this.prop('disabled', true).removeClass('bt-primary')
								.addClass('bt-secondary').text('上传完成');
					} else {
						$('#top-message').text(fb.msg).fadeIn('slow')
								.delay(5000).fadeOut('slow');
						$this.prop('disabled', false).removeClass('bt-primary')
								.addClass('bt-secondary').text('上传');
					}
				},
				error : function(data, status, e) {
					$('#top-message').text("上传出错").fadeIn('slow').delay(5000)
							.fadeOut('slow')
					$('#uploadImg').hide();
					$this.prop('disabled', false).removeClass('bt-primary')
							.addClass('bt-secondary').text('上传');
				}
			});
}
