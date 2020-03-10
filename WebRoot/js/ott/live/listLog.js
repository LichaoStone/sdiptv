$(function(){
	$('#queryLog').click(function(e) {
		initPage();
		$('#queryForm').submit();
	});
	
	$('#delLogs').click(function(e){
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
		var url =window.ctx+'/search/delLogList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
})

function toSNumber($this, virtualNumber, id){
	var retVal = openShowModal(window.ctx+ '/search/toSNumber.do?id='+id+'&virtualNumber='+virtualNumber, 500, 300);
	retVal && location.reload();
}

function delSearch($this, id){
	var url = url = window.ctx+ '/search/delSearch.do?id='+id;
	if(!window.confirm("您确定要删除此热门词！")){
			return false;
		}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}

function updateLogStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/search/searchLogReverse.do?id='+id;
		if(!window.confirm("您确定要启用此热门内容！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/search/searchLogClose.do?id='+id;
		if(!window.confirm("您确定要停用此热门内容！")){
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