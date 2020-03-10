package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseArea.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-16 - 上午10:09:15
 */
public class ResponseArea implements Serializable {

	/** */
	private static final long serialVersionUID = -9172749871632469987L;

	private String spName;

	private String city;
	private String isSpecial;

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public ResponseArea(String spName, String city, String isSpecial) {
		super();
		this.spName = spName;
		this.city = city;
		this.isSpecial = isSpecial;
	}

	public ResponseArea() {
		super();
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((spName == null) ? 0 : spName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof ResponseArea) {
			ResponseArea dict = (ResponseArea) obj;
			if (this.city.equals(dict.city) && this.spName.equals(dict.spName)
					) {
				return true;
			}
		}
		return false;
	}

	
	
	
}
