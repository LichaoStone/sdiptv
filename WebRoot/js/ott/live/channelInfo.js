$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'name': {
				required: true,
				remote:{
							url : window.ctx + '/channel/checkChannelByName.do',
							data : {
								'oldName' : function() {
									return $("#oldName").val();
								}
							}
						}
			}, 
			'parentId': {
				required: true
			}, 
			'remark': {
				maxlength: 200
			}
		},
		messages: { // 出错提示信息
			'name': {
				required:"请填写频道名称",
				remote: "频道名称已存在,请重新输入"
			},
			'parentId': "请选择所属频道",
			'remark': {
				maxlength:"频道说明字符长度不能超过{0}"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving = false;
	// 单击保存按钮提交表单事件
	$('#saveChannel').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = '';
			isSaving = true;
			if($('#id').val() == ''){
				 url= window.ctx+ '/channel/addChannel.do';
			}else{
				 url= window.ctx+ '/channel/updateChannel.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					location.href=window.ctx+ '/channel/findChannel.do'
				}else{
					$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
					isSaving = false;
				}
			});
		}
	});
	
	$('#logoFile').change(function(e){
		new uploadPreview(this,{  DivShow: "preview", ImgShow: "imghead" }).show();
	});
	
	$('#logoFile2').change(function(e){
		new uploadPreview(this,{  DivShow: "preview2", ImgShow: "imghead2" }).show();
	});
	
	$('#saveLogoImg').click(function(e){
		ajaxFileUpload("logoFile", $(this), "logoUrl")
	});
	
	$('#saveDescImg').click(function(e){
		ajaxFileUpload("logoFile2", $(this), "logoUrl2")
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
				url : window.ctx + '/programs/uploadProgramsFile.do?channelId=999999&fileId='+fileId, // 需要链接到服务器地址
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
					$('#top-message').text("上传出错").fadeIn('slow').delay(5000)
							.fadeOut('slow')
					$('#uploadImg').hide();
					$this.prop('disabled', false).removeClass('bt-primary')
							.addClass('bt-secondary').text('上传');
				}
	});
}


function changeChannel(obj, dom){
	if (obj.value == '') {
		jQuery('#' + dom + ' option').remove();
		jQuery('#' + dom)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		return;
	}
	var param={
		'parentId': obj.value
	}
	$.get(window.ctx + "/channel/changeChannel.do" , param,
			function(res) {
				if (res == null) {
					return;
				}
				jQuery('#' + dom + ' option').remove();
				jQuery('#' + dom)
						.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
				for (var c in res) {
					jQuery("#" + dom).append("<option value='" + res[c].id
							+ "'>" + res[c].name + "</option>");
				}
			});
	return false;
}