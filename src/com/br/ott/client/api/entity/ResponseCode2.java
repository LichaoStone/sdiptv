package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseCode2.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-16 - 上午10:10:16
 */
public class ResponseCode2 implements Serializable {

	/** */
	private static final long serialVersionUID = -1292090845992552142L;

	// 设备类型
	private String type;
	// 编码格式
	private String codeFormat;
	// 客户码
	private String clientCode;
	// 键值名
	private String keyName;
	// 键值码
	private String keyCode;
	// 全码
	private String allCode;

	public ResponseCode2() {
		super();
	}

	public ResponseCode2(String type, String codeFormat, String clientCode,
			String keyName, String keyCode, String allCode) {
		super();
		this.type = type;
		this.codeFormat = codeFormat;
		this.clientCode = clientCode;
		this.keyName = keyName;
		this.keyCode = keyCode;
		this.allCode = allCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(String codeFormat) {
		this.codeFormat = codeFormat;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getAllCode() {
		return allCode;
	}

	public void setAllCode(String allCode) {
		this.allCode = allCode;
	}

}
