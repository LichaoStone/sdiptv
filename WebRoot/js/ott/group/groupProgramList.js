$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'groupCodes': {
				required: true
			}
		},
		messages: { // 出错提示信息
			'groupCodes': "请选择用户组"
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
		var nodes = checkNodeFn();
		$('#groupCodes').val(nodes.join(','));
		if (validator.form()) {
			isSaving = true;
			var url= window.ctx+ '/group/saveGroupProgramList.do';
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					location.href=window.ctx+'/rec/findRecProgram.do'
				}else{
					$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
					isSaving = false;
				}
			});
		}
	});
	
	$('#all_code').click(function(e) {
		if ($(this).prop("checked")) {
			$('input[name="nodes"]').attr("checked", true);
		} else {
			$('input[name="nodes"]').attr("checked", false);
		}
	})
})	


function checkNodeFn() {
	var $nodes = $('input:checked[name="nodes"]');
	var array = [];
	if ($nodes && $nodes.length > 0) {
		for (var i = 0; i < $nodes.length; i++) {
			array.push($($nodes[i]).val());
		}
	}
	return array;
}
