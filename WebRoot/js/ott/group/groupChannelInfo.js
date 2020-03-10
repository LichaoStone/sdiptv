$(function(){

	var validator = $("#form0").validate({
		onsubmit : true, 
		rules: {
			'operators':{
			 	required: true	
			},
			'groupCode':{
			 	required: true	
			},
			'channelName':{
			 	required: true	
			},
			'ochannelId':{
				required: true	
			},
			'localCid':{
				required:true,
			 	digits: true
			},
			'sequence':{
				required:true,
			 	digits: true
			}
		},
		messages: { // 出错提示信息
			'operators':{
			 	required: "请选择运营商"	
			},
			'groupCode':{
			 	required: "请选择用户组"	
			},
			'channelName':{
			 	required: "频道名称不能为空"	
			},
			'ochannelId':{
				required: "请选择频道"	
			},
			'localCid':{
			 	required: "请输入频道号",
			 	digits: "频道号只能是数字"
			},
			'sequence':{
			 	required: "请输入排序",
			 	digits: "排序只能是数字"
			}
		},
		errorPlacement: $.handleError,
		success: $.handleSuccess,
		invalidHandler: $.invalidAlertHandler
	});
	
	var isSaving= false
	$('#save-btn').click(function (e) { 
		if (isSaving) {
			return false;
		}
		if (validator.form()) {
			var url= window.ctx+ '/groupChannel/saveGroupChannel.do';
			var $this = $(this);
			$this.removeClass('bt-primary').addClass('bt-secondary').text('保存中...').prop('disabled', true);
			var data = $('#form0').serializeArray();
			$.post(url, data, function(feedback) {
				alert(feedback.message);
					if(feedback.successful) {
						location.href=window.ctx+'/groupChannel/findGroupChannel.do?operators='+$('select#operators option:selected').val()+'&groupCode='+$('select#groupCode option:selected').val();
					}else{
						$this.removeClass('bt-secondary').addClass('bt-primary').text('保存').prop('disabled', false);
						isSaving = false;
					}
				});
		}
	});
	
	$('#ochannelId').change(function(e){
		var channelName=$('select#ochannelId option:selected').attr("data");
		$('#channelName').val(channelName);
		var localCid=$('select#ochannelId option:selected').attr("data2");
		$('#localCid').val(localCid);
	})
	
})

function changeOperators(obj, dom, dom2){
	if (obj.value == '') {
		jQuery('#' + dom + ' option').remove();
		jQuery('#' + dom)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		
		jQuery('#' + dom2 + ' option').remove();
		jQuery('#' + dom2)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		return;
	}
	var param={
		'operators': obj.value
	}
	$.getJSON(window.ctx + "/group/changeUserGroup.do" , param,
			function(res) {
				if (res == null) {
					return;
				}
				$('#' + dom + ' option').remove();
				$('#' + dom).append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
				$.each(res, function(i,group) {
					 $("#" + dom).append("<option data='"+group.groupName+"' value='" + group.groupCode
							 + "'>" + group.groupName+ "</option>");
				});
	});
	
	$.getJSON(window.ctx + "/oc/changeOperatorsChannel.do" , param,
			function(res) {
				if (res == null) {
					return;
				}
				$('#' + dom2 + ' option').remove();
				$('#' + dom2).append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
				$.each(res, function(i,channel) {
					 $("#" + dom2).append("<option data='"+channel.channelName+"' data2='"+channel.playChannelId+"' value='" + channel.id
							 + "'>" + channel.channelName+ "</option>");
				});
	});
	return false;
}