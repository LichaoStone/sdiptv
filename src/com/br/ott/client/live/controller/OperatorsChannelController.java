package com.br.ott.client.live.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.OperatorsChannelService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：OperatorsChannelController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-5-28 - 下午2:03:42
 */
@Controller
@RequestMapping(value = "/oc")
public class OperatorsChannelController extends BaseController {

	@Autowired
	private OperatorsChannelService operatorsChannelService;
	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private ChannelService channelService;

	private static final String BUSI_NAME = "运营商频道管理";

	/**
	 * 按分页查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:01:26
	 * 
	 * @param request
	 * @param channel
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperatorsChannel", method = RequestMethod.GET)
	public String findOperatorsChannel(HttpServletRequest request,
			OperatorsChannel channel, Model model) {
		channel.setCurrentPage(getPageNo(request));
		channel.setShowCount(getPageRow(request));
		String areaId = request.getParameter("areaId");
		String operators = request.getParameter("operators");
		String parentId = request.getParameter("parentId");
		channel.setAreaId(areaId);
		channel.setOperators(operators);
		channel.setParentId(parentId);

		List<OperatorsChannel> list = operatorsChannelService
				.findOperatorsChannelByPage(channel);
		model.addAttribute("list", list);
		list = null;

		Channel c = new Channel();
		c.setSecondNode("0");
		c.setOrderColumn("id");
		c.setStatus("1");
		List<Channel> channels = channelService.findChannelByCond(c);
		model.addAttribute("channels", channels);
		channels = null;

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);

		model.addAttribute("channel", channel);
		return "live/listOperChannel";
	}

	/**
	 * 转到运营商频道页面
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:01:05
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toOperatorsChannel", method = RequestMethod.GET)
	public String toChannel(HttpServletRequest request, Model model) {
		OperatorsChannel oc = new OperatorsChannel();
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			oc = operatorsChannelService.getOperatorsChannelById(id);
			if (oc != null) {
				Channel cc = ChannelCache.getChannelById(oc.getChannelId());
				if (cc != null) {
					List<Channel> csList = ChannelCache
							.findChannelByParentId(cc.getParentId());
					model.addAttribute("csList", csList);
					csList = null;
					Channel cc2 = ChannelCache.getChannelById(cc.getParentId());
					if (cc2 != null) {
						if (!"0".equals(cc2.getParentId())) {
							Channel cc3 = ChannelCache.getChannelById(cc2.getParentId());
							if (cc3 != null) {
								oc.setCareaId(cc3.getId());
							}
						}else{
							oc.setCareaId(cc2.getId());
						}
					}
				}
			}
		}
		model.addAttribute("oc", oc);

		Channel c = new Channel();
		c.setParentId("0");
		c.setStatus("0");
		List<Channel> channels = channelService.findChannelByCond(c);
		model.addAttribute("channels", channels);
		channels = null;

		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		return "live/operChannelInfo";
	}

	/**
	 * 增加运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:00:31
	 * 
	 * @param request
	 * @param operators
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addOperatorsChannel")
	public @ResponseBody
	Feedback addOperatorsChannel(HttpServletRequest request,
			OperatorsChannel operators) {
		try {
			boolean bool = operatorsChannelService.checkOperatorsChannel(
					operators.getOperators(), operators.getChannelId(),
					operators.getPlayChannelId());
			if (!bool) {
				return Feedback.fail("运营商频道保存失败,运营商频道已使用");
			}
			operatorsChannelService.addOperatorsChannel(operators);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加运营商频道失败");
		}
		operaLogService.addOperaLog(
				OPERA_TYPE_ADD,
				request,
				BUSI_NAME,
				"新增运营商名称为：" + operators.getOperatorsName() + "频道:"
						+ operators.getChannelName());
		return Feedback.success("增加运营商频道成功");
	}

	/**
	 * 修改运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:38:54
	 * 
	 * @param request
	 * @param oc
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateOperatorsChannel")
	public @ResponseBody
	Feedback updateOperatorsChannel(HttpServletRequest request,
			OperatorsChannel oc) {
		OperatorsChannel old = operatorsChannelService
				.getOperatorsChannelById(oc.getId());
		try {
			if (!old.getOperators().equals(oc.getOperators())
					&& !old.getChannelId().equals(oc.getChannelId())) {
				boolean bool = operatorsChannelService.checkOperatorsChannel(
						oc.getOperators(), oc.getChannelId(),
						oc.getPlayChannelId());
				if (!bool) {
					return Feedback.fail("运营商频道已使用,请重新输入");
				}
			}
			operatorsChannelService.updateOperatorsChannel(oc);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改运营商频道失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, oc);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改运营商频道");
	}

	/**
	 * 运营商频道停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:46
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeOperatorsChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeOperatorsChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商频道停用失败,请选择要停用的运营商频道");
		}
		try {
			operatorsChannelService.updateOperatorsChannelStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用运营商频道编号为：" + id);
		}
		return Feedback.success("运营商频道停用成功");
	}

	/**
	 * 运营商频道启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseOperatorsChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseOperatorsChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商频道启用失败,请选择要启用的运营商频道");
		}
		try {
			operatorsChannelService.updateOperatorsChannelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商频道启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用运营商频道编号为：" + id);
		}
		return Feedback.success("运营商频道启用成功");
	}

	/**
	 * 删除的运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:00:19
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delOperatorsChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperatorsChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商频道删除失败,请选择要删除的运营商频道");
		}
		try {
			operatorsChannelService.delOperatorsChannelById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商频道编号为：" + id);
		}
		return Feedback.success("运营商频道删除成功");
	}

	/**
	 * 批量删除运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-6-9 - 下午2:07:49
	 * 
	 * @param ids
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delOperChannelList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperChannelList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("运营商频道删除失败,请选择要删除的运营商频道");
		}
		String[] arr = ids.split(",");
		try {
			operatorsChannelService.delOperatorsChannelList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商频道编号为：" + ids);
		}
		return Feedback.success("运营商频道删除成功");
	}

	/**
	 * 批量导入运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-6-9 - 下午2:08:05
	 * 
	 * @param msr
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "importOperatorsChannel", method = RequestMethod.POST)
	public void importOperatorsChannel(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("fileOperChannel");
			msg = operatorsChannelService.addOperatorsChannels(
					file.getInputStream(), request);
		} catch (IOException e) {
			e.printStackTrace();
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}
}
