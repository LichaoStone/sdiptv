package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ModelCode.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-3 - 上午10:31:13
 */
public class ModelCode implements Serializable {

	/** */
	private static final long serialVersionUID = 7841820829618070535L;
	private String keyName;
	private String allCode;

	public ModelCode() {
		super();
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getAllCode() {
		return allCode;
	}

	public void setAllCode(String allCode) {
		this.allCode = allCode;
	}

}
