$(function(){
	var validator = $("#form0").validate({
		onsubmit : false, 
		rules: {
					'groupName': {
						required: true,
						maxlength: 32,
					},
					'groupCode': {
						required: true,
						maxlength: 16,
					},
					'operators': {
						required: true,
					}
		},
		messages: { // 出错提示信息
					'groupName': {
						required: "用户组名必填" ,
						maxlength: "用户组名不能超过{0}",
					},
					'groupCode': {
						required: "用户组编码必填" ,
						maxlength: "用户组编码不能超过{0}",
					},
					'operators': {
						required: "请选择运营商" 
					}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving = false;
	// 单击保存按钮提交表单事件
	$('#save-btn').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url= window.ctx+ '/group/saveUserGroup.do';
			var $this = $(this);
			var nodes = checkNodeFn();
			$('#groupChannels').val(nodes.join(','));
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form0').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					location.href=window.ctx+'/group/findUserGroup.do?operators='+$('select#operators option:selected').val()
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
			$('#all_code_label').text('取消所有');
		} else {
			$('input[name="nodes"]').attr("checked", false);
			$('#all_code_label').text('勾选所有');
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