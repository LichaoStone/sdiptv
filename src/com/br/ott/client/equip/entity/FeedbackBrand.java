package com.br.ott.client.equip.entity;

import java.util.Date;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/* 
 *  品牌型号反馈
 *  文件名：Feedback.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 上午11:48:21
 */
public class FeedbackBrand extends Pagination {

	private String id;
	// 硬件类型
	private String hardType;
	// 品牌名称
	private String brandName;
	// 硬件型号
	private String modelName;
	// 反馈时间
	private Date feedTime;
	// 反馈人
	private String feedMan;
	// 反馈类型 
	private String feedType;
	// 反馈内容
	private String feedContent;
	// 所在地区
	private String address;
	// 反馈人手机型号
	private String phoneModel;
	// 状态:0未处理，1处理中 2已处理
	private String status;
	// 处理描述
	private String remark;
	// 处理人
	private String transactor;
	// 处理时间
	private Date doTime;

	private String orderColumn;

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getFeedContent() {
		return feedContent;
	}

	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getTypeName() {
		if (StringUtil.isEmpty(this.hardType)) {
			return "";
		} else {
			Dictionary dictionary = DictCache.getDictValue(DicType.EQUIP_YJXH,
					hardType);
			return null == dictionary ? "" : dictionary.getDicName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHardType() {
		return hardType;
	}

	public void setHardType(String hardType) {
		this.hardType = hardType;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getFeedTimeStr() {
		return DateUtil.toString(feedTime);
	}

	public Date getFeedTime() {
		return feedTime;
	}

	public void setFeedTime(Date feedTime) {
		this.feedTime = feedTime;
	}

	public String getFeedMan() {
		return feedMan;
	}

	public void setFeedMan(String feedMan) {
		this.feedMan = feedMan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public String getDoTimeStr() {
		return DateUtil.toString(doTime);
	}

	public Date getDoTime() {
		return doTime;
	}

	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}

}
