package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseChannel.java
 *  创建人：pananz
 *  创建时间：2014-7-1 - 下午3:39:01
 */
public class ResponseChannel implements Serializable {

	/** */
	private static final long serialVersionUID = -7519447485961836552L;
	// 基础频道ID
	private String channelId;
	// 频道名称
	private String channelName;
	// 频道LOGO
	private String channelLogoUrl;
	// 频道号
	private String localCid;

	public String getChannelLogoUrl() {
		return channelLogoUrl;
	}

	public void setChannelLogoUrl(String channelLogoUrl) {
		this.channelLogoUrl = channelLogoUrl;
	}

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
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
