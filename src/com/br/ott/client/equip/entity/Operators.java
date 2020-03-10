package com.br.ott.client.equip.entity;

import java.util.Date;

import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Pagination;

/* 
 *  红外码运营商
 *  文件名：City.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 上午10:50:52
 */
public class Operators extends Pagination {
	private String id;
	// 运营商所属地区
	private String areaId;
	// 运营商所属地区
	private String areaName;
	// 运营商红外方案（套）
	private String name;
	// 运营商编码
	private String code;
	// 运营商名称
	private String spName;
	// 对应频道运营商ID
	private String cityOperators;
	// 运营商别名(频道运营商名称)
	private String otherName;
	// 运营商全称
	private String fullName;
	// 运营商状态
	private String status;
	// 运营商加入时间
	private Date createTime;
	// 排序属性
	private String orderColumn;
	private String parentId;
	// 排序
	private int sequence;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getCityOperators() {
		return cityOperators;
	}

	public void setCityOperators(String cityOperators) {
		this.cityOperators = cityOperators;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeStr() {
		return DateUtil.toString(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

}
