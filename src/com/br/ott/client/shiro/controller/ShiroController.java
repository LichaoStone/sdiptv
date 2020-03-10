/* 
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Founder. You shall not disclose such Confidential Information 
 * and shall use it only in accordance with the terms of the agreements 
 * you entered into with Founder. 
 * 
 */

package com.br.ott.client.shiro.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.ott.Global;
import com.br.ott.base.BaseController;
import com.br.ott.client.shiro.entity.Groups;
import com.br.ott.client.shiro.entity.Resources;
import com.br.ott.client.shiro.entity.Role;
import com.br.ott.client.shiro.entity.UserInfo;
import com.br.ott.common.jackson.TinyTreeBean;
import com.br.ott.common.util.Constants;
import com.br.ott.common.util.StringUtil;

/**
 * 登录 Controller This class is used for ...
 * 
 * @author zhuw
 * @version 1.0 2012-7-25
 */
@Controller
@RequestMapping(value = "shiro")
public class ShiroController extends BaseController {

	private static Logger log = Logger.getLogger(ShiroController.class);
	private static final String ROOT_MENU_ID = "0";

	/**
	 * 登录
	 * 
	 * @param loginName
	 * @param password
	 * @param keeptime
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login")
	public String login(String loginName, String password, String keeptime,
			Model model, HttpServletRequest request) {
		try {
			return checkUserLogin(loginName, password, keeptime, model, request);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "redirect:/app";
		}
	}

	/**
	 * 转到应用
	 * 
	 * @param keeptime
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toApply")
	public String toApply(String keeptime, Model model,
			HttpServletRequest request) {
		try {
			// Community community = SessionUtil.getCommunUser();
			// if (null != community) {
			// String loginName = community.getAdminName();
			// String password = community.getAdminPwd();
			// return checkUserLogin(loginName, password, keeptime, model,
			// request);
			// } else {
			return "redirect:/index";
			// }
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "redirect:/index";
		}
	}

	/**
	 * 校验用户登录
	 * 
	 * @param loginName
	 * @param password
	 * @param keeptime
	 * @param model
	 * @param request
	 * @return
	 */
	private String checkUserLogin(String loginName, String password,
			String keeptime, Model model, HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject(); // 获取当前Subject,Subject对象代表当前用户
		// if (!currentUser.isAuthenticated()) { // 判断用户是否登录
		// (在此期间需要使用有效的凭据登录系统，否则值为false.)
		// 提交身份和证明
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,
				password, Boolean.valueOf(keeptime));
		token.setRememberMe(true);
		try {
			// 提交身份和证明
			currentUser.login(token);
			/**
			// 判断用户是否拥有特定的角色
			if (currentUser.hasRole("admin")) {
				log.info("May the Schwartz be with you!");
				// 显示‘Create User’按钮
				// result.append("/orgemp/** = authc,roles[11],roles[12]...roles[N]");
				// // 允许多个角色访问同一个URL
			} else {
				log.info("Hello, mere mortal.");
				// 按钮置灰
			}
			// 判断用户是否对特定某实体有操作权限
			if (currentUser.isPermitted("lightsaber:weild")) {
				log.info("You may use a lightsaber ring. Use it wisely.");
			} else {
				log.info("Sorry, lightsaber rings are for schwartz masters only.");
			}
			// 判断用户是否有访问特定类型实例的权限
			if (currentUser.isPermitted("winnebago:drive:eagle5")) {
				log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'. "
						+ "Here are the keys - have fun!");
			} else {
				log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
			}
			 * 
			 */
			return "redirect:/portal";
		} catch (UnknownAccountException uae) {
			log.info("There is no user with username of "
					+ token.getPrincipal());
			model.addAttribute("message", "用户名或密码错误！");
			return "shiro/login";
		} catch (IncorrectCredentialsException ice) {
			log.info("Password for account " + token.getPrincipal()
					+ " was incorrect!");
			model.addAttribute("message", "证书不正确！ 稍后再试");
			return "shiro/login";
		} catch (LockedAccountException lae) {
			log.info("The account for username " + token.getPrincipal()
					+ " is locked.  "
					+ "Please contact your administrator to unlock it.");
			model.addAttribute("message", "帐户锁定异常！ 稍后再试");
			return "shiro/login";
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			model.addAttribute("message", "系统异常！ 稍后再试");
			return "shiro/login";
		}
	}

	/**
	 * 退出
	 * 
	 * @author zhuw
	 * @param request
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// 释放所有已知的身份信息
			subject.logout();
		}
		return "redirect:/app";
	}

	/**
	 * 转到管理主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "home")
	public String home(Model model, HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		@SuppressWarnings("unchecked")
		List<Resources> list = (List<Resources>) currentUser.getSession()
				.getAttribute(Constants.USER_RESOURCE);
		if (CollectionUtils.isNotEmpty(list)) {
			boolean flag = checkRoleIsAdmin(currentUser);
			List<TinyTreeBean> menus = buildMenuPrivilegeTree(list, flag);
			sortMenu(menus);
			SecurityUtils.getSubject().getSession()
					.setAttribute(Constants.USER_MENU_PRI, menus);
		}
		list = null;
		return "index/index";
	}

	/**
	 * 页面顶部
	 * 
	 * @return
	 */
	@RequestMapping(value = "top")
	public String top() {
		return "index/top";
	}

	/**
	 * 页面底部
	 * 
	 * @return
	 */
	@RequestMapping(value = "footer")
	public String footer() {
		return "index/footer";
	}

	/**
	 * 页面中部
	 * 
	 * @return
	 */
	@RequestMapping(value = "drag")
	public String drag() {
		return "index/drag";
	}

	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		log.debug("跳转到登录页面...");
		return "shiro/login";
	}

	/**
	 * 登录成功后加载该用户可操作权限树
	 * 
	 * @author zhuw
	 * @return
	 */

	@RequestMapping(value = "getIndexMenu", method = RequestMethod.GET)
	public String getIndexMenu() {
		return "index/index_menu";
	}

	@SuppressWarnings("unchecked")
	private boolean checkRoleIsAdmin(Subject currentUser) {
		boolean flag = false;
		UserInfo userInfo = (UserInfo) currentUser.getSession().getAttribute(
				Constants.CURRENT_USER);
		if (userInfo == null)
			return flag;
		List<Role> roles = (List<Role>) currentUser.getSession().getAttribute(
				Constants.USER_ROLE);
		List<String> names = new ArrayList<String>();
		for (Role role : roles) {
			names.add(role.getEnname());
		}
		Set<Groups> userGroups = userInfo.getGroups();
		boolean bool = false;
		for (Groups g : userGroups) {
			if (Constants.GROUP_SUPER.equals(g.getEnName())) {
				bool = true;
				break;
			}
		}
		if (names.contains(Constants.ROLE_PARTNER) && bool) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 构造用户资源成树型展示
	 * 
	 * @param entMenuPrivileges
	 * @return
	 */
	private List<TinyTreeBean> buildMenuPrivilegeTree(
			List<Resources> entMenuPrivileges, boolean flag) {
		TinyTreeBean root = new TinyTreeBean(ROOT_MENU_ID, null);
		Map<String, TinyTreeBean> menuIdTreeMap = new HashMap<String, TinyTreeBean>();
		menuIdTreeMap.put(ROOT_MENU_ID, root);
		String[] menus = Global.MENU_FILTER.split(",");
		List<String> menuIds = Arrays.asList(menus);
		for (Resources each : entMenuPrivileges) {
			TinyTreeBean node = new TinyTreeBean(each.getId(), each.getName());
			node.setUrl(each.getLink());
			node.setHide(each.changeIsOpen());
			node.selectStyle();
			if (StringUtil.isEmpty(each.getOrderid())) {// 因要比较大小，当ORDERID为空时，设置一个靠后的排序方式
				node.setOrderId("9999999999");
			} else {
				node.setOrderId(each.getOrderid());
			}
			menuIdTreeMap.put(each.getId(), node);
		}
		for (Resources each : entMenuPrivileges) {
			if (flag && menuIds.contains(each.getId())) {
				continue;
			}
			TinyTreeBean parent = menuIdTreeMap.get(each.getParentId());
			if (parent == null) {
				continue;
			}
			parent.addChild(menuIdTreeMap.get(each.getId()));
		}
		return menuIdTreeMap.get(ROOT_MENU_ID).getChildren();
	}

	/**
	 * 菜单按ORDERID排序
	 * 
	 * @param menus
	 */
	@SuppressWarnings("unchecked")
	private void sortMenu(List<TinyTreeBean> menus) {
		if (CollectionUtils.isEmpty(menus))
			return;
		BeanComparator menuBC = new BeanComparator("orderId");// 排序
		Collections.sort(menus, menuBC);
		for (TinyTreeBean bean : menus) {
			sortMenu(bean.getChildren());
		}
	}
}
