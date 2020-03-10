package com.br.ott.client.api.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.exception.SystemExceptionCode.ApiCode;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：VoiceApiService.java
 *  创建人：pananz
 *  创建时间：2014-9-22 - 上午10:43:12
 */
@Service
public class VoiceApiService {
	private static Logger log = Logger.getLogger(VoiceApiService.class);

	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;

	@Autowired
	private ProgramsMapper programsMapper;

	/**
	 * 语音搜索接口 创建人：pananz 创建时间：2014-9-22 - 上午10:45:21
	 * 
	 * @param request
	 *            语音搜索需求： 1. 优先查频道 2. 频道没找到优先查直播的节目 3. 直播没找到在去前3天的回看查找节目
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String 频道名称 标识 (直播还是回看) 播放时间
	 * @exception throws
	 */
	public String voiceSearch(JSONObject json) throws OTTException {
		String keyword = "";
		String operatorsCode = "";
		try {
			keyword = StringUtil.getJsonStr(json, "keyword");
			operatorsCode = StringUtil.getJsonStr(json, "operators");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OTTException(ApiCode.DECODE_JSON_ERROR);
		}
		log.debug("keyword: " + keyword + ", operators: " + operatorsCode);
		JSONObject jsonObject = new JSONObject();
		String operators=OperatorsCache.getOperatorsIdByCode(operatorsCode);
		List<OperatorsChannel> channels = operatorsChannelMapper
				.findOperatorsChannelByMoreName(operators, keyword);
		if (CollectionUtils.isNotEmpty(channels)) {
			jsonObject.put("keyword", channels.get(0).getChannelName());
			jsonObject.put("playTime", "");
			jsonObject.put("timeLongth", "");
			jsonObject.put("isLiving", "1");
			jsonObject.put("localCid", channels.get(0).getPlayChannelId());
			channels = null;
			log.debug("频道运营商查询频道，频道不为空");
			return jsonObject.toString();
		} else {// 先查询直播节目
			Programs programs = new Programs();
			programs.setName(keyword);
			programs.setPlayNow("yes");
			programs.setCurrentPage(1);
			programs.setShowCount(1);
			programs.setcStatus("1");
			programs.setOperators(operators);
			programs.setOrderColumn("oc.playChannelId");
			List<Programs> list = programsMapper
					.findCProgramsByPageAndOperators(programs);
			if (CollectionUtils.isNotEmpty(list)) {
				Programs p = list.get(0);
				jsonObject.put("keyword", p.getChannelName());
				jsonObject.put("playTime", p.getPlayTime());
				jsonObject.put("isLiving", "1");
				jsonObject.put("timeLongth", p.getTimeLongth());
				jsonObject.put("localCid", p.getLocalCid());
				log.debug("频道运营商查询节目，频道不为空");
			} else {// 如直播节目没有查询回看内容
				Programs pro = new Programs();
				pro.setName(keyword);
				pro.setCurrentPage(1);
				pro.setShowCount(1);
				pro.setcStatus("1");
				pro.setOperators(operators);
				// 频道升序，播放时间降序
				pro.setOrderColumn("oc.id ,op.playTime desc");
				pro.setPlayTimeMin(DateUtil.toString(DateUtil.addDay(
						new Date(), -3)));
				pro.setPlayTimeMax(DateUtil.toString(new Date()));
				List<Programs> pros = programsMapper
						.findCProgramsByPageAndOperators(pro);
				log.debug("频道运营商查询回看节目");
				if (CollectionUtils.isNotEmpty(pros)) {
					Programs p = pros.get(0);
					jsonObject.put("keyword", p.getChannelName());
					jsonObject.put("playTime", p.getPlayTime());
					jsonObject.put("localCid", p.getLocalCid());
					jsonObject.put("isLiving", "0");
					jsonObject.put("timeLongth", p.getTimeLongth());
				}
				pros = null;
			}
			list = null;
			return jsonObject.toString();
		}
	}
}
