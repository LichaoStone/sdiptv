$(function(){
	                   
	$('#queryOperators').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
	$('#delOperators').click(function(e){
		var $ptId = $('input:checked[name="arcID"]');
		if($ptId.length <=0){
			alert('请选择要删除的红外方案选项');
			return;
		}
		if(!window.confirm("您确定要删除您选择的红外方案选项,同时删除此红外方案的红外码！")){
			return false;
		}
		var ptIds = [];
		for (i = 0; i < $ptId.length; i++) {
				ptIds.push($($ptId[i]).val());
		}
		var ptId = ptIds.join(",");
		var url =window.ctx+'/equip/delOperatorsList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
})
function delOperators($this, id){
	if(!window.confirm("您确定要删除此红外方案,同时删除此红外方案下的红外码！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/delOperators.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}	

function toInfraredCode($this,operators,areaId){
	openShowModal(window.ctx+ '/equip/findOperatorsCode.do?operators='+operators+'&areaId='+areaId, 1080, 700);
}

function toChannel($this,operators,areaId){
	openShowModal(window.ctx+ '/oc/findOperatorsChannel.do?operators='+operators+'&areaId='+areaId, 1080, 700);
}

function updateOperatorsStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/equip/reverseOperators.do?id='+id;
		if(!window.confirm("您确定要启用此红外方案！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/equip/closeOperators.do?id='+id;
		if(!window.confirm("您确定要停用此红外方案！")){
			return false;
		}
	}else if(status == '2'){
		url = window.ctx+ '/equip/passOperators.do?id='+id;
		if(!window.confirm("您确定已完善此红外方案！")){
			return false;
		}
	}else{
		return;
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
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