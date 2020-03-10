package com.br.ott.client.api.entity;

import java.io.Serializable;

/**
 * 文件名：ResponseCustomer.java
 *  版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-8-31
 */
public class ResponseCustomer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8160098484885725146L;
	private String name;
	private String no;
	private String logoUrl;
	private String type;
	public ResponseCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseCustomer(String name, String no, String logoUrl, String type) {
		super();
		this.name = name;
		this.no = no;
		this.logoUrl = logoUrl;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}


