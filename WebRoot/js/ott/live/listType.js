$(function(){
	$('#toAddChannelType').click(function(e){
		var retVal = openShowModal(window.ctx+ '/type/toChannelType.do', 500, 300);
		retVal && location.reload();
	});
})

function updateTypeStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/type/channelTypeReverse.do?id='+id;
		if(!window.confirm("您确定要启用此频道类型！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/type/channelTypeClose.do?id='+id;
		if(!window.confirm("您确定要停用此频道类型！")){
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

function toUpdateType($this, id){
//	var retVal = openShowModal(window.ctx+ '/type/toChannelType.do?id='+id, 500, 300);
//	retVal && location.reload();
	location.href=window.ctx+ '/type/toChannelType.do?id='+id;
}