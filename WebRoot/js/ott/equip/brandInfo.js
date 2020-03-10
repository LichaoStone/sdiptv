$(function(){

	var validator = $("#brandForm").validate({
		onsubmit : true, 
		rules: {
	       'name': {
			 required: true	,
			 remote: {
				 	url: window.ctx+'/equip/checkBrandName.do',
				 	data: {
				 		'oldName': function(){
							return $('#oldName').val()
						}
				 	}
				 }	
			},
			'factory':{
			 required: true	
			},
			'code':{
			 required: true	
			}
		},
		messages: { // 出错提示信息
		   'name':{
			    required:'请填写品牌名称',
			    remote:"此品牌名称已经正在使用，请重新填写"
			},
			'factory': {
				required:'请填写厂商全称'
			},
			'code': {
				required:'请填写编码'
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
		var url = window.ctx+'/equip/addBrand.do';
		if($('#id').val() !=''){
			url = window.ctx+'/equip/updateBrand.do';
		}
		var $this = $(this);
		$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
		var nodes = checkNodeFn();
		$('#recType').val(nodes.join(','));	
		
		var data = $('#brandForm').serializeArray();
		$.post(url, data, function(feedback) {
					alert(feedback.message);
					if(feedback.successful) {
						location.href =window.ctx+'/equip/findBrand.do';
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
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
