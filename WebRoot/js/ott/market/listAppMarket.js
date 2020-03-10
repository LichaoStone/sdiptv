$(function(){
	                   
	$('#queryMarket').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
})

function delAppMarket($this, id){
	var url = window.ctx+ '/market/delAppMarket.do?id='+id;
	if(!window.confirm("您确定要删除此登陆应用市场信息！")){
		return false;
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}	
	