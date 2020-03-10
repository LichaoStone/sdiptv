package com.br.ott.client.api.entity;

import java.io.Serializable;
import java.util.List;

/* 
 *  
 *  文件名：ResponseModel.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-14 - 下午2:07:27
 */
public class ResponseModel implements Serializable {

	/** */
	private static final long serialVersionUID = -643602879812892394L;
	// 硬件型号
	private String model;
	// 类型名称(别名)
	private String typeName;
	// 品牌编码
	private String brandCode;
	// 品牌红外码
	private List<ResponseCode> codes;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<ResponseCode> getCodes() {
		return codes;
	}

	public void setCodes(List<ResponseCode> codes) {
		this.codes = codes;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
