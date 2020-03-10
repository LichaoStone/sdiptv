package com.br.ott.client.live.entity;

import java.util.Date;

import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Pagination;

/* 
 *  地市运营商
 *  文件名：CityOperators.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-12 - 下午2:31:31
 */
public class CityOperators extends Pagination {

	private String id;
	// 运营商所属地区ID
	private String areaId;
	// 运营商所属地区
	private String areaName;
	// 运营商名称
	private String name;
	// 运营商全称
	private String fullName;
	// 运营商编码
	private String code;
	// 运营商状态
	private String status;
	// 运营商加入时间
	private Date createTime;
	// 排序属性
	private String orderColumn;
	// 上一级地区ID
	private String parentId;
	// 运营商排序
	private int sequence;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeStr() {
		return DateUtil.toString(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
