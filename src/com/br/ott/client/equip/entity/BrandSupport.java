package com.br.ott.client.equip.entity;

import com.br.ott.Global;
import com.br.ott.common.util.Pagination;

/* 
 *  支付红外功能的手机品牌
 *  文件名：BrandSupport.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-5 - 下午3:10:32
 */
public class BrandSupport extends Pagination {

	private String id;
	// 品牌名称
	private String name;
	// 品牌LOGO地址
	private String logo;
	// 支持型号
	private String supportModel;
	// 排序
	private String sequence;
	// 状态：1启用，0停用
	private String status;

	private String orderColumn;

	public String getLogoUrl() {
		return Global.SERVER_SOURCE_URL + this.logo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSupportModel() {
		return supportModel;
	}

	public void setSupportModel(String supportModel) {
		this.supportModel = supportModel;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
