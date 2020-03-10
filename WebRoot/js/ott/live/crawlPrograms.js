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
	$('#saveCrawl').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+ '/programs/crawlPrograms.do';
			isSaving = true;
			var data = {
				'createFile':$('input:checked[name="createFile"]').val(),
				'startDate': $('#startDate').val(),
				'count': $('#count').val(),
				'type': $('select#type option:selected').val()
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('抓取中...').prop('disabled', true);
			$('#uploadImg').show();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				$('#uploadImg').hide();
				if(feedback.successful) {
					window.returnValue = true;
					window.close();
				}else{
					$this.removeClass('bt-secondary').addClass('bt-primary').text('抓取').prop('disabled', false);
					isSaving = false;
				}
			});
		}
	});
	
})	