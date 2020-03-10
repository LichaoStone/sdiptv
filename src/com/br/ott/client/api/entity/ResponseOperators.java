package com.br.ott.client.api.entity;

import java.io.Serializable;
import java.util.List;

/* 
 *  红外运营商
 *  文件名：ResponseOperators.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-15 - 上午10:04:42
 */
public class ResponseOperators implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	// 运营商红外方案
	private String name;
	// 频道运营商ID
	private String operators;
	// 运营商别名(频道运营商名称)
	private String otherName;
	// 运营商ID
	private String code;
	// 运营商名称
	private String spName;
	// 区域名称
	private String area;
	// 红外码集合
	List<ResponseCode2> codes;

	public ResponseOperators(String name, String code, String otherName,
			String area) {
		super();
		this.name = name;
		this.code = code;
		this.otherName = otherName;
		this.area = area;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public ResponseOperators() {
		super();
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public List<ResponseCode2> getCodes() {
		return codes;
	}

	public void setCodes(List<ResponseCode2> codes) {
		this.codes = codes;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
