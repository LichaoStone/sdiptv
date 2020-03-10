package com.br.ott.client.live.entity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.br.ott.client.common.ChannelCache;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：UserFollow.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:11:06
 */
public class UserFollow extends Pagination {

	private String id;
	// 用户编号
	private String uid;
	// 操作动作：1，收藏 2，最近收看
	private String type;
	// 频道编号
	private String channelId;
	// 录入时间
	private String createTime;
	// 正常1，取消0
	private String status;
	// 查询参数
	private String orderColumn;
	private String beginTime;
	private String endTime;
	private String playNow;
	private String areaId;
	private String operators;
	// 返回第三方属性
	private String channelName;
	private String cStatus;
	// 正在播放的节目id
	private String programId;
	// 节目名称
	private String programName;
	// 节目播放时间
	private String playTime;
	// 节目时长
	private String timeLongth;
	// 节目剧集
	private String queue;
	// 第三方频道ID
	private String cid;
	// 节目播放ID
	private String playId;
	// 是否支持点播
	private String dbPlay;
	// 播放频道号
	private String localCid;
	private String channelList;
	// 单播地址1
	private String singleLiveServer;
	// 单播地址2
	private String singleLiveServer2;
	// 组播地址1
	private String groupLiveServer;
	// 组播地址2
	private String groupLiveServer2;

	public String getChannelName2() {
		return ChannelCache.getChannelNameById(channelId);
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

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getChannelList() {
		return channelList;
	}

	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
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

	private static final String FORMAT = "yyyy-MM-dd HH:mm";

	public String getPlayDtime() {
		if (StringUtil.isEmpty(this.playTime)
				|| StringUtil.isEmpty(this.timeLongth)) {
			return "";
		}
		Date startDate = null;
		try {
			startDate = DateUtil.formatDate(this.playTime, FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (null == startDate)
			return "";
		Date endDate = DateUtil.add(startDate, Calendar.MINUTE,
				Integer.valueOf(this.timeLongth));

		return DateUtil.parseDate(startDate, "HH:mm") + "-"
				+ DateUtil.parseDate(endDate, "HH:mm");
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
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

	public String getPlayNow() {
		return playNow;
	}

	public void setPlayNow(String playNow) {
		this.playNow = playNow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeLongth() {
		return timeLongth;
	}

	public void setTimeLongth(String timeLongth) {
		this.timeLongth = timeLongth;
	}

}
