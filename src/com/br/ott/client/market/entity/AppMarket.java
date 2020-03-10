package com.br.ott.client.market.entity;

import com.br.ott.common.util.Pagination;

/**
 * 登陆应用市场信息 文件名：AppInfo.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
public class AppMarket extends Pagination {

	private String id;
	// 应用名称
	private String appName;
	// 市场名称
	private String marketName;
	// 平台等级
	private String grade;
	// 平台属性
	private String marketAttr;
	// 网址
	private String site;
	// 姓名
	private String partnerName;
	// qq
	private String partnerQQ;
	// 电话
	private String partnerTel;
	// 邮件
	private String partnerEmail;
	// 备注
	private String partnerRemark;
	// 发布应用入口
	private String entrance;
	// 注册邮箱或手机(账号)
	private String registerAccount;
	// 帐号属性 公司/个人
	private String accountType;
	// 发布版本号
	private String version;
	// 更新说明
	private String updateDesc;
	// 总下载
	private int totalDownloads;
	// 月下载
	private int monthDownloads;
	// 周下载
	private int weekDownloads;
	// 评论数
	private int reviews;
	// 软件发布顺序
	private int incr;
	private AppLoads appLoads;

	private String orderColumn;

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public int getDayDownloads() {
		if (appLoads == null) {
			return 0;
		} else {
			return appLoads.getDayDownLoads();
		}
	}

	public int getIncr() {
		return incr;
	}

	public void setIncr(int incr) {
		this.incr = incr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMarketAttr() {
		return marketAttr;
	}

	public void setMarketAttr(String marketAttr) {
		this.marketAttr = marketAttr;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerQQ() {
		return partnerQQ;
	}

	public void setPartnerQQ(String partnerQQ) {
		this.partnerQQ = partnerQQ;
	}

	public String getPartnerTel() {
		return partnerTel;
	}

	public void setPartnerTel(String partnerTel) {
		this.partnerTel = partnerTel;
	}

	public String getPartnerEmail() {
		return partnerEmail;
	}

	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}

	public String getPartnerRemark() {
		return partnerRemark;
	}

	public void setPartnerRemark(String partnerRemark) {
		this.partnerRemark = partnerRemark;
	}

	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	public String getRegisterAccount() {
		return registerAccount;
	}

	public void setRegisterAccount(String registerAccount) {
		this.registerAccount = registerAccount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public int getTotalDownloads() {
		return totalDownloads;
	}

	public void setTotalDownloads(int totalDownloads) {
		this.totalDownloads = totalDownloads;
	}

	public int getMonthDownloads() {
		return monthDownloads;
	}

	public void setMonthDownloads(int monthDownloads) {
		this.monthDownloads = monthDownloads;
	}

	public int getWeekDownloads() {
		return weekDownloads;
	}

	public void setWeekDownloads(int weekDownloads) {
		this.weekDownloads = weekDownloads;
	}

	public int getReviews() {
		return reviews;
	}

	public void setReviews(int reviews) {
		this.reviews = reviews;
	}

}
