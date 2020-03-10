package com.br.ott.client.api.entity;

import java.io.Serializable;

/**
 * 文件名：OperatorsTV.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 * 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2016-4-21
 */
public class OperatorsTV implements Serializable {

	private static final long serialVersionUID = -7816357553378896420L;
	private String id;
	// 运营商名称
	private String name;
	// 运营商编码
	private String code;
	// 运营商类型
	private String type;

	public OperatorsTV() {
		super();
	}

	public OperatorsTV(String id, String name, String code, String type) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
