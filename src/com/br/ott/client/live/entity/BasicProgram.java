package com.br.ott.client.live.entity;

import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：BasicProgram.java
 *  描述：节目基础信息表
 *  创建人：pengwei
 *  创建时间：2014-9-15 - 上午10:37:14
 */
public class BasicProgram extends Pagination {

	// 编号
	private String id;
	// 节目名称
	private String name;
	// 其他名称
	private String otherName;
	// 内容提供商
	private String cProvider;
	// 节目编码（唯一值）
	private String pcode;
	// 码率
	private String bitrate;
	// 版本
	private String version;
	// 产地（中国大陆，中国香港，中国台湾，美国等）
	private String origin;
	// 语言(中文，英语，韩文等)
	private String language;
	// 上映年份
	private String playyear;
	// 导演
	private String director;
	// 主演
	private String actor;
	// 主持人
	private String compere;
	// 类型（电影，电视剧，体育节目，娱乐节目等）
	private String ptype;
	// 类型分类（动作片，科幻片，篮球节目，足球节目等）
	private String ttype;
	// 关键字
	private String keyword;
	// 播放时长
	private String timeLongth;
	// 播放源地址
	private String playUrl;
	// 节目介绍
	private String remark;
	// 录入时间
	private String createTime;
	// 操作人
	private String operator;
	// 状态：1有效，0失效
	private String status;
	// 展示类型 2, 推荐, 3,广告
	private String ztype;
	// 节目logo图片url
	private String logoImgUrl;
	// 节目简介图片url
	private String descImgUrl;
	// 排序字段
	private String orderColumn;
	// 节目是否为特殊频道节目: 值001表示非特殊频道节目，值为频道id，表示为特定频道节目
	private String channelId;
	// 特殊节目名称(如南京1937)
	private String specialName;
	// 免费播放时长（N分后开始提示订购）
	private int limitTime;
	// 点播一次价格()
	private float price;
	// 频道名称
	private String channelName;
	// 频道LOGO
	private String cLogoUrl;
	// 节目播放时间
	private String playTime;
	private String cid;
	private String localCid;
	private String playId;
	// 单播地址1
	private String singleLiveServer;
	// 单播地址2
	private String singleLiveServer2;
	// 组播地址1
	private String groupLiveServer;
	// 组播地址2
	private String groupLiveServer2;
	private String queue;

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
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

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getcLogoUrl() {
		return cLogoUrl;
	}

	public void setcLogoUrl(String cLogoUrl) {
		this.cLogoUrl = cLogoUrl;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public BasicProgram() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getcProvider() {
		return cProvider;
	}

	public void setcProvider(String cProvider) {
		this.cProvider = cProvider;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPlayyear() {
		return playyear;
	}

	public void setPlayyear(String playyear) {
		this.playyear = playyear;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getCompere() {
		return compere;
	}

	public void setCompere(String compere) {
		this.compere = compere;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getTtype() {
		return ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTimeLongth() {
		return timeLongth;
	}

	public void setTimeLongth(String timeLongth) {
		this.timeLongth = timeLongth;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZtype() {
		return ztype;
	}

	public void setZtype(String ztype) {
		this.ztype = ztype;
	}

	public String getLogoImgUrl() {
		return logoImgUrl;
	}

	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}

	public String getDescImgUrl() {
		return descImgUrl;
	}

	public void setDescImgUrl(String descImgUrl) {
		this.descImgUrl = descImgUrl;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
