package com.br.ott.client.live.entity;

import com.br.ott.common.util.Pagination;

/* 
 *  运营商频道关系
 *  文件名：OperatorsChannel.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-5-28 - 下午12:14:24
 */
public class OperatorsChannel extends Pagination {

	private String id;
	// 运营商ID
	private String operators;
	// 频道ID
	private String channelId;
	// 状态：1启用，停用
	private String status;
	// 运营商名称
	private String operatorsName;
	// 频道名称
	private String channelName;
	// 录入时间
	private String createTime;
	// 地区ID
	private String areaId;
	// 地区名称
	private String areaName;
	// 排序
	private int sequence;
	// 第三方频道号
	private String playChannelId;
	// 是否本地频道 1是，0否，默认0
	private String isLocal;
	//单播地址1
	private String singleLiveServer;
	//单播地址2
	private String singleLiveServer2;
	//组播地址1
	private String groupLiveServer;
	//组播地址2
	private String groupLiveServer2;
	// 所属地区上一级
	private String parentId;
	private String orderColumn;
	private String channelList;
	// 当前频道直播节目实体
	private Programs liveProgram;
	private String careaId;

	public String getCareaId() {
		return careaId;
	}

	public void setCareaId(String careaId) {
		this.careaId = careaId;
	}

	public String getChannelList() {
		return channelList;
	}

	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}

	public String getSingleLiveServer() {
		return singleLiveServer;
	}

	public void setSingleLiveServer(String singleLiveServer) {
		this.singleLiveServer = singleLiveServer;
	}

	public String getGroupLiveServer() {
		return groupLiveServer;
	}

	public void setGroupLiveServer(String groupLiveServer) {
		this.groupLiveServer = groupLiveServer;
	}

	public String getSingleLiveServer2() {
		return singleLiveServer2;
	}

	public void setSingleLiveServer2(String singleLiveServer2) {
		this.singleLiveServer2 = singleLiveServer2;
	}

	public String getGroupLiveServer2() {
		return groupLiveServer2;
	}

	public void setGroupLiveServer2(String groupLiveServer2) {
		this.groupLiveServer2 = groupLiveServer2;
	}

	public Programs getLiveProgram() {
		return liveProgram;
	}

	public void setLiveProgram(Programs liveProgram) {
		this.liveProgram = liveProgram;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
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

	public String getPlayChannelId() {
		return playChannelId;
	}

	public void setPlayChannelId(String playChannelId) {
		this.playChannelId = playChannelId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
