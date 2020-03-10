$(function(){

	var validator = $("#form1").validate({
		onsubmit : true, 
		rules: {
	       'name': {
				required: true
			},
			'areaId':{
			 required: true	
			},
			'code':{
			 required:true,
			 unique: {
				 	url: window.ctx+'/equip/checkOperatorsByCode.do',
				 	data:{
						oldCode: function(){
							return $('#oldCode').val()
						}
					}
				 }	
			},
			'spName':{
				required: true
			},
			'sequence':{
			 	digits: true
			}
		},
		messages: { // 出错提示信息
			'areaId':{
				required: '请选择区域'
			},
			'name': {
				required:'请填写运营商红外方案'
			},
			'code':{
			     required:'请填写运营商红外方案编码',
			     remote:"此运营商红外方案编码已经正在使用，请填写新的编码"
			},
			'spName':{
			    required:'请填写运营商名称型'
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
			var url = window.ctx+'/equip/addOperators.do';
			if($('#id').val() !=''){
				url = window.ctx+'/equip/updateOperators.do';
			}
			var $this = $(this);
				$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form1').serializeArray();
			$.post(url, data, function(feedback) {
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findOperators.do?areaId='+$('select#areaId option:selected').val();
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
	$('#cityOperators').change(function(e){
		var operatorsName=$('select#cityOperators option:selected').text();
		$('#otherName').val(operatorsName);
		$('#span_operators').text(operatorsName);
	})
	
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

function changeCityOperators(obj, dom){
	
	if (obj.value == '') {
		jQuery('#' + dom + ' option').remove();
		jQuery('#' + dom)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		return;
	}
	var param={
		'areaId': obj.value
	}
	$.get(window.ctx + "/live/changeCityOperators.do" , param,
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