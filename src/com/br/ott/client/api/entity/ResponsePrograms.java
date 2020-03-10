package com.br.ott.client.api.entity;

import java.io.Serializable;

import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ResponsePrograms.java
 *  创建人：pananz
 *  创建时间：2014-7-1 - 下午4:05:58
 */
public class ResponsePrograms implements Serializable {

	/** */
	private static final long serialVersionUID = -8849481278592604848L;
	// 节目编号
	private String programId;
	// 节目名称
	private String programName;
	// logo图片url
	private String logoUrl;
	// 横logo图片url
	private String wlogoUrl;
	// 竖logo图片url
	private String hlogoUrl;
	// 所属频道编号
	private String channelId;
	// 频道名称
	private String channelName;
	// 播放时间（格式为 yyyy-mm-dd HH:MM）
	private String playDtime;
	// 时长(分钟)
	private String timeOut;
	// 节目剧集
	private String queue;
	// 本地频道号
	private String localCid;
	// 节目播放ID
	private String playId;
	// 是否支持点播
	private String dbPlay;
	// 单播地址1
	private String singleLiveServer;

	public String getSingleLiveServer() {
		return singleLiveServer;
	}

	public void setSingleLiveServer(String singleLiveServer) {
		this.singleLiveServer = singleLiveServer;
	}

	public String getDbPlay() {
		return dbPlay;
	}

	public void setDbPlay(String dbPlay) {
		this.dbPlay = dbPlay;
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

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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

	public String getPlayDtime() {
		return playDtime;
	}

	public void setPlayDtime(String playDtime) {
		this.playDtime = playDtime;
	}

	public String getTimeOut() {
		try {
			if (StringUtil.isNotEmpty(timeOut)) {
				return Integer.parseInt(timeOut) + "";
			} else {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result
				+ ((channelName == null) ? 0 : channelName.hashCode());
		result = prime * result
				+ ((programName == null) ? 0 : programName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof ResponsePrograms) {
			ResponsePrograms rp = (ResponsePrograms) obj;
			if (this.channelId.equals(rp.channelId)
					&& this.programName.equals(rp.programName)) {
				return true;
			}
		}
		return false;
	}

}
