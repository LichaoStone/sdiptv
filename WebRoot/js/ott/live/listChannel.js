$(function(){
	$('#toAddChannel').click(function(e){
		var retVal = openShowModal(window.ctx+ '/type/toChannel.do', 1000, 700);
		retVal && location.reload();
	});
	
	$('#queryChannel').click(function(e) {
		initPage();
		$('#queryForm').submit();
	});
})

function updateChannelStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/channel/channelReverse.do?id='+id;
		if(!window.confirm("您确定要启用此频道！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/channel/channelClose.do?id='+id;
		if(!window.confirm("您确定要停用此频道！")){
			return false;
		}
	}else if(status == '2'){
		url = window.ctx+ '/channel/channelPass.do?id='+id;
		if(!window.confirm("您确定已完善此频道！")){
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

function delChannel($this, id){
	var url = window.ctx+ '/channel/delChannel.do?id='+id;
	if(!window.confirm("您确定要删除此频道！")){
		return false;
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}
	
function toUpdateChannel($this, id){
	location.href=window.ctx+ '/channel/toChannel.do?id='+id;
}

function toUpdatePrograms($this, id){
	openShowModal(window.ctx+ '/channel/toUpdate.do?id='+id, 500, 300);
}

function updatePrograms($this, id, type){
	if(!window.confirm("您确定要更新此频道当前时间后的节目单！")){
		return false;
	}
	var url = window.ctx+'/channel/updateChannelOfPro.do?id='+id+"&type="+type;
	$('#uploadImg').show();
	$.post(url, function(feedback){
		$('#uploadImg').hide();
		alert(feedback.message);
	})
}