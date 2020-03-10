package com.br.ott.client.market.entity;

import com.br.ott.common.util.Pagination;

/**
 * 应用版本日下载记录 文件名：AppLoads.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
public class AppLoads extends Pagination {

	private String id;
	//应用市场ID
	private String marketId;
	//应用市场名称
	private String marketName;
	//应用版本号
	private String version;
	//日下载量
	private int dayDownLoads;
	//下载日期（yyyy-MM-dd）
	private String recordTime;
	
	private String orderColumn;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public int getDayDownLoads() {
		return dayDownLoads;
	}

	public void setDayDownLoads(int dayDownLoads) {
		this.dayDownLoads = dayDownLoads;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

}
