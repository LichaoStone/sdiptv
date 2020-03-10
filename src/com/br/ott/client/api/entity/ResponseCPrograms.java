package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseCPrograms.java
 *  创建人：pananz
 *  创建时间：2014-7-14 - 下午12:53:50
 */
public class ResponseCPrograms implements Serializable {

	/** */
	private static final long serialVersionUID = -8276599004536081543L;
	// 电视频道ID
	private String channelId;
	// 频道名称
	private String channelName;
	// 频道logo图标地址
	private String channelLogoUrl;
	// 正在播放的节目id
	private String programId;
	// 节目名称
	private String programName;
	// 节目播放时间段（开始-结束 格式如：18：00-20:00）
	private String playDtime;
	// 时长
	private String timeOut;
	// 节目剧集
	private String queue;
	// 运营商本地频道号
	private String localCid;
	// 第三方频道ID(如直播中使用)
	private String cid;
	// 播放ID
	private String playId;
	// 单播地址1
	private String singleLiveServer;
	
	private String dbPlay;
	
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

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
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

	public String getChannelLogoUrl() {
		return channelLogoUrl;
	}

	public void setChannelLogoUrl(String channelLogoUrl) {
		this.channelLogoUrl = channelLogoUrl;
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

	public String getPlayDtime() {
		return playDtime;
	}

	public void setPlayDtime(String playDtime) {
		this.playDtime = playDtime;
	}

	public String getTimeOut() {
		try {
			return Integer.parseInt(timeOut) + "";
		} catch (Exception e) {
			return "0";
		}
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

}
