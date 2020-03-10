$(function(){
	                   
	$('#queryCode').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
	$('#delCode').click(function(e){
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
		var url =window.ctx+'/equip/delCodeList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
	
	$('#inputExcel').click(function(e) {
		var f = $('#fileCode').val();
		if (f == '') {
			$('#top-message').text('请选择文件!').fadeIn('slow').delay(5000)
					.fadeOut('slow');
			$(this).prop('disabled', true).removeClass('bt-primary')
				.addClass('bt-secondary');
			return;
		}
		$('#uploadImg').show();
		$(this).prop('disabled', true).removeClass('bt-primary')
				.addClass('bt-secondary').text('上传中...');
		uploadFileFn();
	});
})

function updateCodeStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/equip/codeReverse.do?id='+id;
		if(!window.confirm("您确定要启用此红外码！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/equip/codeClose.do?id='+id;
		if(!window.confirm("您确定要停用此红外码！")){
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
	
function delCode($this, id){
	if(!window.confirm("您确定要删除此红外码！")){
		return false;
	}
	$.ajax({
         type: "POST",
         url: window.ctx+"/equip/delCode.do?id="+id,
         success: function(feedback){
             alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
         }
     });
}	

function uploadFileFn() {
	$.ajaxFileUpload({
				url : window.ctx + '/equip/importInfraredCode.do', 
				dataType : 'text',
				secureuri : false,
				fileElementId : 'fileCode', // 文件选择框的id属性
				success : function(data, status, e) {
					var jsonData=$.parseJSON(data);
					alert(jsonData.msg);
					$('#fileCode').val('');
					$('#inputExcel').prop('disabled', false)
							.removeClass('bt-secondary').addClass('bt-primary')
							.text('导入')
					$('#uploadImg').hide();
					location.reload();
				},
				error : function(data, status, e) {
					alert("上传出错");
					$('#inputExcel').prop('disabled', false)
							.addClass('bt-primary')
					$('#uploadImg').hide();
				}
			});
}

function checkFile($this, name) {
	if (/^.+\.(xls)$/i.test(name.toLowerCase())
			|| /^.+\.(xlsx)$/i.test(name.toLowerCase())) {
		$('#inputExcel').prop('disabled', false).removeClass('bt-secondary')
				.addClass('bt-primary');
	} else {
		$('#top-message').text('你选择的文件格式不正确,请重新选择Excel文件!').fadeIn('slow')
				.delay(5000).fadeOut('slow');
		$('#inputExcel').prop('disabled', true).removeClass('bt-primary')
				.addClass('bt-secondary');
	}
}