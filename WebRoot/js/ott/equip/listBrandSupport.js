
function updateBrandStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/equip/reverseBrandSupport.do?id='+id;
		if(!window.confirm("您确定要启用此红外手机品牌！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/equip/closeBrandSupport.do?id='+id;
		if(!window.confirm("您确定要停用此红外手机品牌！")){
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
	