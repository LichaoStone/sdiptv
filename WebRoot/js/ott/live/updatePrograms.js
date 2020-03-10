$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'type':{
				required: true
			},
			'startDate': {
				required: true
			}, 
			'count': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'type': {required:"请选择抓取地址"},
			'startDate': {required:"请选择开始日期"},
			'count': "请选择抓取范围"
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	var isSaving = false;
	// 单击保存按钮提交表单事件
	$('#saveType').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+ '/channel/updatePrograms.do';
			isSaving = true;
			var data = {
				'id':$('#id').val(),
				'startDate': $('#startDate').val(),
				'count': $('#count').val(),
				'type': $('select#type option:selected').val(),
				'createFile':$('input:checked[name="createFile"]').val(),
				'afterNow':$('input:checked[name="afterNow"]').val()
			}
			$('#uploadImg').show();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				$('#uploadImg').hide();
				if(feedback.successful) {
					window.returnValue = true;
					window.close();
				}else{
					isSaving = false;
				}
			});
		}
	});
	
})	