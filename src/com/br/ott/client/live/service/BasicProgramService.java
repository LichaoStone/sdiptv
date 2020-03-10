package com.br.ott.client.live.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.BasicProgram;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.BasicProgramMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：BasicProgramService.java
 *  版权：BRLF.Copyright 2014-2015,All rights reserved
 *  公司名称：博瑞立方网络科技有限公司
 *  创建人：Administrator
 *  创建时间：2014-5-16 - 下午5:49:31
 */
@Service
public class BasicProgramService {
	private static final Logger logger = Logger
			.getLogger(BasicProgramService.class);
	@Autowired
	private BasicProgramMapper basicprogramMapper;

	@Autowired
	private ProgramsMapper programsMapper;

	/** 节目非特殊频道值 */
	private static final String FTXPDZ = "001";

	public void addBasicProgram(BasicProgram basicprogram) {
		basicprogramMapper.addBasicProgram(basicprogram);
	}

	public List<BasicProgram> findBasicProgramByPage(BasicProgram basicprogram) {
		int totalResult = basicprogramMapper.getCountBasicProgram(basicprogram);
		basicprogram.setTotalResult(totalResult);
		return basicprogramMapper.findBasicProgramByPage(basicprogram);
	}

	public BasicProgram findBasicProgramByName(String cid, String name) {
		List<BasicProgram> bprograms = basicprogramMapper
				.findBasicProgramByName(name);
		BasicProgram bp = null;
		for (BasicProgram bprogram : bprograms) {
			if (bprogram.getName().equals(name)
					&& bprogram.getChannelId().equals(cid)) {// 名称与频道完成匹配时
				bp = bprogram;
				break;
			} else if (bprogram.getName().equals(name)
					&& bprogram.getChannelId().equals(FTXPDZ)) {// 名称匹配,而频道非特殊频道时
				bp = bprogram;
				break;
			} else if (bprogram.getChannelId().equals(cid)
					&& bprogram.getName().length() > name.length()) {// 频道匹配，而查询得到的名称大于条件时
				bp = bprogram;
				break;
			} else {
				logger.debug("全名称查询节目匹配不上");
			}
		}
		if (bp == null) {// 当节目模糊查询基础数据还是不存在时，做节目名称过虑，再查询
			String word = StringUtil.filterWord(name);
			logger.debug(name + " ->在过滤后的节目名称->" + word);
			List<BasicProgram> bps = basicprogramMapper
					.findBasicProgramByName(word);
			for (BasicProgram bprogram : bps) {
				if (bprogram.getName().equals(word)
						&& bprogram.getChannelId().equals(cid)) {// 名称与频道完成匹配时
					bp = bprogram;
					logger.debug("名称与频道完成匹配");
					break;
				} else if (bprogram.getName().equals(word)
						&& bprogram.getChannelId().equals(FTXPDZ)) {// 名称匹配,而频道非特殊频道时
					bp = bprogram;
					logger.debug("名称匹配,而频道非特殊频道");
					break;
				} else if (bprogram.getChannelId().equals(cid)
						&& bprogram.getName().length() > word.length()) {// 频道匹配，而查询得到的名称大于条件时
					bp = bprogram;
					logger.debug("频道匹配，而查询得到的名称大于条件");
					break;
				} else {
					logger.debug("名称匹配不上");
				}
			}
		}
		return bp;
	}

	public String findBasicProgramByName(JSONObject jsonObject) {
		String cid = StringUtil.getJsonStr(jsonObject, "cid");
		String name = StringUtil.getJsonStr(jsonObject, "name");
		String opCode = StringUtil.getJsonStr(jsonObject, "operators");
//		BasicProgram bp = findBasicProgramByName(cid, name);
		Map<String, String> map = new HashMap<String, String>();
//		if (bp != null) {
//			map.put("programDesc", bp.getRemark());
//		}
		String operators = OperatorsCache.getOperatorsIdByCode(opCode);
		Programs programs = new Programs();
		programs.setStatus("1");
		programs.setOperators(operators);
		programs.setChannelId(cid);
		programs.setCurrentPage(1);
		programs.setShowCount(1);
		programs.setName(name);
		programs.setPlayTimeMin(DateUtil.toString(
				DateUtil.addDay(new Date(), -1), "yyyy-MM-dd HH:mm"));
		programs.setPlayTimeMax(DateUtil.toString(new Date(),
				"yyyy-MM-dd HH:mm"));
		programs.setOrderColumn("op.playTime desc");
		List<Programs> pList = programsMapper
				.findCProgramsByPageAndOperators(programs);

		if (CollectionUtils.isNotEmpty(pList)) {
			Programs oc = pList.get(0);
			if (oc != null) {
				map.put("logoUrl", oc.getLogoImgUrl());
				map.put("timeOut", oc.getTimeLongth());
				map.put("localCid", oc.getLocalCid());
				map.put("channelId", oc.getChannelId());
				map.put("channelName", oc.getChannelName());
				map.put("playId", oc.getPlayId());
				map.put("programId", oc.getId());
				map.put("programName", oc.getName());
				map.put("playDtime", oc.getPlayTime());
				map.put("dbPlay", oc.getDbPlay());
				map.put("singleLiveServer", oc.getSingleLiveServer());
//				map.put("singleLiveServer2", oc.getSingleLiveServer2());
//				map.put("groupLiveServer", oc.getGroupLiveServer());
//				map.put("groupLiveServer2", oc.getGroupLiveServer2());
			}else{
				return "";
			}
		}
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * 验证输入的常量名是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean findBProgramByName(String cid, String name) {
		List<BasicProgram> bprograms = basicprogramMapper
				.findBProgramByName(name);
		if (CollectionUtils.isEmpty(bprograms)) {
			return true;
		}
		for (BasicProgram bprogram : bprograms) {
			if (bprogram.getName().equals(name)
					&& bprogram.getChannelId().equals(cid)) {
				return false;
			}
		}
		return true;
	}

	public BasicProgram getBasicProgramById(String id) {
		return basicprogramMapper.getBasicProgramById(id);
	}

	public void updateBasicProgram(BasicProgram basicprogram) {
		basicprogramMapper.updateBasicProgram(basicprogram);
	}

}
