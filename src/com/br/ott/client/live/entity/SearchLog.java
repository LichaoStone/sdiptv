package com.br.ott.client.live.entity;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：SearchLog.java
 *  创建人：pananz
 *  创建时间：2014-7-23 - 下午10:27:00
*/
public class SearchLog extends Pagination {

	private String id;
	//搜索内容
	private String name;
	//真实搜索次数值
	private String realNumber;
	//干预搜索次数值
	private String virtualNumber;
	//最近一次搜索时间
	private String updateTime;
	//状态：1有效，0无效
	private String status;

	private String orderColumn;

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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
