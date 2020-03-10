package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  设备类型信息
 *  文件名：ResponseType.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-14 - 上午11:23:29
 */
public class ResponseType implements Serializable {

	/** */
	private static final long serialVersionUID = 1084250939082009193L;
	private String name;
	private String type;

	public ResponseType(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
