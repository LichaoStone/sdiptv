package com.br.ott.client.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.quartz.CCTVQuartz;
import com.br.ott.client.common.quartz.ProgramsUtils;
import com.br.ott.client.common.quartz.TvSouQuartz;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.CTypeMapper;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.jackson.TinyTreeBean;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方网络科技有限公司
 *  创建人：pananz
 *  创建时间：2014-5-16 - 下午5:53:30
 */
@Service
public class ChannelService {

	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private ProgramsMapper programsMapper;

	@Autowired
	private CTypeMapper cTypeMapper;

	public void addChannel(Channel channel) {
		channelMapper.addChannel(channel);
	}

	public void updateChannel(Channel channel) {
		channelMapper.updateChannel(channel);
	}

	public List<Channel> findChannelByPage(Channel channel) {
		if (StringUtil.isNotEmpty(channel.getType())) {
			List<CType> list = cTypeMapper.findCTypeByTypeId(channel.getType());
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> types = new ArrayList<String>();
				for (CType type : list) {
					types.add(type.getChannelId());
				}
				channel.setTypeList(StringUtils.join(types, ","));
			} else {
				return null;
			}
		}
		int totalResult = channelMapper.getCountChannel(channel);
		channel.setTotalResult(totalResult);
		return channelMapper.findChannelByPage(channel);
	}

	public Channel getChannelById(String id) {
		return channelMapper.getChannelById(id);
	}

	public List<Channel> findChannelByCond(Channel channel) {
		if (StringUtil.isNotEmpty(channel.getType())) {
			List<CType> list = cTypeMapper.findCTypeByTypeId(channel.getType());
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> types = new ArrayList<String>();
				for (CType type : list) {
					types.add(type.getChannelId());
				}
				channel.setType(StringUtils.join(types, ","));
			} else {
				channel.setType("");
			}
		}
		return channelMapper.findChannelByCond(channel);
	}

	public void addChannels(List<Channel> list) {
		channelMapper.addChannels(list);
	}

	public void updateChannelStatus(String status, String id) {
		channelMapper.updateChannelStatus(status, id);
	}

	public void delChannel(String id) {
		channelMapper.delChannel(id);
	}

	public boolean checkChannelByName(String name) {
		List<Channel> list = channelMapper.findChannelByName(name);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}

	public List<TinyTreeBean> buildTinyTreeBean(List<Channel> channels,
			String parentId) {
		if (CollectionUtils.isEmpty(channels)) {
			return null;
		}
		Map<String, TinyTreeBean> ptTreeMap = new HashMap<String, TinyTreeBean>();
		if (CollectionUtils.isEmpty(channels)) {
			TinyTreeBean root = new TinyTreeBean(parentId, null);
			root.setName("暂无频道");
			ptTreeMap.put(parentId, root);
			return ptTreeMap.get(parentId).getChildren();
		}
		for (Channel channel : channels) {
			TinyTreeBean node = new TinyTreeBean(channel.getId(),
					channel.getName());
			ptTreeMap.put(channel.getId(), node);
		}
		for (Channel pt : channels) {
			TinyTreeBean parent = ptTreeMap.get(pt.getParentId());
			if (null == parent) {
				continue;
			}
			parent.addChild(ptTreeMap.get(pt.getId()));
		}
		return ptTreeMap.get(parentId).getChildren();
	}

	private static final Logger logger = Logger.getLogger(ChannelService.class);

	public void reloadChannel() {
		List<Channel> list = channelMapper.findChannelByCond(new Channel());
		ChannelCache.reloadChannel(list);
	}

	/**
	 * 更新频道节目信息
	 * 
	 * 创建人：pananz 创建时间：2016-11-3
	 * 
	 * @param @param date
	 * @param @param id
	 * @param @param type
	 * @param @param createFile
	 * @param @param afterNow
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	public String updatePrograms(Date date, String id, String type,
			String createFile, String afterNow) {
		Channel channel = channelMapper.getChannelById(id);
		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		List<Programs> list = null;
		if ("tvsou".equals(type)) {
			list = TvSouQuartz.getProgramsByTS(channel,
					date, 1, tsMap);
		} else if ("cctv".equals(type)) {
			List<Programs> programs = CCTVQuartz.getProgramsByYS(channel, date,
					1, tsMap);
			list = CCTVQuartz.setTime(programs, channel, tsMap);
		}
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		List<Programs> pList = new ArrayList<Programs>();
		for (Programs p : list) {
			if ("1".equals(afterNow)) {
				long diff = DateUtil
						.dateDiff(date, DateUtil.parseDate(p.getPlayTime(),
								"yyyy-MM-dd HH:mm"));
				if (diff < 0) {// 超过当前时间的剔除
					logger.debug("超过当前时间:" + p.getPlayTime());
					continue;
				} else {
					pList.add(p);
				}
			} else {
				pList.add(p);
			}
		}
		if (CollectionUtils.isNotEmpty(pList)) {
			if ("1".equals(afterNow)) {// 当前时间后的数据
				programsMapper.delProgramsByCidAndDateMore(id,
						DateUtil.toString(date, "yyyyMMdd"));
			} else {// 当前时间天的数据
				programsMapper.delProgramsByCidAndDate(id,
						DateUtil.toString(date, "yyyy-MM-dd"));
			}
			programsMapper.addProgramsByList(pList);
			//TvSouQuartz.syncNameTodbcms(programsMapper,pList);
			if ("1".equals(createFile)) {
				try {
					ProgramsUtils.createDataFile(type + "_data-" + id, date,
							pList, Global.PROGRAM_UPLOAD_SOURCE_URL);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("写：" + DateUtil.toString(date) + "节目单出错");
				}
			}
		}
		return String.valueOf(pList.size());
	}
}
