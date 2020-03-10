$(function(){
	                   
	$('#queryFeedback').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
	$('#delFeedback').click(function(e){
		var $ptId = $('input:checked[name="arcID"]');
		if($ptId.length <=0){
			alert('请选择要删除的选项');
			return;
		}
		if(!window.confirm("您确定要删除您选择的选项！")){
			return false;
		}
		var ptIds = [];
		for (i = 0; i < $ptId.length; i++) {
				ptIds.push($($ptId[i]).val());
		}
		var ptId = ptIds.join(",");
		var url =window.ctx+'/equip/delFeedbackOperatorsList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
	
})

function toFeedback($this, id){
	openShowModal(window.ctx+ '/equip/toFeedbackOperators.do?id='+id, 800, 700);
}

function updateModelStatus($this, id){
	if(!window.confirm("您确定已完成处理此反馈！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/updateFeedbackOperatorsStatus.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}	


function delFeedback($this, id){
	if(!window.confirm("您确定要删除此反馈！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/delFeedbackOperators.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}

function changeOperators(obj, dom) {
	if (obj.value == '') {
		jQuery('#' + dom + ' option').remove();
		jQuery('#' + dom)
				.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
		return;
	}
	var param={
		'areaId': obj.value
	}
	$.get(window.ctx + "/equip/changeOperators.do" , param,
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