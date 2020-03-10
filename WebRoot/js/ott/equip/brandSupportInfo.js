$(function(){

	var validator = $("#brandForm").validate({
		onsubmit : true, 
		rules: {
	       'name': {
				 required: true	,
				 remote: {
					 	url: window.ctx+'/equip/checkBrandSupportByName.do',
					 	data: {
					 		'oldName': function(){
								return $('#oldName').val()
							}
					 	}
					 }	
			},
			'supportModel':{
			 	required: true	
			},
			'sequence':{
			 	required: true,
			 	digits: true
			}
		},
		messages: { // 出错提示信息
		   'name':{
			    required:'请填写品牌名称',
			    remote:"此品牌名称已经正在使用，请重新填写"
			},
			'supportModel': {
				required:'请填写厂商全称'
			},
			'sequence': {
				required:'请填写编码',
				digits: "排序只能是数字"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving = false;
	$('#saveBrand').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
		var url = window.ctx+'/equip/addBrandSupport.do';
		if($('#id').val() !=''){
			url = window.ctx+'/equip/updateBrandSupport.do';
		}
		var $this = $(this);
		$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
		var data = $('#brandForm').serializeArray();
		$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findBrandSupport.do';
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
	
	$('#saveLogoImg').click(function(e){
		ajaxFileUpload("logoFile", $(this), "logo")
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
				url : window.ctx + '/common/uploadFile.do?fileId='+fileId, // 需要链接到服务器地址
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