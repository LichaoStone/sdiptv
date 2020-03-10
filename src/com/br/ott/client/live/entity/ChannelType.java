package com.br.ott.client.live.entity;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：ChannelType.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午6:19:58
 */
public class ChannelType extends Pagination {

	private String id;
	// 频道
	private String name;
	// 是否为坑爹类型 1:是，0否(后台有此类型而终端不展示)
	private String parentId;
	// 类型编码
	private String code;
	// 状态
	private String status;
	// 显示顺序
	private String sequence;
	// 运营商
	private String operators;
	// 特殊类型 1:是，2否(基础频道管理是否显示此类型)
	private String isSpecial;
	// 公共排序属性
	private String orderColumn;
	private boolean isCheck;

	public String getOperatorsName() {
		return OperatorsCache.getOperatorsNameById(operators);
	}
	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public ChannelType() {
		super();
	}

	public ChannelType(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
