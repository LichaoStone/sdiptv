package com.br.ott.client.market.entity;

import com.br.ott.Global;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/**
 * 文件名：Customer.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 * 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-8-31
 */
public class Customer extends Pagination {

	private String id;
	// 客服名称
	private String customerName;
	// 客服账号
	private String customerNo;
	// 头像地址
	private String headUrl;
	//账号类型 1：qq，2：微信，3：微博
	private String type;
	// 状态：0：停用，1：启用
	private String status;
	private String orderColumn;

	public String getLogoUrl() {
		if (StringUtil.isNotEmpty(headUrl)) {
			return Global.SERVER_SOURCE_URL + headUrl;
		}
		return "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
