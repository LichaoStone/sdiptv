package com.br.ott.client.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.ott.base.BaseApiController;
import com.br.ott.client.common.entity.EpgProgram;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.service.ProgramsService;
import com.br.ott.common.exception.OTTException;

/**
 * 文件名：EPGProgramController.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2016-3-28
 */
@Controller
@RequestMapping(value = "/epg")
public class EPGProgramController extends BaseApiController {

	private static final Logger logger = Logger
			.getLogger(EPGProgramController.class);

	@Autowired
	private ProgramsService programsService;

	/**
	 * 查询节目单接口
	 * 
	 * 创建人：pananz 创建时间：2016-3-28
	 * 
	 * @param @param request
	 * @param @param response
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findEpgOfProgram")
	public void findEpgOfProgram(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject json = getJSONObj(request);
			String cid = getStringFromJsonObject(json, "cid", false);
			String day = getStringFromJsonObject(json, "day", false);
			Programs programs = new Programs();
			programs.setChannelId(cid);
			programs.setPlayTime(day);
			List<Programs> pList = programsService.findProgramsByCond(programs);
			List<EpgProgram> epList = new ArrayList<EpgProgram>();
			for (Programs p : pList) {
				EpgProgram ep = new EpgProgram(p.getName(), p.getChannelId(),
						p.getChannelName(), p.getPlayTime(), p.getTimeLongth(),
						p.getQueue(), p.getBasicName());
				epList.add(ep);
			}
			JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(epList);
			jsonObject.put("result", "0");
			jsonObject.put("programs", jsonArray.toArray());
			logger.debug("返回节目请求信息：" + jsonObject.toString());
			writeJson(response, jsonObject);
		} catch (OTTException e) {
			logger.error("查询节目单业务出错:" + e.getMessage());
			jsonObject.put("result", "-1");
			writeJson(response, jsonObject);
		}
	}

}
