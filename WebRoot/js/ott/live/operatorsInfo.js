$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'name': {
				required: true
			},
			'fullName':{
			 required: true	
			},
			'code':{
			 required:true
			},
			'sequence':{
				digits: true
			}
		},
		messages: { // 出错提示信息
			'name': {
				required:'请填写运营商名称'
			},
			'fullName':{
			    required:'请填写运营商全称'
			},
			'code':{
			     required:'请填写运营商编码'
			},
			'sequence':{
			 	digits: "排序只能是数字"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#saveOperators').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url = window.ctx+'/live/addCityOperators.do';
			if($('#id').val() !=''){
				url = window.ctx+'/live/updateCityOperators.do';
			}
			var $this = $(this);
				$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					if(feedback.successful) {
						location.href =window.ctx+'/live/findCityOperators.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
})

function changeArea(obj, dom) {
	if (obj.value == '') {
		jQuery('#' + dom + ' option').remove();
		jQuery('#' + dom)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		return;
	}
	var param={
		'parentId': obj.value
	}
	$.get(window.ctx + "/equip/changeArea.do" , param,
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