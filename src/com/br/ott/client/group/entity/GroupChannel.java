package com.br.ott.client.group.entity;

import com.br.ott.client.common.GroupCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.common.util.Pagination;

/**
 * 文件名：GroupChannel.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2016-10-11
 */
public class GroupChannel extends Pagination {
	private String id;
	// 运营商编码
	private String operators;
	// 用户组编码
	private String groupCode;
	// 频道名称
	private String channelName;
	// 频道号
	private String localCid;
	// 所属运营商频道ID
	private String ochannelId;
	// 状态，1有效，0无效
	private String status;
	// 排序
	private String sequence;
	
	private String orderColumn;

	public String getOperatorsName() {
		return OperatorsCache.getOperatorsNameByCode(operators);
	}

	public String getGroupName() {
		return GroupCache.getUserGroupNameByCode(operators, groupCode);
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

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getOchannelId() {
		return ochannelId;
	}

	public void setOchannelId(String ochannelId) {
		this.ochannelId = ochannelId;
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
