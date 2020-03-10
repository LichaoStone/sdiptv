$(function(){
	                   
	$('#queryLoads').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
})

function delAppLoads($this, id){
	var url = window.ctx+ '/market/delAppLoads.do?id='+id;
	if(!window.confirm("您确定要删除此应用版本日下载信息！")){
		return false;
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}	
	