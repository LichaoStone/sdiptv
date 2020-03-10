package com.br.ott.client.api.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.ott.base.BaseApiController;
import com.br.ott.client.api.service.ChannelApiService;
import com.br.ott.client.api.service.ChannelTypeApiService;
import com.br.ott.client.api.service.LiveApiService;
import com.br.ott.client.api.service.ProgramApiService;
import com.br.ott.client.api.service.UserFollowApiService;
import com.br.ott.client.api.service.UserProgramsApiService;
import com.br.ott.client.api.service.VoiceApiService;
import com.br.ott.client.live.service.BasicProgramService;
import com.br.ott.common.exception.OTTException;

/* 
 *  
 *  文件名：LiveApiController.java
 *  创建人：pananz
 *  创建时间：2014-7-3 - 上午11:21:30
 */
@Controller
@RequestMapping(value = "/api")
public class LiveApiController extends BaseApiController {
	@Autowired
	private LiveApiService liveApiService;

	@Autowired
	private UserProgramsApiService userProgramsApiService;

	@Autowired
	private UserFollowApiService userFollowApiService;

	@Autowired
	private BasicProgramService basicprogramService;

	@Autowired
	private VoiceApiService voiceApiService;

	@Autowired
	private ChannelApiService channelApiService;

	@Autowired
	private ChannelTypeApiService channelTypeApiService;

	@Autowired
	private ProgramApiService programApiService;

	private static Logger logger = Logger.getLogger(LiveApiController.class);
	/**
	 * 查询手机端频道类型 创建人：pananz 创建时间：2014-7-12 - 上午11:46:04    C端
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "findType")
	public void findType(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject content = getJSONObj2(request);
			String operators = getStringFromObject(content, "operators");			
			writeJson(response, channelTypeApiService.findType(operators));
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findType出现异常：" + e.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findType出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	/**
	 * 查询手机端频道类型 创建人：pananz 创建时间：2014-7-12 - 上午11:46:04     B端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findType2")
	public void findType2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			String operators = getStringFromObject(content, "operators");
			String result = channelTypeApiService.findType(operators);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findType2出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findType2出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 查询频道列表 创建人：pananz 创建时间：2014-7-3 - 上午11:24:35
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCollects")
	public void findCollects(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "operators", false);
			getStringFromJsonObject(jsonObject, "tid", false);
		    result = channelApiService.findCollects(jsonObject);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} finally {
			if(StringUtils.isNotBlank(result))
				result = null;
		}
	}

	/**
	 * 查询频道列表 创建人：pananz 创建时间：2014-7-3 - 上午11:24:35    B端
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "findCollect2")
	public void findCollects2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			getStringFromObject(content, "operators");
			getStringFromObject(content, "tid");
			String result = channelApiService.findCollects(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findCollect2出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findCollect2出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 首页推荐频道类型                        B端
	 * 
	 * 创建人：pananz 创建时间：2016-11-16
	 * 
	 * @param @param request
	 * @param @param response 返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "findRecType")
	public void findRecType(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			String operators = getStringFromObject(content, "operators");
			String result = liveApiService.findRecType(operators);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findRecType出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findRecType出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 推荐直播查询                       B端
	 * 
	 * 创建人：pananz 创建时间：2016-11-16
	 * 
	 * @param @param request
	 * @param @param response 返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "findRecChannelLive")
	public void findRecChannelLive(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			getStringFromObject(content, "operators");
			getStringFromObject(content, "tid");
			String result = liveApiService.findRecChannelLive(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findRecChannelLive出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findRecChannelLive出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 查询直播内容 创建人：pananz 创建时间：2014-7-14 - 下午1:34:24       C端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCPrograms")
	public void findCPrograms(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "operators", false);
			String result = liveApiService.findCPrograms(jsonObject);
			writeJson(response, result);
			result = null;
			jsonObject = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findCPrograms出现异常：" + e.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findCPrograms出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	/**
	 * 查询直播内容 创建人：pananz 创建时间：2014-7-14 - 下午1:34:24       B端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCProgram2")
	public void findCProgram2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			getStringFromObject(content, "operators");
			String result = liveApiService.findCPrograms(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findCProgram2出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findCProgram2出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 查询节目列表 创建人：pananz 创建时间：2014-7-3 - 上午11:25:57      C端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findPrograms")
	public void findPrograms(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "operators", false);
		    result = liveApiService.findPrograms(jsonObject);
			writeJson(response, result);
			result = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findPrograms出现异常：" + e.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findPrograms出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	/**
	 * 查询节目列表 创建人：pananz 创建时间：2014-7-3 - 上午11:25:57      B端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findProgram2")
	public void findProgram2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			getStringFromObject(content, "operators");
			String result = liveApiService.findPrograms(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法findProgram2出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法findProgram2出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 查询节目详细信息 创建人：pananz 创建时间：2014-7-3 - 上午11:25:57
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findProgramInfo")
	public void findProgramInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "cid", false);
			getStringFromJsonObject(jsonObject, "name", false);
			String result = basicprogramService.findBasicProgramByName(jsonObject);
			JSONObject json = new JSONObject();
			json.put("returnCode", "0");
			json.put("result", result);
			writeJson(response, json.toString());
		} catch (OTTException e) {
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "findProgramInfo2")
	public void findProgramInfo2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "cid", false);
			getStringFromJsonObject(jsonObject, "name", false);
			String result = basicprogramService.findBasicProgramByName(jsonObject);
			JSONObject json = new JSONObject();
			json.put("returnCode", "0");
			json.put("result", result);
			writeJson(response, callback + "(" + json.toString() + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 查询热门搜索内容 创建人：pananz 创建时间：2014-7-23 - 下午11:12:00          B端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findSearchLog")
	public void findSearchLog(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try{
			JSONObject content = getJSONObj2(request);
			String result = channelApiService.findSearchLog(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
		}catch(Exception e){
			logger.error(LiveApiController.class.getName() + "方法findSearchLog出现异常：" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + "2" + "\",\"returnMsg\":\"" + "系统异常，请稍后重试" + "\"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	@RequestMapping(value = "findSearchLog2")
	public void findSearchLog2(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			String result = channelApiService.findSearchLog(jsonObject);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	/**
	 * 用户预定/取消预定节目 创建人：pananz 创建时间：2014-7-3 - 上午11:28:37
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "reserveProgram")
	public void reserveProgram(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "programIds", false);
			String result = userProgramsApiService.reserveProgram(jsonObject);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "reserveProgram2")
	public void reserveProgram2(HttpServletRequest request,
			HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "programIds", false);
			String result = userProgramsApiService.reserveProgram(jsonObject);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 加入收藏或最近收看 创建人：pananz 创建时间：2014-7-3 - 下午1:11:21     C端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "collect")
	public void collect(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "channelid", false);
			getStringFromJsonObject(jsonObject, "operators", false);
			String result = userFollowApiService.collect(jsonObject);
			writeJson(response, result);
			result = null;
			jsonObject = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法collect出现异常：" + e.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法collect出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	@RequestMapping(value = "collect2")
	public void collect2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "channelid", false);
			getStringFromJsonObject(jsonObject, "operators", false);
			String result = userFollowApiService.collect(jsonObject);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response,callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 用戶是否已收藏某一頻道 创建人：pananz 创建时间：2014-8-12 - 上午11:00:08         C端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "isCollect")
	public void isCollect(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "channelid", false);
			String result = userFollowApiService.isCollect(jsonObject);
			writeJson(response, result);
			result = null;
			jsonObject = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法isCollect出现异常：" + e.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法isCollect出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	@RequestMapping(value = "isCollect2")
	public void isCollect2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "channelid", false);
			String result = userFollowApiService.isCollect(jsonObject);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 点播频道日志记录 创建人：pananz 创建时间：2014-8-9 - 下午12:30:26
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "addChannelLog")
	public void addChannelLog(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "cid", false);
			getStringFromJsonObject(jsonObject, "cname", false);
			String result = channelApiService.addChannelLog(request, jsonObject);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response,JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "addChannelLog2")
	public void addChannelLog2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "cid", false);
			getStringFromJsonObject(jsonObject, "cname", false);
			String result = channelApiService.addChannelLog(request, jsonObject);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 取消收藏或最近收看 创建人：pananz 创建时间：2014-7-3 - 下午1:11:54 (c端)
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "unCollect")
	public void unCollect(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "uid", false);
			getStringFromJsonObject(jsonObject, "type", false);
			getStringFromJsonObject(jsonObject, "operators", false);
			getStringFromJsonObject(jsonObject, "channelids", false);
			String result = userFollowApiService.unCollect(jsonObject);
			writeJson(response, result);
			result = null;
			jsonObject = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法unCollect出现异常：" + e.getMessage());
			writeJson(response,JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法unCollect出现异常：" + ex.getMessage());
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString());
		}
	}

	/**
	 * 取消收藏或最近收看 创建人：pananz 创建时间：2014-7-3 - 下午1:11:54      B端
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "unCollect2")
	public void unCollect2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback") == null ? "" : (request.getParameter("callback") + "(");
		try {
			JSONObject content = getJSONObj2(request);
			getStringFromObject(content, "uid");
			getStringFromObject(content, "type");
			getStringFromObject(content, "operators");
			getStringFromObject(content, "channelids");
			String result = userFollowApiService.unCollect(content);
			writeJson(response, callback + result + (callback.equals("") ? "" : ")"));
			result = null;
			content = null;
		} catch (OTTException e) {
			logger.error(LiveApiController.class.getName() + "方法unCollect2出现异常" + e.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + (callback.equals("") ? "" : ")"));
		} catch (Exception ex) {
			logger.error(LiveApiController.class.getName() + "方法unCollect2出现异常：" + ex.getMessage());
			writeJson(response, callback + JSONObject.fromObject("{\"returnCode\":\"2\",\"returnMsg\":\"系统异常，请稍后重试 \"}").toString() + (callback.equals("") ? "" : ")"));
		}
	}

	/**
	 * 语音查询 创建人：pananz 创建时间：2014-9-22 - 上午11:49:53
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "voiceSearch")
	public void voiceSearch(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "keyword", false);
			String result = voiceApiService.voiceSearch(jsonObject);
			JSONObject json = new JSONObject();
			json.put("returnCode", 0);
			json.put("result", result);
			writeJson(response, json.toString());
		} catch (OTTException e) {
			writeJson(response,JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "voiceSearch2")
	public void voiceSearch2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			getStringFromJsonObject(jsonObject, "keyword", false);
			String result = voiceApiService.voiceSearch(jsonObject);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 根据频道和节目名获取节目回看信息列表
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "lockBackPrograms")
	public void lockBackPrograms(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			String cid = getStringFromJsonObject(jsonObject, "cid", false);
			String pname = getStringFromJsonObject(jsonObject, "pname", false);
			String result = programApiService.lockBackPrograms(cid, pname);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response,JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "lockBackProgram2")
	public void lockBackProgram2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			String cid = getStringFromJsonObject(jsonObject, "cid", false);
			String pname = getStringFromJsonObject(jsonObject, "pname", false);
			String result = programApiService.lockBackPrograms(cid, pname);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}

	/**
	 * 根据频道名获取频道logo地址
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findChannelLogos")
	public void findChannelLogos(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj2(request);
			String channelNames = getStringFromJsonObject(jsonObject, "channelNames", false);
			String operators = getStringFromJsonObject(jsonObject, "operators", false);
			String result = channelApiService.findChannelByNames(operators, channelNames);
			writeJson(response, result);
		} catch (OTTException e) {
			writeJson(response, JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString());
		}
	}

	@RequestMapping(value = "findChannelLogo2")
	public void findChannelLogo2(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		try {
			JSONObject jsonObject = getJSONObj2(request);
			String channelNames = getStringFromJsonObject(jsonObject, "channelNames", false);
			String operators = getStringFromJsonObject(jsonObject, "operators", false);
			String result = channelApiService.findChannelByNames(operators, channelNames);
			writeJson(response, callback + "(" + result + ")");
		} catch (OTTException e) {
			writeJson(response, callback + "(" + JSONObject.fromObject("{\"returnCode\":\"" + e.getCode() + "\",\"returnMsg\":\"" + e.getMessage() + "\"}").toString() + ")");
		}
	}
}
