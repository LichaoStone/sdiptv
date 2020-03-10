package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  红外品牌
 *  文件名：ResponseOperators.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-15 - 上午10:04:42
 */
public class ResponseOperatorsBrand implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	
	private String brandName;

	private String city;
	
	private String isSpecial;

	public ResponseOperatorsBrand(String brandName, String city,
			String isSpecial) {
		super();
		this.brandName = brandName;
		this.city = city;
		this.isSpecial = isSpecial;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
	
	
}
