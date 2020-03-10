package com.br.ott.client.live.entity;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：BasicCProgram.java
 *  创建人：pananz
 *  创建时间：2014-9-23 - 下午5:23:29
 */
public class BasicCProgram extends Pagination {

	private String programId;
	private String programName;
	private String playTime;
	private String timeLongth;
	private String channelId;
	private String channelName;
	private String specialName;
	// 查询排序属性
	private String orderColumn;
	// 查询播放时间最小范围
	private String playTimeMin;
	// 查询播放时间最大范围
	private String playTimeMax;
	// 节目类型
	private String ptype;
	// 频道类型集
	private String typeList;
	// 节目剧集
	private String queue;
	// 节目名称
	private String basicName;
	// 第三方直播频道ID
	private String cid;
	// 播放ID
	private String playId;
	// 是否支持点播
	private String dbPlay;
	// 单播地址1
	private String singleLiveServer;
	// 单播地址2
	private String singleLiveServer2;
	// 组播地址1
	private String groupLiveServer;
	// 组播地址2
	private String groupLiveServer2;
	// 运营商地本频道号
	private String localCid;
	// 频道运营商ID
	private String operators;
	private String logoImgUrl;
	
	public String getLogoImgUrl() {
		return logoImgUrl;
	}

	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}

	public String getDbPlay() {
		return dbPlay;
	}

	public void setDbPlay(String dbPlay) {
		this.dbPlay = dbPlay;
	}

	public String getSingleLiveServer() {
		return singleLiveServer;
	}

	public void setSingleLiveServer(String singleLiveServer) {
		this.singleLiveServer = singleLiveServer;
	}

	public String getSingleLiveServer2() {
		return singleLiveServer2;
	}

	public void setSingleLiveServer2(String singleLiveServer2) {
		this.singleLiveServer2 = singleLiveServer2;
	}

	public String getGroupLiveServer() {
		return groupLiveServer;
	}

	public void setGroupLiveServer(String groupLiveServer) {
		this.groupLiveServer = groupLiveServer;
	}

	public String getGroupLiveServer2() {
		return groupLiveServer2;
	}

	public void setGroupLiveServer2(String groupLiveServer2) {
		this.groupLiveServer2 = groupLiveServer2;
	}

	public String getBasicName() {
		return basicName;
	}

	public void setBasicName(String basicName) {
		this.basicName = basicName;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getPlayTimeMin() {
		return playTimeMin;
	}

	public void setPlayTimeMin(String playTimeMin) {
		this.playTimeMin = playTimeMin;
	}

	public String getPlayTimeMax() {
		return playTimeMax;
	}

	public void setPlayTimeMax(String playTimeMax) {
		this.playTimeMax = playTimeMax;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getTimeLongth() {
		return timeLongth;
	}

	public void setTimeLongth(String timeLongth) {
		this.timeLongth = timeLongth;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
