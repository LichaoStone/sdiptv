package com.br.ott.client.common;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.br.ott.client.common.service.DistrictService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.dict.mapper.DictionarysMapper;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.CityOperatorsMapper;
import com.br.ott.client.live.service.ChannelRecoverService;
import com.br.ott.client.live.service.ChannelTypeService;
import com.br.ott.client.live.service.RecChannelService;
import com.br.ott.common.util.spring.SpringContextHolder;
import com.br.ott.common.util.spring.SpringUtils;

public class CacheListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(CacheListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("服务器关闭..... ");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.debug("服务器启动..... 开始查找缓存数据.");

		loadDictCache(event.getServletContext());

		loadDistrictCache(event.getServletContext());

		loadChannelCache(event.getServletContext());

		loadAbnormalCache(event.getServletContext());
	}

	/**
	 * 加载区域信息 创建人：陈登民 创建时间：2013-1-18 - 下午2:56:28 返回类型：void
	 * 
	 * @exception throws
	 */
	private void loadDistrictCache(ServletContext sc) {
		try {
			DistrictService districtService = SpringUtils.getBean(sc,
					"districtService", DistrictService.class);
			DistrictCache.reload(districtService.getAllDistrict(2));
		} catch (Exception e) {
			logger.error("加载区域数据异常.........");
			e.printStackTrace();
		}
	}

	/**
	 * 加载字典 创建人：pananz 创建时间：2012-12-27 - 下午6:00:53 返回类型：void
	 * 
	 * @exception throws
	 */
	private void loadDictCache(ServletContext sc) {
		try {
			DictionarysMapper dictionarysMapper = SpringUtils.getBean(sc,
					"dictionarysMapper", DictionarysMapper.class);
			Dictionary cictionary = new Dictionary();
			cictionary.setStatus("1");
			List<Dictionary> dicts = dictionarysMapper
					.findAllDictionarys(cictionary);
			DictCache.reload(dicts);
			dicts = null;
		} catch (Exception e) {
			logger.error("加载字典数据异常.........");
			e.printStackTrace();
		}
	}

	/**
	 * 加载基础频道信息
	 * 
	 * 创建人：pananz 创建时间：2016-10-31
	 * 
	 * @param @param sc 返回类型：void
	 * @exception throws
	 */
	private void loadChannelCache(ServletContext sc) {
		try {
			CityOperatorsMapper cityOperatorsMapper = SpringUtils.getBean(sc,
					"cityOperatorsMapper", CityOperatorsMapper.class);
			CityOperators co = new CityOperators();
			co.setStatus("1");
			List<CityOperators> list = cityOperatorsMapper
					.findCityOperatorsByCond(co);
			OperatorsCache.reload(list);

			ChannelMapper channelMapper = SpringUtils.getBean(sc,
					"channelMapper", ChannelMapper.class);
			List<Channel> clist = channelMapper
					.findChannelByCond(new Channel());
			ChannelCache.reloadChannel(clist);

			ChannelTypeService channelTypeService = SpringUtils.getBean(sc,
					"channelTypeService", ChannelTypeService.class);
			channelTypeService.reloadChannelType();

			RecChannelService recChannelService = SpringContextHolder
					.getBean(RecChannelService.class);
			recChannelService.findRecIndex();
			recChannelService.findIndexChannel();
			
		} catch (Exception e) {
			logger.error("基础频道缓存加载异常" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 加载异常信息
	 * 
	 * 创建人：pananz 创建时间：2015-11-30
	 * 
	 * @param @param sc 返回类型：void
	 * @exception throws
	 */
	private void loadAbnormalCache(ServletContext sc) {
		try {
			ChannelRecoverService channelRecoverService = SpringUtils.getBean(
					sc, "channelRecoverService", ChannelRecoverService.class);
			channelRecoverService.reloadAbnormalInfo();
		} catch (Exception e) {
			logger.error("加载异常频道节目数据异常.........");
			e.printStackTrace();
		}
	}
}
