package com.br.ott.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelType;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelCache.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-20 - 上午10:41:51
 */
public class ChannelCache {
	private static final Logger logger = Logger.getLogger(ChannelCache.class);
	// 基础频道信息
	public static List<Channel> cList = new ArrayList<Channel>();
	// 所有频道类型
	public static List<ChannelType> ctList = new ArrayList<ChannelType>();
	// 运营商频道类型
	public static Map<String, String> ctMap = new HashMap<String, String>();
	// 运营商首页直播信息
	public static Map<String, String> indexRecMap = new HashMap<String, String>();
	// 推荐频道信息
	public static Map<String, String> recChannelMap = new HashMap<String, String>();

	public static void reloadIndexRec(Map<String, String> map) {
		logger.debug("sysCache内容的运营商首页直播信息更新");
		indexRecMap = map;
	}

	public static void reloadRecChannel(Map<String, String> map) {
		logger.debug("sysCache内容的运营商首页直播频道信息更新");
		recChannelMap = map;
	}

	public static void reloadChannelType(Map<String, String> map) {
		logger.debug("sysCache内容的运营商类型基础频道信息更新");
		ctMap = map;
	}

	public static String getIndexRecByOperators(String operatorsCode) {
		return indexRecMap.get(operatorsCode);
	}

	public static String getRecChannelByOperators(String operatorsCode) {
		return recChannelMap.get(operatorsCode);
	}

	public static String getChannelTypeByOperators(String operatorsCode) {
		return ctMap.get(operatorsCode);
	}

	/**
	 * 更新基础频道信息
	 * 
	 * 创建人：pananz 创建时间：2016-10-13
	 * 
	 * @param @param map 返回类型：void
	 * @exception throws
	 */
	public static void reloadChannel(List<Channel> list) {
		logger.debug("更新基础频道信息");
		cList = list;
	}

	public static void reloadChannelType(List<ChannelType> list) {
		logger.debug("更新频道类型信息");
		ctList = list;
	}

	public static List<Channel> findChannelByParentId(String parentId) {
		if (StringUtil.isEmpty(parentId) || CollectionUtils.isEmpty(cList))
			return null;
		List<Channel> channels = new ArrayList<Channel>();
		for (Channel node : cList) {
			if (parentId.equals(node.getParentId())) {
				channels.add(node);
			}
		}
		return channels;
	}

	public static Channel getChannelById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(cList))
			return null;
		Channel channel = null;
		for (Channel node : cList) {
			if (id.equals(node.getId())) {
				channel = node;
				break;
			}
		}
		return channel;
	}

	public static String getChannelNameById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(cList))
			return "";
		String channelName = null;
		for (Channel node : cList) {
			if (id.equals(node.getId())) {
				channelName = node.getName();
				break;
			}
		}
		return channelName;
	}

	public static String getChannelLogoUrlById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(cList))
			return "";
		String channelName = null;
		for (Channel node : cList) {
			if (id.equals(node.getId())) {
				channelName = node.getLogoUrl();
				break;
			}
		}
		return channelName;
	}

	public static String getChannelLogoUrl2ById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(cList))
			return "";
		String channelName = null;
		for (Channel node : cList) {
			if (id.equals(node.getId())) {
				channelName = node.getLogoUrl2();
				break;
			}
		}
		return channelName;
	}

	public static ChannelType getChannelTypeById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(ctList))
			return null;
		ChannelType channel = null;
		for (ChannelType node : ctList) {
			if (id.equals(node.getId())) {
				channel = node;
				break;
			}
		}
		return channel;
	}

	public static String getTypeCodeById(String id) {
		if (StringUtil.isEmpty(id) || CollectionUtils.isEmpty(ctList))
			return "";
		String code = "";
		for (ChannelType node : ctList) {
			if (id.equals(node.getId())) {
				code = node.getCode();
				break;
			}
		}
		return code;
	}
	
	/**
	 * 通过Channel的name获取其id 创建人：lizhenghg 创建时间：2016-11-20
	 * 
	 * @param name
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	public static String getIdByName(String name) {
		if (StringUtils.isEmpty(name))
			return null;
		String id = null;
		Channel channel = null;
		Iterator<Channel> it = cList.iterator();
		while (it.hasNext()) {
			channel = it.next();
			if (name.equals(channel.getName())) {
				id = channel.getId();
				break;
			}
		}
		return id;
	}

	/**
	 * 通过Channel的otherName获取其id 创建人：lizhenghg 创建时间：2016-11-20
	 * 
	 * @param name
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	public static String getIdByOtherName(String otherName) {
		if (StringUtils.isEmpty(otherName))
			return null;
		String id = null;
		Channel channel = null;
		boolean flag = false;
		Iterator<Channel> itr = cList.iterator();
		while (itr.hasNext()) {
			channel = itr.next();
			String[] otherNames = null;
			if (StringUtils.isNotBlank(channel.getOtherName())) {
				otherNames = channel.getOtherName().split(",");
				for (String name : otherNames) {
					if (name.equals(otherName)) {
						id = channel.getId();
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}
		return id;
	}
}
