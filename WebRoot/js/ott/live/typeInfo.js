$(function(){
	
	var validator = $("#form1").validate({
		onsubmit : false, 
		rules: {
			'operators': {
				required: true
			},
			'name': {
				required: true,
				remote:{
							url : window.ctx + '/type/checkName.do',
							data : {
								'oldName' : function() {
									return $("#oldName").val();
								},
								'type':function(){
									return $('input:checked[name="type"]').val()
								}
							}
						}
			},
			'code': {
				required: true
			},
			'sequence': {
				required: true,
				digits: true
			}
		},
		messages: { // 出错提示信息
			'name': {required:"请填写名称",
					 remote:"此名称在此类型下已使用"
				},
			'code': "请填写代码",
			'operators': "请选择类型运营商",
			'sequence':{
				required:"请填写排序值",
				digits:"排序值只能是整数值"
			}
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
			var url = '';
			isSaving = true;
			if($('#id').val() == ''){
				 url= window.ctx+ '/type/addChannelType.do';
			}else{
				 url= window.ctx+ '/type/updateChannelType.do';
			}
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
				if(feedback.successful) {
					location.href=window.ctx+'/type/findChannelType.do'
				}else{
					$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
					isSaving = false;
				}
			});
		}
	});
	
})	