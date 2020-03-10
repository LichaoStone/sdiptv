package com.br.ott.client.live.entity;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.Constants.DicType;

/* 
 *  频道推荐
 *  文件名：RecChannel.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-2-6 - 下午3:28:35
 */
public class RecChannel extends Pagination {

	private String id;
	// 推荐类型：1,launcher频道推荐, 4,手机频道推荐
	private String type;
	// 频道编号
	private String channelId;
	// 排序编号
	private String sequence;
	// 内容描述
	private String content;
	// 推荐LOGO(方图)
	private String logoUrl;
	// 录入时间
	private String createTime;
	// 推荐LOGO横图
	private String wlogoUrl;
	// 推荐LOGO竖图
	private String hlogoUrl;
	// 状态：1：启用，0：停用
	private String status;
	// 查询排序方式
	private String orderColumn;
	// 频道名称
	private String channelName;
	// 频道LOGO
	private String cLogoUrl;
	// 节目ID
	private String pId;
	// 节目名称
	private String pName;
	// 节目播放时间
	private String playTime;
	// 运营商ID
	private String operators;
	private String areaId;

	private int oldSequence;
	public int getOldSequence() {
		return oldSequence;
	}

	public void setOldSequence(int oldSequence) {
		this.oldSequence = oldSequence;
	}
	
	public String getOperatorsName() {
		return OperatorsCache.getOperatorsNameById(operators);
	}

	public String getTypeName() {
		Dictionary dict = DictCache.getDictValue(DicType.TJLX, type);
		return dict == null ? "" : dict.getDicName();
	}
	
	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getWlogoUrl() {
		return wlogoUrl;
	}

	public void setWlogoUrl(String wlogoUrl) {
		this.wlogoUrl = wlogoUrl;
	}

	public String getHlogoUrl() {
		return hlogoUrl;
	}

	public void setHlogoUrl(String hlogoUrl) {
		this.hlogoUrl = hlogoUrl;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getcLogoUrl() {
		return cLogoUrl;
	}

	public void setcLogoUrl(String cLogoUrl) {
		this.cLogoUrl = cLogoUrl;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

}
