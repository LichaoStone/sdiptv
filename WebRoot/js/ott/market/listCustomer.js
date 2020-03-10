$(function(){
	                   
	$('#queryCustomer').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
})

function updateCustomerStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/market/reverseCustomer.do?id='+id;
		if(!window.confirm("您确定要启用此客服！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/market/closeCustomer.do?id='+id;
		if(!window.confirm("您确定要停用此客服！")){
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
function delCustomer($this, id){
	var url = window.ctx+ '/market/delCustomer.do?id='+id;
	if(!window.confirm("您确定要删除此客服！")){
		return false;
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}	
	