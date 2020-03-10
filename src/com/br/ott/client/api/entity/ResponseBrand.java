package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseModel.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-14 - 下午2:07:27
 */
public class ResponseBrand implements Serializable {

	/** */
	private static final long serialVersionUID = -643602879812892394L;
	private String brand;
	private String brandCode;

	public ResponseBrand(String brand, String brandCode) {
		super();
		this.brand = brand;
		this.brandCode = brandCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

}
