package com.br.ott.client.live.entity;

import java.io.Serializable;
import java.util.Date;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：ChannelLog.java
 *  创建人：pananz
 *  创建时间：2014-8-9 - 下午12:07:06
 */
public class ChannelRecover extends Pagination implements Serializable {

	/** */
	private static final long serialVersionUID = 224490338342591924L;
	private String id;
	// 频道名称
	private String channelName;
	// 频道编号
	private String channelId;
	// 恢复时间
	private Date recoverTime;
	
	private String queryTime;
	
	private String orderColumn;
	
	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
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

	public Date getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Date recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
