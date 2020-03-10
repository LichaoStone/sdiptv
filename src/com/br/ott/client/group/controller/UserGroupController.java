package com.br.ott.client.group.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.api.entity.OperatorsTV;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.group.entity.UserGroup;
import com.br.ott.client.group.service.UserGroupService;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.service.OperatorsChannelService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/**
 * 创建时间：2016-10-11
 */
@Controller
@RequestMapping(value = "/group")
public class UserGroupController extends BaseController {
	@Autowired
	private UserGroupService mUserGroupService;
	@Autowired
	private OperaLogService operaLogService;
	@Autowired
	private OperatorsChannelService operatorsChannelService;

	private static final String BUSI_NAME = "用户组信息管理";

	/**
	 * 按分页查询用户组信息
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param request
	 * @param @param usergroup
	 * @param @param model
	 * @param @return
	 * @param @throws Exception 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findUserGroup")
	public String findUserGroup(HttpServletRequest request,
			UserGroup usergroup, Model model) throws Exception {
		usergroup.setCurrentPage(getPageNo(request));
		usergroup.setShowCount(getPageRow(request));
		usergroup.setOrderColumn("operators,groupCode");
		List<UserGroup> list = mUserGroupService.findUserGroupByPage(usergroup);
		model.addAttribute("usergroups", list);
		model.addAttribute("usergroup", usergroup);
		list = null;

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		olist = null;
		return "group/listUserGroup";
	}

	/**
	 * 按运营商编码取得用户组信息
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param request
	 * @param @return 返回类型：List<UserGroup>
	 * @exception throws
	 */
	@RequestMapping(value = "changeUserGroup")
	public @ResponseBody
	List<UserGroup> changeUserGroup(HttpServletRequest request) {
		String operators = request.getParameter("operators");
		UserGroup ug = new UserGroup();
		ug.setOperators(operators);
		ug.setStatus("1");
		List<UserGroup> ocList = mUserGroupService.findUserGroupByCond(ug);
		return ocList;
	}

	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping(value = "toUserGroup")
	public String toUserGroup(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("id");
		UserGroup mUserGroup = new UserGroup();
		if (!StringUtil.isEmpty(id)) {
			mUserGroup = mUserGroupService.findUserGroupById(id);
		}
		model.addAttribute("usergroup", mUserGroup);

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		olist = null;
		return "group/usergroupInfo";
	}

	@RequestMapping(value = "getGroupChannel")
	public @ResponseBody
	List<OperatorsChannel> getGroupChannel(HttpServletRequest request,
			Model model) throws Exception {
		String operators = request.getParameter("operators");
		model.addAttribute("operators", operators);
		OperatorsChannel operatorsChannel = new OperatorsChannel();
		operatorsChannel.setOperators(OperatorsCache
				.getOperatorsByCode(operators));
		operatorsChannel.setStatus("1");
		operatorsChannel.setOrderColumn("oc.playChannelId");
		List<OperatorsChannel> ocList = operatorsChannelService
				.findOperatorsChannelByCond(operatorsChannel);
		model.addAttribute("ocList", ocList);
		return ocList;
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "saveUserGroup")
	public @ResponseBody
	Feedback saveUserGroup(HttpServletRequest request, UserGroup usergroup,
			Model model) {
		try {
			String id = request.getParameter("id");
			if (StringUtil.isEmpty(id)) {
				usergroup.setStatus("1");
				String groupChannels = request.getParameter("groupChannels");
				mUserGroupService.insertUserGroup(usergroup, groupChannels);
				operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
						"新增用户组信息名称为：" + usergroup.getGroupName());
			} else {
				UserGroup old = mUserGroupService.findUserGroupById(id);
				mUserGroupService.updateUserGroup(usergroup);
				try {
					String diffStr = ObjectUtil.getDiffColumnDescript(old,
							usergroup);
					if (null != diffStr) {
						// 写入系统操作日志
						operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
								BUSI_NAME, diffStr);
					}
				} catch (Exception e) {
				}
			}
			// 增加同步用组信息到EPG服务器

			return Feedback.success("保存成功");
		} catch (Exception e) {
			return Feedback.fail("保存失败");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteUserGroupById")
	public @ResponseBody
	Feedback deleteUserGroupById(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		boolean flag = false;
		try {
			mUserGroupService.deleteUserGroupById(id);
			flag = true;
			// 增加同步用组信息到EPG服务器

		} catch (Exception e) {
			return Feedback.fail("删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除用户组信息编号为：" + id);
		}
		return Feedback.success("删除成功");
	}

	/**
	 * 用户组运营商频道停用
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param id
	 * @param @param request
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeUserGroup", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeUserGroup(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("用户组停用失败,请选择要停用的用户组");
		}
		try {
			mUserGroupService.updateUserGroupStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("用户组停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用用户组频道编号为：" + id);
		}
		return Feedback.success("用户组停用成功");
	}

	/**
	 * 用户组运营商频道启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseUserGroup", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseUserGroup(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("用户组启用失败,请选择要启用的用户组");
		}
		try {
			mUserGroupService.updateUserGroupStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("用户组启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用用户组编号为：" + id);
		}
		return Feedback.success("用户组启用成功");
	}

	/**
	 * 同步用户组信息
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param request
	 * @param @param model
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "syncGroupTo3A")
	public @ResponseBody
	Feedback syncGroupTo3A(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		boolean flag = false;
		try {
			UserGroup userGroup = mUserGroupService.findUserGroupById(id);
			// 同步用组信息到3A服务器
			String result = mUserGroupService.sysTo3A(userGroup);
			flag = true;
			if (flag) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_DELETE, request,
						BUSI_NAME, "同步用户组信息编号为：" + id);
			}
			return Feedback.success("同步用户组信息:3A平台返回结果" + result);
		} catch (Exception e) {
			return Feedback.fail("同步用户组信息失败");
		}
	}

	@RequestMapping(value = "syncGroupToLaunch")
	public @ResponseBody
	Feedback syncGroupToLaunch(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		boolean flag = false;
		try {
			UserGroup userGroup = mUserGroupService.findUserGroupById(id);
			// 同步用组信息到launcher服务器,
			String result = mUserGroupService.sysToLauncher(userGroup);
			flag = true;
			if (flag) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_DELETE, request,
						BUSI_NAME, "同步用户组信息编号为：" + id);
			}
			return Feedback.success("同步用户组信息:电信launch平台返回结果" + result);
		} catch (Exception e) {
			return Feedback.fail("同步用户组信息失败");
		}
	}
}
