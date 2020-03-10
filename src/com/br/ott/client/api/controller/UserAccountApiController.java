/*
 * @# UserAccountApiController.java Sep 3, 2012 4:22:51 PM
 * 
 * Copyright (C) 2011 - 2012 青岛博瑞
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.client.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseApiController;
import com.br.ott.client.api.service.UserAccountApiService;
import com.br.ott.common.exception.OTTException;

/*
 * 用户对外信息接口
 * @author pananz
 */
@Controller
@RequestMapping(value = "/api")
public class UserAccountApiController extends BaseApiController {

	@Autowired
	private UserAccountApiService userAccountApiService;

	/**
	 * 用户唯一信息校验 创建人：pananz 创建时间：2014-7-4 - 上午10:09:19
	 * 
	 * @param request
	 *            checktype Varchar（2） 校验数据类型（1，用户名 ，2，手机号，3邮箱） 是 checkContent
	 *            Varchar（16） 需要校验的内容 是
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "checkUser", method = RequestMethod.POST)
	public @ResponseBody
	String checkUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj(request);
			getStringFromJsonObject(jsonObject, "checktype", false);
			getStringFromJsonObject(jsonObject, "checkContent", false);
			return success(response,
					userAccountApiService.checkUser(request, jsonObject));
		} catch (OTTException e) {
			return error(response, e.getCode(), e.getMessage());
		}
	}

	/**
	 * 增加修改用户 创建人：pananz 创建时间：2014-7-4 - 上午11:30:51
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public @ResponseBody
	String addUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj(request);
			getStringFromJsonObject(jsonObject, "operate", false);
			getStringFromJsonObject(jsonObject, "username", false);
			getStringFromJsonObject(jsonObject, "phone", false);
			return success(response,
					userAccountApiService.addUser(request, jsonObject));
		} catch (OTTException e) {
			return error(response, e.getCode(), e.getMessage());
		}
	}

	/**
	 * 用户登陆
	 * 
	 * 创建人：pananz 创建时间：2016-3-21
	 * 
	 * @param @param request
	 * @param @param response
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody
	String login(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObject = getJSONObj(request);
			getStringFromJsonObject(jsonObject, "account", false);
			getStringFromJsonObject(jsonObject, "pwd", false);
			return success(response,
					userAccountApiService.login(request, jsonObject));
		} catch (OTTException e) {
			return error(response, e.getCode(), e.getMessage());
		}
	}

}
