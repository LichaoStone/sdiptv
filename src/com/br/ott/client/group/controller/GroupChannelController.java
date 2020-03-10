package com.br.ott.client.group.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.br.ott.base.BaseController;
import com.br.ott.client.api.entity.OperatorsTV;
import com.br.ott.client.common.GroupCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.group.entity.GroupChannel;
import com.br.ott.client.group.entity.UserGroup;
import com.br.ott.client.group.service.GroupChannelService;
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
@RequestMapping(value = "/groupChannel")
public class GroupChannelController extends BaseController {
	private final Logger logger = Logger
			.getLogger(GroupChannelController.class);

	@Autowired
	private GroupChannelService groupChannelService;
	@AutowiredresolveBeanFromRequest
	private OperatorsChannelService operatorsChannelService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "用户组运营商频道管理";

	/**
	 * 按分页查询
	 */
	@RequestMapping(value = "findGroupChannel")
	public String findGroupChannel(HttpServletRequest request,
			GroupChannel groupChannel, Model model) throws Exception {
		groupChannel.setCurrentPage(getPageNo(request));
		groupChannel.setShowCount(getPageRow(request));
		List<GroupChannel> list = groupChannelService
				.findGroupChannelByPage(groupChannel);
		model.addAttribute("groupChannels", list);
		model.addAttribute("groupChannel", groupChannel);
		list = null;

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		olist = null;

		if (StringUtil.isNotEmpty(groupChannel.getOperators())) {
			List<UserGroup> ugList = GroupCache
					.findUserGroupByOperators(groupChannel.getOperators());
			model.addAttribute("ugList", ugList);
			ugList = null;
		}
		return "group/listGroupChannel";
	}

	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping(value = "toGroupChannel")
	public String toGroupChannel(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("id");
		GroupChannel groupChannel = new GroupChannel();
		if (!StringUtil.isEmpty(id)) {
			groupChannel = groupChannelService.findGroupChannelById(id);
			if (groupChannel != null) {
				OperatorsChannel operatorsChannel = new OperatorsChannel();
				operatorsChannel.setOperators(OperatorsCache
						.getOperatorsByCode(groupChannel.getOperators()));
				operatorsChannel.setStatus("1");
				List<OperatorsChannel> ocList = operatorsChannelService
						.findOperatorsChannelByCond(operatorsChannel);
				model.addAttribute("ocList", ocList);
				ocList = null;

				UserGroup userGroup = new UserGroup();
				userGroup.setOperators(groupChannel.getOperators());
				List<UserGroup> ugList = userGroupService
						.findUserGroupByCond(userGroup);
				model.addAttribute("ugList", ugList);
				ugList = null;
			}
		}
		model.addAttribute("groupChannel", groupChannel);

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		olist = null;
		return "group/groupChannelInfo";
	}

	/**
	 * 用户组运营商频道保存
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param request
	 * @param @param groupChannel
	 * @param @param model
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "saveGroupChannel")
	public @ResponseBody
	Feedback saveGroupChannel(HttpServletRequest request,
			GroupChannel groupChannel, Model model) {
		logger.debug(getClass().getName() + " save()");
		try {
			String id = request.getParameter("id");
			if (StringUtil.isEmpty(id)) {
				groupChannel.setStatus("1");
				groupChannelService.insertGroupChannel(groupChannel);
				operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
						"新增用户组运营商频道为：" + groupChannel.getGroupCode() + "频道:"
								+ groupChannel.getChannelName());
			} else {
				GroupChannel old = groupChannelService.findGroupChannelById(id);
				groupChannelService.updateGroupChannel(groupChannel);
				try {
					String diffStr = ObjectUtil.getDiffColumnDescript(old,
							groupChannel);
					if (null != diffStr) {
						// 写入系统操作日志
						operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
								BUSI_NAME, diffStr);
					}
				} catch (Exception e) {

				}
			}
			return Feedback.success("用户组运营商频道保存成功");
		} catch (Exception e) {
			return Feedback.success("用户组运营商频道保存失败");
		}

	}

	/**
	 * 删除用户组运营商频道
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param request
	 * @param @param model
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "deleteGroupChannelById")
	public @ResponseBody
	Feedback deleteGroupChannelById(HttpServletRequest request, Model model) {
		logger.debug(getClass().getName() + " delete()");
		String id = request.getParameter("id");
		boolean flag = false;
		try {
			groupChannelService.deleteGroupChannelById(id);
			flag = true;
		} catch (Exception e) {
			return Feedback.success("删除用户组运营商频道失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除用户组运营商频道编号为：" + id);
		}
		return Feedback.success("删除用户组运营商频道成功");
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
	@RequestMapping(value = "closeGroupChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeGroupChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("用户组运营商频道停用失败,请选择要停用的用户组运营商频道");
		}
		try {
			groupChannelService.updateChannelStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("用户组运营商频道停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用用户组运营商频道编号为：" + id);
		}
		return Feedback.success("用户组运营商频道停用成功");
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
	@RequestMapping(value = "reverseGroupChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseGroupChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("用户组运营商频道启用失败,请选择要启用的用户组运营商频道");
		}
		try {
			groupChannelService.updateChannelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("用户组运营商频道启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用用户组运营商频道编号为：" + id);
		}
		return Feedback.success("用户组运营商频道启用成功");
	}

	/**
	 * 导入数据
	 * 
	 * 创建人：pananz 创建时间：2016-10-13
	 * 
	 * @param @param msr
	 * @param @param response
	 * @param @param request 返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "importGroupChannel", method = RequestMethod.POST)
	public void importGroupChannel(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("fileGroupChannel");
			msg = groupChannelService.addGroupChannels(file.getInputStream(),
					request);
		} catch (IOException e) {
			e.printStackTrace();
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}
	
	/**
	 * 排序值修改
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-12-22
		 * 
		 * @param @param id
		 * @param @param sequence
		 * @param @param request
		 * @param @param recProgram
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "changeSequence", method = RequestMethod.POST)
	public @ResponseBody
	Feedback changeSequence(@RequestParam("id") String id,
			@RequestParam("sequence") String sequence,
			HttpServletRequest request,
			GroupChannel groupChannel) {
		try {
			groupChannel.setSequence(sequence);
			groupChannel.setId(id);
			groupChannelService.updateSequence(groupChannel);
		} catch (Exception e) {
			return Feedback.fail("修改失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
				"排序值修改编号为：" + id);
		return Feedback.success("排序值修改成功");
	}
}
