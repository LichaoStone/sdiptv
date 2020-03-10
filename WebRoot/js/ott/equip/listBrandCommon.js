$(function(){
	                   
	$('#queryType').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	

	$('#delBrandCommon').click(function(e){
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
		var url =window.ctx+'/equip/delBrandCommonList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
})

function delBrandCommon($this, id){
	if(!window.confirm("您确定要删除此品牌类型推荐！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/delBrandCommon.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}	
	
	