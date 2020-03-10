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
 *  文件名：Programs.java
 *  创建人：pananz
 *  创建时间：2014-5-16 - 上午10:37:14
 */
public class Programs extends Pagination {

	private String id;
	// 节目名称
	private String name;
	// 所属频道ID
	private String channelId;
	// 所属频道名称
	private String channelName;
	// 节目全称
	private String fullName;
	// 播放时间
	private String playTime;
	// 播放时长
	private String timeLongth;
	// 回看播放ID
	private String playId;
	// 说明
	private String remark;
	// 节目剧集 如是电视剧时，第几集
	private String queue;
	// logo图片url
	private String logoImgUrl;
	// 简介图片url
	private String descImgUrl;
	// 录入时间
	private String createTime;
	// 操作人
	private String operator;
	// 状态
	private String status;
	// 节目单对应节目基础数据名称
	private String basicName;
	// 资源服务器id
	private String serverId;
	// 资源存放的根目录
	private String rootDir;
	// 文件服务器地址
	private String sourceServer;
	// 文件物理存放位置
	private String fileAddr;
	// 是否可以点播播放:0否，1是
	private String dbPlay;
	// 查询排序属性
	private String orderColumn;
	// 查询播放时间最小范围
	private String playTimeMin;
	// 查询播放时间最大范围
	private String playTimeMax;
	// 正在播放的节目
	private String playNow;
	// 所有频道集合
	private String channelList;
	// 频道简介图片
	private String cDescImgUrl;
	// 频道LOGO
	private String cLogoUrl;
	// 频道推荐类型
	private String rcType;
	// 频道状态
	private String cStatus;
	// logo图片url
	private String wlogoUrl;
	// logo图片url
	private String hlogoUrl;
	// 第三方频道ID
	private String cid;
	// 运营商ID
	private String operators;
	// 是否本地频道
	private String isLocal;
	// 运营商地本频道号
	private String localCid;
	// 单播地址1
	private String singleLiveServer;
	// 单播地址2
	private String singleLiveServer2;
	// 组播地址1
	private String groupLiveServer;
	// 组播地址2
	private String groupLiveServer2;
	private String areaId;

	public String getDbPlay() {
		return dbPlay;
	}

	public void setDbPlay(String dbPlay) {
		this.dbPlay = dbPlay;
	}

	public String getBaseChannelName() {
		return ChannelCache.getChannelNameById(channelId);
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
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

	public Programs() {
		super();
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public String getSourceServer() {
		return sourceServer;
	}

	public void setSourceServer(String sourceServer) {
		this.sourceServer = sourceServer;
	}

	public String getFileAddr() {
		return fileAddr;
	}

	public void setFileAddr(String fileAddr) {
		this.fileAddr = fileAddr;
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

	public String getRcType() {
		return rcType;
	}

	public void setRcType(String rcType) {
		this.rcType = rcType;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public String getBasicName() {
		return basicName;
	}

	public void setBasicName(String basicName) {
		this.basicName = basicName;
	}

	public Programs(String name, String channelId, String playTime,
			String playId) {
		super();
		this.name = name;
		this.channelId = channelId;
		this.playTime = playTime;
		this.playId = playId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((playTime == null) ? 0 : playTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Programs) {
			Programs programs = (Programs) obj;
			if (this.name.equals(programs.name)
					&& this.channelId.equals(programs.channelId)
					&& this.playTime.equals(programs.playTime)) {
				return true;
			}
		}
		return false;
	}

	public String getcDescImgUrl() {
		return cDescImgUrl;
	}

	public void setcDescImgUrl(String cDescImgUrl) {
		this.cDescImgUrl = cDescImgUrl;
	}

	public String getcLogoUrl() {
		return cLogoUrl;
	}

	public void setcLogoUrl(String cLogoUrl) {
		this.cLogoUrl = cLogoUrl;
	}

	public String getChannelList() {
		return channelList;
	}

	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}

	public String getPlayNow() {
		return playNow;
	}

	public void setPlayNow(String playNow) {
		this.playNow = playNow;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
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
}
