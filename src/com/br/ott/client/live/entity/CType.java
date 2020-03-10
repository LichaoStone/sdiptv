package com.br.ott.client.live.entity;

/* 
 *  
 *  文件名：CType.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午6:57:15
 */
public class CType {

	private String id;
	private String channelId;
	private String channelName;
	private String typeId;
	private String typeName;
	private String code;
	private String cList;
	
	public String getcList() {
		return cList;
	}

	public void setcList(String cList) {
		this.cList = cList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public CType() {
		super();
	}

	public CType(String channelId, String typeId) {
		super();
		this.channelId = channelId;
		this.typeId = typeId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
