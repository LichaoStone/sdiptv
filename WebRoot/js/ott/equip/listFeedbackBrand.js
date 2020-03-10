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
		var url =window.ctx+'/equip/delFeedbackBrandList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
	
})
function toFeedback($this, id){
	openShowModal(window.ctx+ '/equip/toFeedbackBrand.do?id='+id, 800, 700);
}

function updateModelStatus($this, id){
	if(!window.confirm("您确定已完成处理此反馈！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/updateFeedbackStatus.do?id="+id,
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
         url: window.ctx+"/equip/delFeedbackBrand.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}