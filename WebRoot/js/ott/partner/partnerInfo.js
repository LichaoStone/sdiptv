$(function() {
			initPartner();
			
			var validate= $("#form1").validate({
					onsubmit : false,
					rules : {
						'partnerName' : {
							required : true
						},
						'partnerType' : {
							required : true
						},
						'partnerPhone' : {
							required : true,
							phoneCN : true
						},
						'identitycardImgFile' : {
							required : true,
							accept : true
						},
						'linkman' : {
							required : true
						},
						'SPid' : {
							required : true
						}
					},
					messages : { // 出错提示信息
						'partnerName' : "请填写个人名称",
						'partnerType' : "请选择合作类型",
						'partnerPhone' : {
							required : "请填写电话号码",
							phoneCN : "电话号码格式不正确"
						},
						'identitycardImgFile' : {
							required : "请上传身份证图片",
							accept : "只允许上传jpg|png|jpeg类型的图片"
						},
						'linkman' : {
							required : "请填写联系人"
						},
						'SPid' : {
							required : "请填写SPid"
						}
					},
					errorPlacement : $.handleError,
					success : $.handleSuccess,
					invalidHandler: $.invalidAlertHandler
				});
			var validate2= $("#form2").validate({
					onsubmit : false,
					rules : {
						'partnerName' : {
							required : true
						},
						'partnerType' : {
							required : true
						},
						'partnerPhone' : {
							required : true,
							phoneCN : true
						},
						'merchantImgFile' : {
							required : true,
							accept : true
						},
						'identitycardImgFile' : {
							required : true,
							accept : true
						},
						'businesslicenseFile' : {
							required : true,
							accept : true
						},
						'linkman' : {
							required : true
						},
						'SPid' : {
							required : true
						},
						'simpleName' : {
							required : true
						},
						'legal' : {
							required : true
						},
						'competent' : {
							required : true
						},
						'competentPhone' : {
							required : true,
							phoneCN : true
						},
						'competentEmail' : {
							required : true,
							email : true
						},
						'contact' : {
							required : true
						},
						'contactPhone' : {
							required : true,
							phoneCN : true
						},
						'contactEmail' : {
							required : true,
							email : true
						}
					},
					messages : { // 出错提示信息
						'partnerName' : "请填写公司名称",
						'partnerType' : "请选择合作类型",
						'partnerPhone' : {
							required : "请填写电话号码",
							phoneCN : "电话号码格式不正确"
						},
						'merchantImgFile' : {
							required : "请上传商家图片",
							accept : "商家图片只允许上传jpg|png|jpeg类型的图片"
						},
						'identitycardImgFile' : {
							required : "请上传身份证图片",
							accept : "身份证只允许上传jpg|png|jpeg类型的图片"
						},
						'businesslicenseFile' : {
							required : "请上传营业执照图片",
							accept : "营业执照只允许上传jpg|png|jpeg类型的图片"
						},
						'linkman' : {
							required : "请填写联系人"
						},
						'SPid' : {
							required : "请填写SPid"
						},
						'simpleName' : {
							required : "请填写公司简称"
						},
						'legal' : {
							required : "请填写法人代表"
						},
						'competent' : {
							required : "请填写业务负责人"
						},
						'competentPhone' : {
							required : "请填写业务电话",
							phoneCN : "业务电话格式不正确"
						},
						'competentEmail' : {
							required : "请填写业务邮箱",
							email : "业务邮箱格式不正确"
						},
						'contact' : {
							required : "请填写客服联系人"
						},
						'contactPhone' : {
							required : "请填写客服电话",
							phoneCN : "客服电话格式不正确"
						},
						'contactEmail' : {
							required : "请填写客服邮箱",
							email : "客服邮箱格式不正确"
						}
					},
					errorPlacement : $.handleError,
					success : $.handleSuccess,
					invalidHandler: $.invalidAlertHandler
				});	
			var isSaving = false;
			// 单击保存按钮提交表单事件
			$('#savePartner').click(function(e) {
				if (isSaving) {
					return false;
				}
				if (validate.form()) {
					var url = '';
					if ($('#id').val() == '') {
						url = window.ctx + '/partner/addPartner.do';
					} else {
						url = window.ctx + '/partner/modifyPartner.do';
					}
					isSaving = true;
					$(this).removeClass('bt-primary').addClass('bt-secondary')
							.text('保存中...');
					 $("#form1").attr('action', url).submit();
				}
			});

			$('#savePartner2').click(function(e) {
				if (isSaving) {
					return false;
				}
				if (validate2.form()) {
					var url = '';
					if ($('#id').val() == '') {
						url = window.ctx + '/partner/addPartner.do';
					} else {
						url = window.ctx + '/partner/modifyPartner.do';
					}
					isSaving = true;
					$(this).removeClass('bt-primary').addClass('bt-secondary')
							.text('保存中...');
					 $("#form2").attr('action', url).submit();
				}
			});
			
			$('#merchantImgFile').change(function(e) {
						new uploadPreview(this,{  DivShow: "preview", ImgShow: "imghead" }).show();
					})

			$('#identitycardImgFile').change(function(e) {
						new uploadPreview(this,{  DivShow: "preview2", ImgShow: "imghead2" }).show();
					})

			$('#idcardFile').change(function(e) {
						new uploadPreview(this,{  DivShow: "preview5", ImgShow: "imghead5" }).show();
					})
					
			$('#businesslicenseFile').change(function(e) {
						new uploadPreview(this,{  DivShow: "preview3", ImgShow: "imghead3" }).show();
					})

			$('#otherImgFile').change(function(e) {
						new uploadPreview(this,{  DivShow: "preview4", ImgShow: "imghead4" }).show();
					})

			$('#partnerType').change(function(e) {
						var selected = $('select#partnerType option:selected');
						if (selected.text() == '个人内容提供者') {
							$('#form1').show();
							$('#form2').hide();
							$('#personType').val(selected.val());
							$('#companyType').val('');
						} else {
							$('#form1').hide();
							$('#form2').show();
							$('#personType').val('');
							$('#companyType').val(selected.val());
						}
					})
		})

function initPartner() {
	if ($('#editable').val() == "false") {
		$('#savePartner').text('审核中..').prop('disabled', true)
				.addClass('bt-secondary');
		$('#savePartner2').text('审核中..').prop('disabled', true)
				.addClass('bt-secondary');
	}
	var selected = $('select#partnerType option:selected').text();
	if (selected == '个人内容提供者') {
		$('#form1').show();
		$('#form2').hide();
	} else {
		$('#form1').hide();
		$('#form2').show();
	}
}
