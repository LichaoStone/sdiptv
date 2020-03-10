$(function(){
	                   
	$('#queryArea').click(function(e){
		initPage();
		$('#queryForm').submit();
	});	
	
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


function uploadFileFn() {
	$.ajaxFileUpload({
				url : window.ctx + '/equip/importArea.do', 
				dataType : 'text',
				secureuri : false,
				fileElementId : 'fileArea', // 文件选择框的id属性
				success : function(data, status, e) {
					var jsonData=$.parseJSON(data);
					alert(jsonData.msg);
					$('#fileArea').val('');
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


function updateAreaStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/equip/reverseArea.do?id='+id;
		if(!window.confirm("您确定要启用此区域！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/equip/closeArea.do?id='+id;
		if(!window.confirm("您确定要停用此区域！")){
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
	
	