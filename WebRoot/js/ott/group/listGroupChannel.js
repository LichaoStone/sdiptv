$(function(){
	                   
	$('#queryGroupChannel').click(function(e) {
		initPage();
		$('#queryForm').submit();
	});
	
	$('#inputExcel').click(function(e) {
		var f = $('#fileOperChannel').val();
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
	
	
	$('#list-table td input[name="sequence"]').change(function(e){
		  var $this=$(this);
		  var id=$this.attr('data-id');
		  var reCat = new RegExp("^[0-9]*$"); 
		  var sequence=$this.val();
		  if(reCat.test(sequence)&&sequence.length<5){
			  var url =  window.ctx+ '/groupChannel/changeSequence.do?id='+id + "&sequence="+sequence;
			  $.post(url, function(feedback){
				})
		  }else{
			  alert("请输入小于10000的正整数！");
			  $this.val(oldValue);
		  }
		  
	});
})

function uploadFileFn() {
	$.ajaxFileUpload({
				url : window.ctx + '/groupChannel/importGroupChannel.do', 
				dataType : 'text',
				secureuri : false,
				fileElementId : 'fileGroupChannel', // 文件选择框的id属性
				success : function(data, status, e) {
					var jsonData=$.parseJSON(data);
					alert(jsonData.msg);
					$('#fileGroupChannel').val('');
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

function updateChannelStatus($this, id, status) {
	var url = '';
	if (status == '0') {
		url = window.ctx + '/groupChannel/reverseGroupChannel.do?id=' + id;
		if (!window.confirm("您确定要启用此频道！")) {
			return false;
		}
	} else if (status == '1') {
		url = window.ctx + '/groupChannel/closeGroupChannel.do?id=' + id;
		if (!window.confirm("您确定要停用此频道！")) {
			return false;
		}
	}else {
		return;
	}
	$.post(url, function(feedback) {
		alert(feedback.message);
		if (feedback.successful) {
			location.reload();
		}
	})
}

function changeOperators(obj, dom){
		if (obj.value == '') {
			jQuery('#' + dom + ' option').remove();
			jQuery('#' + dom)
					.append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
			return;
		}
		var param={
			'operators': obj.value
		}
		$.getJSON(window.ctx + "/group/changeUserGroup.do" , param,
				function(res) {
					if (res == null) {
						return;
					}
					$('#' + dom + ' option').remove();
					$('#' + dom).append("<option value=''>=\u8bf7\u9009\u62e9=</option>");
					$.each(res, function(i,group) {
						 $("#" + dom).append("<option data='"+group.groupName+"' value='" + group.groupCode
								 + "'>" + group.groupName+ "</option>");
					});
		});
	}