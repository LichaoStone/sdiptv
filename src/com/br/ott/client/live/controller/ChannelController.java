package com.br.ott.client.live.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.Global;
import com.br.ott.base.BaseController;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelType;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.ChannelTypeService;
import com.br.ott.common.jackson.TinyTreeBean;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelController.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午7:08:19
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController extends BaseController {

	@Autowired
	private ChannelService channelService;

	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "频道管理";

	@Autowired
	private ChannelTypeService channelTypeService;

	@RequestMapping(value = "findChannel", method = RequestMethod.GET)
	public String findChannel(HttpServletRequest request, Channel channel,
			Model model) {
		channel.setCurrentPage(getPageNo(request));
		channel.setShowCount(getPageRow(request));
		List<Channel> list = channelService.findChannelByPage(channel);
		model.addAttribute("list", list);
		model.addAttribute("channel", channel);

		ChannelType channelType = new ChannelType();
		channelType.setStatus("1");
		channelType.setOrderColumn("operators, sequence");
		channelType.setIsSpecial("2");
		List<ChannelType> types = channelTypeService
				.findChannelTypeByCond(channelType);
		model.addAttribute("types", types);
		list = null;
		types = null;
		
		model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		Channel c = new Channel();
		c.setParentId("0");
		List<Channel> channels = channelService.findChannelByCond(c);
		model.addAttribute("channels", channels);
		channels = null;
		return "live/listChannel";
	}

	/**
	 * 获取二级频道
	 * 
	 * 创建人：pananz 创建时间：2015-8-24
	 * 
	 * @param @param request
	 * @param @return 返回类型：List<Channel>
	 * @exception throws
	 */
	@RequestMapping(value = "changeChannel")
	public @ResponseBody
	List<Channel> changeChannel(HttpServletRequest request) {
		String parentId = request.getParameter("parentId");
		Channel channel = new Channel();
		channel.setParentId(parentId);
		List<Channel> list = channelService.findChannelByCond(channel);
		return list;
	}

	@RequestMapping(value = "changeChannel2")
	public void changeChannel2(HttpServletRequest request,
			HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		Channel channel = new Channel();
		channel.setAreaId(parentId);
		List<Channel> list = channelService.findChannelByCond(channel);
		List<TinyTreeBean> ttbs = channelService.buildTinyTreeBean(list,
				parentId);
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(ttbs);
		list=null;
		ttbs=null;
		writeJson(response, jsonArray.toString());
	}
	
	@RequestMapping(value = "toChannel", method = RequestMethod.GET)
	public String toChannel(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		Channel channel = new Channel();
		if (StringUtil.isNotEmpty(id)) {
			channel = channelService.getChannelById(id);
			model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
			
			if(channel!=null){
				Channel cc = channelService.getChannelById(channel.getParentId());
				if(cc!=null){
					channel.setAreaId(cc.getParentId());
					Channel c = new Channel();
					c.setStatus("0");
					c.setParentId(cc.getParentId());
					List<Channel> csList = channelService.findChannelByCond(c);
					model.addAttribute("csList", csList);
					csList = null;
				}
			}
		}
		model.addAttribute("channel", channel);
		Channel c = new Channel();
		c.setStatus("0");
		c.setParentId("0");
		List<Channel> channels = channelService.findChannelByCond(c);
		model.addAttribute("channels", channels);
		channels = null;
		return "live/channelInfo";
	}

	@RequestMapping(value = "checkChannelByName", method = RequestMethod.GET)
	public void checkChannelByName(HttpServletResponse response,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		if (StringUtil.isEmpty(name) || oldName.equals(name)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = channelService.checkChannelByName(name);
		writeAjaxResult(response, String.valueOf(result));
	}

	@RequestMapping(value = "updateChannel")
	public @ResponseBody
	Feedback updateChannel(HttpServletRequest request, Channel channel) {
		Channel old = channelService.getChannelById(channel.getId());
		try {
			if (!old.getName().equals(channel.getName())) {
				boolean bool = channelService.checkChannelByName(channel
						.getName());
				if (!bool) {
					return Feedback.fail("频道修改失败,频道名称已使用");
				}
			}
			channelService.updateChannel(channel);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改频道失败");
		}
		try {
			channel.setStatus(old.getStatus());
			String diffStr = ObjectUtil.getDiffColumnDescript(old, channel);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改频道成功");
	}

	@RequestMapping(value = "addChannel")
	public @ResponseBody
	Feedback addChannel(HttpServletRequest request, Channel channel) {
		try {
			boolean bool = channelService.checkChannelByName(channel.getName());
			if (!bool) {
				return Feedback.fail("频道保存失败,频道名称已使用");
			}
			channel.setStatus("1");
			channelService.addChannel(channel);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加频道失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增频道名称为：" + channel.getName());
		return Feedback.success("增加频道成功");
	}

	@RequestMapping(value = "channelClose", method = RequestMethod.POST)
	public @ResponseBody
	Feedback channelClose(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道停用失败,请选择要停用的频道");
		}
		try {
			channelService.updateChannelStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用频道编号为：" + id);
		}
		return Feedback.success("频道停用成功");
	}

	/**
	 * 频道启用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "channelReverse", method = RequestMethod.POST)
	public @ResponseBody
	Feedback channelReverse(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道启用失败,请选择要启用的频道");
		}
		try {
			channelService.updateChannelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用频道编号为：" + id);
		}
		return Feedback.success("频道启用成功");
	}

	/**
	 * 频道完善
	 * 
	 * 创建人：pananz 创建时间：2015-6-2 - 下午4:50:34
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "channelPass", method = RequestMethod.POST)
	public @ResponseBody
	Feedback channelPass(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道完善失败,请选择要完善的频道");
		}
		try {
			channelService.updateChannelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道完善失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"完善频道编号为：" + id);
		}
		return Feedback.success("频道完善成功");
	}

	@RequestMapping(value = "delChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delChannel(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道删除失败,请选择要删除的频道");
		}
		try {
			channelService.delChannel(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除频道编号为：" + id);
		}
		return Feedback.success("频道删除成功");
	}
	
	/**
	 * 转到抓取频道页面
	 * 
	 * 创建人：pananz 创建时间：2015-5-11 - 下午4:32:47
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		Channel channel = channelService.getChannelById(id);
		model.addAttribute("channel", channel);
		return "live/updatePrograms";
	}

	/**
	 * 按时间访问抓取频道节目内容
	 * 
	 * 创建人：pananz 创建时间：2015-5-11 - 下午4:32:13
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updatePrograms", method = RequestMethod.POST)
	public @ResponseBody
	Feedback updatePrograms(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String startDate = request.getParameter("startDate");
			String count = request.getParameter("count");
			String type = request.getParameter("type");
			String createFile = request.getParameter("createFile");
			String afterNow = request.getParameter("afterNow");
			Date start = DateUtil.formatDate(startDate);
			int sum = StringUtil.isEmpty(count) ? 1 : Integer.parseInt(count);
			for (int i = 0; i <= sum; i++) {
				Date date = DateUtil.addDay(start, i);
				channelService.updatePrograms(date, id, type, createFile, afterNow);
			}
			return Feedback.success("更新频道节目成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("更新频道节目失败");
		}
	}

}
