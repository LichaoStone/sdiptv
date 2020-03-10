package com.br.ott.client.live.entity;

import java.io.Serializable;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：ChannelLog.java
 *  创建人：pananz
 *  创建时间：2014-8-9 - 下午12:07:06
 */
public class ChannelLog extends Pagination implements Serializable {

	/** */
	private static final long serialVersionUID = 224490338342591924L;
	private String id;
	// 频道名称
	private String channelName;
	// 频道编号
	private String channelId;
	// 真实点击次数值
	private String realNumber;
	// 干预点击次数值
	private String virtualNumber;
	private String orderColumn;
	private String areaId;
	private String operators;

	private String cid;
	
	private OperatorsChannel channel;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public OperatorsChannel getChannel() {
		return channel;
	}

	public void setChannel(OperatorsChannel channel) {
		this.channel = channel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}

	public String getVirtualNumber() {
		return virtualNumber;
	}

	public void setVirtualNumber(String virtualNumber) {
		this.virtualNumber = virtualNumber;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
