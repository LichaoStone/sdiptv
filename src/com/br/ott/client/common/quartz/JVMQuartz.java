package com.br.ott.client.common.quartz;

import java.util.List;

import org.apache.log4j.Logger;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.mapper.CityOperatorsMapper;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.ChannelTypeService;
import com.br.ott.client.live.service.RecChannelService;
import com.br.ott.common.util.spring.SpringContextHolder;

/**
 * 文件名：JVMQuartz.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 * 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2016-11-15
 */
public class JVMQuartz {
	private static final Logger logger = Logger.getLogger(JVMQuartz.class);

	/**
	 * 更新基础信息（每5分钟执行一次）
	 * 
	 * 创建人：pananz 创建时间：2016-11-15
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public void checkChannelInfo() {
		ChannelService channelService = SpringContextHolder
				.getBean(ChannelService.class);
		channelService.reloadChannel();
		logger.debug("更新基础频道信息");

		CityOperatorsMapper cityOperatorsMapper = SpringContextHolder
				.getBean(CityOperatorsMapper.class);
		CityOperators co = new CityOperators();
		co.setStatus("1");
		List<CityOperators> list = cityOperatorsMapper
				.findCityOperatorsByCond(co);
		OperatorsCache.reload(list);
		logger.debug("更新运营商信息");
		list=null;
		
		ChannelTypeService channelTypeService = SpringContextHolder
				.getBean(ChannelTypeService.class);
		channelTypeService.reloadChannelType();
		logger.debug("更新频道类型信息");
	}

	/**
	 * 直播首页推荐信息（每2分钟更新一次）
	 * 
	 * 创建人：pananz 创建时间：2016-12-23
	 * 
	 * @param 返回类型：void
	 * @exception throws
	 */
	public void reloadRecIndexInfo() {
		RecChannelService recChannelService = SpringContextHolder
				.getBean(RecChannelService.class);
		recChannelService.findRecIndex();

		recChannelService.findIndexChannel();
		logger.debug("更新首页直播推荐信息");
	}
}
