package com.br.ott.client.live.entity;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：UserPrograms.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:24:26
*/
public class UserPrograms extends Pagination {

	private String id;
	private String uid;
	private String programsId;
	private String programsName;
	//操作动作：1，预定2，取消预定 
	private String type;

	private String createTime;
	private String orderColumn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getProgramsId() {
		return programsId;
	}

	public void setProgramsId(String programsId) {
		this.programsId = programsId;
	}

	public String getProgramsName() {
		return programsName;
	}

	public void setProgramsName(String programsName) {
		this.programsName = programsName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
