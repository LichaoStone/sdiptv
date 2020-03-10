package com.br.ott.client.group.entity;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.common.util.Pagination;

public class UserGroup extends Pagination {
	private String id;//
	private String operators;
	private String groupName;// 用户组名称
	private String groupCode;// 用户组编码
	private String status;// 状态：1，有效，0无效
	//3A同步结果 1，同步成功，0同步失败
	private String aaaStatus;
	//launcher同步结果 1，同步成功，0同步失败
	private String lcStatus;
	
	/********** 用于页面查询 ****************/
	private String orderColumn;
	private String isCheck;
	
	public String getAaaStatus() {
		return aaaStatus;
	}

	public void setAaaStatus(String aaaStatus) {
		this.aaaStatus = aaaStatus;
	}

	public String getLcStatus() {
		return lcStatus;
	}

	public void setLcStatus(String lcStatus) {
		this.lcStatus = lcStatus;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getOperatorsName() {
		return OperatorsCache.getOperatorsNameByCode(operators);
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}