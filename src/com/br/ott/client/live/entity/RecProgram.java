package com.br.ott.client.live.entity;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.Constants.DicType;

/* 
 *  节目推荐实体
 *  文件名：RecChannel.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午3:43:26
 */
public class RecProgram extends Pagination {

	private String id;
	// 推荐类型：2:launcher推荐节目, 3:手机推荐节目,5:手机电视剧推荐
	private String type;
	// 频道编号
	private String channelId;
	// 内容描述
	private String content;
	// 推荐LOGO(方图)
	private String logoUrl;
	// 节目查询关键名称
	private String indexSearch;
	// 排序编号
	private String sequence;
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
	// 播放时间
	private String timeOut;
	// 第几集
	private String queue;
	// 查询播放时间最小范围
	private String playTimeMin;
	// 查询播放时间最大范围
	private String playTimeMax;
	private String localCid;
	// 单播地址1
	private String singleLiveServer;
	// 单播地址2
	private String singleLiveServer2;
	// 组播地址1
	private String groupLiveServer;
	// 组播地址2
	private String groupLiveServer2;
	private String cid;
	// 播放ID
	private String playId;
	private String areaId;
	private String operators;
	
	private int oldSequence;
	public int getOldSequence() {
		return oldSequence;
	}

	public void setOldSequence(int oldSequence) {
		this.oldSequence = oldSequence;
	}

	public String getLocalCid() {
		return localCid;
	}

	public void setLocalCid(String localCid) {
		this.localCid = localCid;
	}

	public String getOperatorsName() {
		return OperatorsCache.getOperatorsNameById(operators);
	}
	
	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getTypeName() {
		Dictionary dict = DictCache.getDictValue(DicType.TJLX, type);
		return dict == null ? "" : dict.getDicName();
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

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
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

	public String getIndexSearch() {
		return indexSearch;
	}

	public void setIndexSearch(String indexSearch) {
		this.indexSearch = indexSearch;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
