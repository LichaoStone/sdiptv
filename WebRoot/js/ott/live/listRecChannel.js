$(function(){
	$('#queryChannel').click(function(e) {
		initPage();
		$('#queryForm').submit();
	});
	$('#list-table td input[name="queue"]').focus(function(e){
		$('#list-table td').find("img").hide();
		$(this).parent("td").find("img").show();
	})
	
	$('#list-table td input[name="queue"]').change(function(e){
		  var $this=$(this);
		  var reCat = new RegExp("^[0-9]*$"); 
		  var queue=$this.val();
		  var oldQueue=$this.attr('data-queue');
		  if(reCat.test(queue) && queue.length < 5 && parseInt(queue) > 0){
			  var url =  window.ctx+ '/rec/changeDNSequences.do';
			  var id=$this.attr('data-id');
			  var prama={
					  'id':id,
					  'queue':queue,
					  'oldQueue':oldQueue
			  }
			  $.post(url, prama,function(feedback){
				  if (feedback.successful) {
						location.reload();
				   }else{
					   alert(feedback.message);
				   }
			  })
		  }else{
			  alert("请输入大于0小于10000的正整数！");
			  $this.val(oldQueue);
		  }
	});
})

function delRecChannel($this, id){
	var url = url = window.ctx+ '/rec/delRecChannel.do?id='+id;
	if(!window.confirm("您确定要删除此频道推荐！")){
			return false;
		}
	$.post(url, function(feedback){
		alert(feedback.message);
		if(feedback.successful){
			location.reload();
		}
	})
}

function updateRecStatus($this, id, status){
	var url = '';
	if(status =='0'){
		url = window.ctx+ '/rec/reverseRecChannel.do?id='+id;
		if(!window.confirm("您确定要启用此频道推荐！")){
			return false;
		}
	}else if(status == '1'){
		url = window.ctx+ '/rec/closeRecChannel.do?id='+id;
		if(!window.confirm("您确定要停用此频道推荐！")){
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