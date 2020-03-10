package com.br.ott.client.market.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ott.client.market.entity.AppLoads;
import com.br.ott.client.market.entity.AppMarket;
import com.br.ott.client.market.mapper.AppLoadsMapper;
import com.br.ott.client.market.mapper.AppMarketMapper;
import com.br.ott.common.util.DateUtil;

/**
 * 文件名：AppLoadsService.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
@Service
public class AppLoadsService {

	@Autowired
	private AppLoadsMapper appLoadsMapper;

	@Autowired
	private AppMarketMapper appMarketMapper;

	@Transactional
	public void addAppLoads(AppLoads appLoads) {
		appLoadsMapper.addAppLoads(appLoads);
		int downLoads = appLoads.getDayDownLoads();
		// 计算下载量
		AppMarket appMarket = appMarketMapper.getAppMarketById(appLoads
				.getMarketId());
		try {
			Date date = DateUtil.formatDate(appLoads.getRecordTime());
			int week = DateUtil.getWeek(date);
			if (week == 1) {// 周一时，周下载重新开始计算
				appMarket.setWeekDownloads(downLoads);
			} else {
				appMarket.setWeekDownloads(appMarket.getWeekDownloads()
						+ downLoads);
			}
			String firstDate = DateUtil.toString(DateUtil.setCalendar(date, 1),
					"yyyy-MM-dd");
			if (firstDate.equals(appLoads.getRecordTime())) {// 当月的第一天时,月下载重新开始算
				appMarket.setMonthDownloads(downLoads);
			} else {
				appMarket.setMonthDownloads(appMarket.getMonthDownloads()
						+ downLoads);
			}
			appMarket.setTotalDownloads(appMarket.getTotalDownloads()
					+ downLoads);
			appMarketMapper.updateAppMarketLoads(appMarket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delAppLoadsById(String id) {
		AppLoads appLoads = getAppLoadsById(id);
		appLoadsMapper.delAppLoadsById(id);
		int downLoads = appLoads.getDayDownLoads();
		// 计算下载量
		AppMarket appMarket = appMarketMapper.getAppMarketById(appLoads
				.getMarketId());
		try {
			Date date = DateUtil.formatDate(appLoads.getRecordTime());
			int week = DateUtil.getWeek(date);
			if (week == 1) {// 周一时，周下载重新开始计算
				appMarket.setWeekDownloads(downLoads);
			} else {
				int weekDownloads = appMarket.getWeekDownloads() - downLoads;
				appMarket.setWeekDownloads(weekDownloads > 0 ? weekDownloads
						: 0);
			}
			String firstDate = DateUtil.toString(DateUtil.setCalendar(date, 1),
					"yyyy-MM-dd");
			if (firstDate.equals(appLoads.getRecordTime())) {// 当月的第一天时,月下载重新开始算
				appMarket.setMonthDownloads(downLoads);
			} else {
				int monthDownloads = appMarket.getMonthDownloads() - downLoads;
				appMarket.setMonthDownloads(monthDownloads > 0 ? monthDownloads
						: 0);
			}
			int totalDownloads = appMarket.getTotalDownloads() - downLoads;
			appMarket
					.setTotalDownloads(totalDownloads > 0 ? totalDownloads : 0);
			appMarketMapper.updateAppMarketLoads(appMarket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AppLoads> findAppLoadsByPage(AppLoads appLoads) {
		appLoads.setTotalResult(appLoadsMapper.getCountAppLoads(appLoads));
		return appLoadsMapper.findAppLoadsByPage(appLoads);
	}

	public int getCountAppLoads(AppLoads appLoads) {
		return appLoadsMapper.getCountAppLoads(appLoads);
	}

	public AppLoads getAppLoadsById(String id) {
		return appLoadsMapper.getAppLoadsById(id);
	}

	public List<AppLoads> findAppLoadsByMarketIdAndDate(String marketId,
			String recordTime) {
		return appLoadsMapper.findAppLoadsByMarketIdAndDate(marketId,
				recordTime);
	}

	public boolean checkAppLoadsByDate(String marketId, String recordTime) {
		List<AppLoads> list = findAppLoadsByMarketIdAndDate(marketId,
				recordTime);
		if (CollectionUtils.isNotEmpty(list)) {
			list = null;
			return false;
		}
		return true;
	}
}
