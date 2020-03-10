/*
 * @# UserAccountApiService.java Sep 4, 2012 10:39:00 AM
 * 
 * Copyright (C) 2011 - 2012 青岛博瑞
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.client.api.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.user.entity.UserAccount;
import com.br.ott.client.user.mapper.UserAccountMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.exception.SystemExceptionCode.ApiCode;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.id.CreateIdentityId;
import com.br.ott.common.util.security.AES2;

/*
 * @author pananz
 * TODO
 */
@Service
public class UserAccountApiService {

	@Autowired
	private UserAccountMapper userMapper;

	/**
	 * 校验唯一性 创建人：pananz 创建时间：2014-7-4 - 下午12:11:03
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String checkUser(HttpServletRequest request, JSONObject json)
			throws OTTException {

		String checktype = json.getString("checktype");
		String checkContent = json.getString("checkContent");
		// 1，用户名 ，2，手机号，3邮箱）
		UserAccount userAccount = new UserAccount();
		if ("1".equals(checktype)) {
			userAccount.setUserName(checkContent);
		} else if ("2".equals(checktype)) {
			userAccount.setPhone(checkContent);
		} else if ("3".equals(checktype)) {
			userAccount.setEmail(checkContent);
		} else {
			throw new OTTException(ApiCode.USER_HAV_INFO);
		}
		List<UserAccount> list = userMapper.findUserAccountByCond(userAccount);
		if (list.size() > 0) {
			throw new OTTException(ApiCode.USER_HAV_INFO);
		} else {
			return "{\"result\":\"0\"}";
		}
	}

	/**
	 * 增加修改用户信息接口 创建人：pananz 创建时间：2014-7-4 - 上午11:22:30
	 * 
	 * @param request
	 *            operate Varchar（2） 操作（1，新增 ，2，修改） 是 username Varchar（16） 用户名 是
	 *            pwd Varchar（16） 6-16位的数字字母组合 否 age Varchar（3） 年龄 否 sex
	 *            Varchar（2） 性别（1，男 2，女） 否 mail Varchar（128） 邮箱 否 phone
	 *            Varchar（16） 手机号 是
	 * 
	 * @param json
	 * @return 返回类型：String
	 * @throws OTTException
	 * @exception throws
	 */
	public String addUser(HttpServletRequest request, JSONObject json)
			throws OTTException {
		String operate = json.getString("operate");
		String username = json.getString("username");
		String pwd = json.getString("pwd");
		String birthday = json.getString("birthday");
		String sex = json.getString("sex");
		String mail = json.getString("mail");
		String phone = json.getString("phone");
		String userId = "";
		if ("1".equals(operate)) {
			// 校验手机号
			UserAccount account = new UserAccount();
			account.setPhone(phone);
			List<UserAccount> users = userMapper.findUserAccountByCond(account);
			if (CollectionUtils.isNotEmpty(users)) {
				throw new OTTException(ApiCode.USER_HAV_INFO);
			}
			if (StringUtil.isNotEmpty(mail)) {// 校验邮箱
				UserAccount user = new UserAccount();
				user.setEmail(mail);
				List<UserAccount> list = userMapper
						.findUserAccountByCond(account);
				if (CollectionUtils.isNotEmpty(list)) {
					throw new OTTException(ApiCode.USER_HAV_INFO);
				}
			}

			userId = createUserId();
			account.setNickName(username);
			account.setPwd(pwd);
			account.setSex(sex);
			account.setBirthday(birthday);
			userMapper.addUser(account);

		} else {// 修改时
			userId = request.getHeader("userid");
			if (StringUtil.isEmpty(userId)) {
				throw new OTTException(ApiCode.NO_USERID);
			}
			UserAccount user = userMapper.getUserAccountByUserId(userId);
			if (!phone.equals(user.getPhone())) {
				UserAccount account = new UserAccount();
				account.setPhone(phone);
				List<UserAccount> users = userMapper
						.findUserAccountByCond(account);
				if (CollectionUtils.isNotEmpty(users)) {
					throw new OTTException(ApiCode.USER_HAV_INFO);
				}
			}
			if (!mail.equals(user.getEmail())) {
				UserAccount account = new UserAccount();
				account.setEmail(mail);
				List<UserAccount> users = userMapper
						.findUserAccountByCond(account);
				if (CollectionUtils.isNotEmpty(users)) {
					throw new OTTException(ApiCode.USER_HAV_INFO);
				}
			}
			user.setNickName(username);
			user.setBirthday(birthday);
			user.setSex(sex);
			user.setPhone(phone);
			user.setEmail(mail);
			if (StringUtil.isNotEmpty(pwd)) {
				user.setPwd(pwd);
			}
			userMapper.modifyUserAccount(user);
		}
		String userToken = createUserToken(userId);
		return "{\"uid\":\"" + userId + "\",\"userToken\":\"" + userToken
				+ "\"}";
	}

	/**
	 * 产生userId 创建人：pananz 创建时间：2014-7-4 - 下午12:47:17
	 * 
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String createUserId() {
		String userId = CreateIdentityId.getInstance().createId("ld");
		UserAccount user = userMapper.getUserAccountByUserId(userId);
		if (user != null) {
			return createUserId();
		} else {
			return userId;
		}
	}

	/**
	 * 用户登录 创建人：pananz 创建时间：2014-7-4 - 下午12:19:14
	 * 
	 * @param request
	 * @param json
	 *            account Varchar（16） 用户账号（用户名/手机号） 是 pwd Varchar（16）
	 *            6-16位的数字字母组合 是
	 * 
	 * @return uid Varchar（16） 用户唯一标识 是 username Varchar（16） 用户名 是 userToken
	 *         Varchar（64） 用户令牌 否 age Varchar（2） 年龄 否 sex Varchar（2） 性别 否 mail
	 *         Varchar（128） 邮箱 否 phone Varchar（16） 手机号 是
	 * 
	 *         返回类型：String
	 * @throws OTTException
	 * @exception throws
	 */
	public String login(HttpServletRequest request, JSONObject json)
			throws OTTException {
		String account = json.getString("account");
		String pwd = json.getString("pwd");
		UserAccount user = userMapper.checkLogin(account, pwd);
		if (user == null) {
			throw new OTTException(ApiCode.USER_AUTH_FAIL);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", user.getUserId());
		map.put("username", user.getNickName());
		map.put("userToken", createUserToken(user.getUserId()));
		map.put("birthday", user.getBirthday());
		map.put("sex", user.getSex());
		map.put("mail", user.getEmail());
		map.put("phone", user.getPhone());
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	// userToken加密密钥
	private static final String LICENSE_KEY = "ldtv999";

	/**
	 * 产生userToken 创建人：pananz 创建时间：2013-12-30 - 下午1:53:31
	 * 
	 * @param mac
	 * @param userId
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String createUserToken(String userId) throws OTTException {
		if (StringUtil.isEmpty(userId)) {
			throw new OTTException(ApiCode.NO_USERID);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(userId).append(";").append(DateUtil.getCurrentmin());
		try {
			return AES2.encrypt(sb.toString(), LICENSE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OTTException(ApiCode.USER_CREATE_TOKEN_ERROR);
		}
	}

}
