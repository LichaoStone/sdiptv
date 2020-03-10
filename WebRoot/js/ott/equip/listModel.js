$(function(){
	                   
	$('#queryType').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
$('#delModel').click(function(e){
		var $ptId = $('input:checked[name="arcID"]');
		if($ptId.length <=0){
			alert('请选择要删除的品牌型号选项');
			return;
		}
		if(!window.confirm("您确定要删除您选择的品牌型号选项,同时删除此品牌型号下的红外码！")){
			return false;
		}
		var ptIds = [];
		for (i = 0; i < $ptId.length; i++) {
				ptIds.push($($ptId[i]).val());
		}
		var ptId = ptIds.join(",");
		var url =window.ctx+'/equip/delModelList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
})

function delModel($this, id){
	if(!window.confirm("您确定要删除此品牌型号！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/delModel.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}

function toInfraredCode($this,brandId,type,modelName){
	openShowModal(window.ctx+ '/equip/findInfraredCode.do?brandId='+brandId+'&type='+type+'&modelName='+modelName, 1080, 700);
}

function updateModelStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/equip/hardModelReverse.do?id='+id;
		if(!window.confirm("您确定要启用此型号！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/equip/hardModelClose.do?id='+id;
		if(!window.confirm("您确定要停用此型号！")){
			return false;
		}
	}else if(status == '2'){
		url = window.ctx+ '/equip/hardModelPass.do?id='+id;
		if(!window.confirm("您确定已完善此型号！")){
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
	
	