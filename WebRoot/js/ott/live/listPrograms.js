$(function(){
	$('#queryPrograms').click(function(e) {
		initPage();
		$('#queryForm').submit();
	});
	
	$('#inputExcel').click(function(e) {
		var f = $('#filePrograms').val();
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
	
	$('#delPrograms').click(function(e){
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
		var url =window.ctx+'/programs/delProgramsList.do?ids='+ptId;
		$.post(url, function(feedback){
			alert(feedback.message);
			if(feedback.successful){
				location.reload();
			}
		})
	})
	
})

function changePrograms($this, id, status){
	var url = "";
	if(status =='1'){
		url = window.ctx+ '/programs/programsUnusual.do?id='+id;
		if(!window.confirm("您确定要此节目设置为异常！")){
			return false;
		}
	}else if(status == '2'){
		url = window.ctx+ '/programs/programsNormal.do?id='+id;
		if(!window.confirm("您确定要此节目异常恢复！")){
			return false;
		}
	}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}

function uploadFileFn() {
	$.ajaxFileUpload({
				url : window.ctx + '/programs/importPrograms.do', 
				dataType : 'text',
				fileElementId : 'filePrograms', // 文件选择框的id属性
				success : function(data, status, e) {
					var jsonData=$.parseJSON(data);
					alert(jsonData.msg);
					$('#filePrograms').val('');
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

function tobProgram($this,cid,name){
	var retVal = openShowModal(window.ctx+ '/bprogram/toBProgram.do?cid='+cid+'&name='+name, 1000, 800);
	retVal && location.reload();
}

function toAddBProgram($this){
	var retVal = openShowModal(window.ctx+ '/bprogram/toAddBProgram.do', 1000, 800);
	retVal && location.reload();
}

function toUpdateBProgram($this, id){
	var retVal = openShowModal(window.ctx+ '/bprogram/toUpdateBProgram.do?id='+id, 1000, 800);
	retVal && location.reload();
}

function toCrawlProgram($this,cid,name){
	var retVal = openShowModal(window.ctx+ '/programs/toCrawlPrograms.do', 600, 300);
	retVal && location.reload();
}

function toProgram($this,id){
	var retVal = openShowModal(window.ctx+ '/programs/toPrograms.do?cid='+id, 1000, 720);
	retVal && location.reload();
}