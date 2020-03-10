package com.br.ott.client.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.api.entity.ResponsePrograms;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ProgramApiService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-3-2 - 上午10:24:28
 */
@Service
public class ProgramApiService {

	@Autowired
	private ProgramsMapper programsMapper;

	private static final Logger logger = Logger
			.getLogger(ProgramApiService.class);

	/**
	 * 根据频道和节目名获取节目回看信息列表(当前时间至三天前的)
	 * 
	 * 创建人：pananz 创建时间：2015-3-2 - 上午10:34:01
	 * 
	 * @param cid
	 * @param pname
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String lockBackPrograms(String cid, String pname)
			throws OTTException {
		Programs programs = new Programs();
		programs.setChannelId(cid);
		String word = StringUtil.filterWord(pname);
		logger.debug(pname + " ->节目回看,在过滤后的节目名称->" + word);
		programs.setName(word);
		programs.setcStatus("1");
		programs.setCurrentPage(1);
		programs.setShowCount(15);
		Date date = new Date();
		programs.setPlayTimeMin(DateUtil.toString(DateUtil.addDay(date, -3)));
		programs.setPlayTimeMax(DateUtil.toString(date));
		programs.setOrderColumn("op.playTime");
		int totalResult=programsMapper.getCountPrograms(programs);
		programs.setTotalResult(totalResult);
		List<Programs> plist = programsMapper.findProgramsByPage(programs);
		List<ResponsePrograms> list = new ArrayList<ResponsePrograms>();
		JSONObject jsonObject = new JSONObject();
		for (Programs p : plist) {
			ResponsePrograms rp = new ResponsePrograms();
			rp.setChannelId(p.getChannelId());
			rp.setChannelName(p.getChannelName());
			if (StringUtil.isNotEmpty(p.getcLogoUrl())) {
				rp.setLogoUrl(Global.SERVER_SOURCE_URL + p.getcLogoUrl());
			}
			rp.setProgramId(p.getId());
			rp.setProgramName(p.getName());
			rp.setPlayDtime(p.getPlayTime());
			rp.setTimeOut(p.getTimeLongth());
			rp.setPlayId(p.getPlayId());
			if (!"0".equals(p.getQueue())) {
				rp.setQueue(p.getQueue());
			}
			list.add(rp);
		}
		jsonObject.put("total", programs.getTotalResult());
		jsonObject.put("pageCount", programs.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("resultCode", "0");
		plist = null;
		list = null;
		return jsonObject.toString();
	}
}
