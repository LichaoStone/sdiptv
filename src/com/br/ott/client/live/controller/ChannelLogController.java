package com.br.ott.client.live.controller;

import java.util.Arrays;
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
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelLog;
import com.br.ott.client.live.entity.UserFollow;
import com.br.ott.client.live.entity.UserPrograms;
import com.br.ott.client.live.service.ChannelLogService;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.UserFollowService;
import com.br.ott.client.live.service.UserProgramsService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelLogController.java
 *  创建人：pananz
 *  创建时间：2014-9-23 - 下午2:01:59
 */
@Controller
@RequestMapping(value = "/hot")
public class ChannelLogController extends BaseController {

	@Autowired
	private ChannelLogService ChannelLogService;

	@Autowired
	private UserFollowService userFollowService;

	@Autowired
	private UserProgramsService userProgramsService;
	
	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private ChannelService channelService;
	
	@RequestMapping(value = "findChannelLog", method = RequestMethod.GET)
	public String findChannelLog(HttpServletRequest request,
			ChannelLog channelLog, Model model) {
		channelLog.setCurrentPage(getPageNo(request));
		channelLog.setShowCount(getPageRow(request));
		List<ChannelLog> list = ChannelLogService
				.findChannelLogByPage(channelLog);
		model.addAttribute("list", list);
		model.addAttribute("channelLog", channelLog);
		list = null;
		return "live/listHot";
	}

	@RequestMapping(value = "toCNumber", method = RequestMethod.GET)
	public String toCNumber(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String virtualNumber = request.getParameter("virtualNumber");
		model.addAttribute("id", id);
		model.addAttribute("virtualNumber", virtualNumber);
		return "live/cNumber";
	}

	@RequestMapping(value = "updateChannelNumber")
	public @ResponseBody
	Feedback updateChannelNumber(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String virtualNumber = request.getParameter("virtualNumber");
			ChannelLogService.updateVirtualNumber(virtualNumber, id);
			return Feedback.success("修改频道热门干预值成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改频道热门干预值失败");
		}
	}

	/**
	 * 用户收藏频道信息查询
	 * 
	 * 创建人：pananz 创建时间：2016-11-1
	 * 
	 * @param @param request
	 * @param @param userFollow
	 * @param @param model
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findFollowChannel", method = RequestMethod.GET)
	public String findFollowChannel(HttpServletRequest request,
			UserFollow userFollow, Model model) {
		userFollow.setCurrentPage(getPageNo(request));
		userFollow.setShowCount(getPageRow(request));
		userFollow.setType("1");
		List<UserFollow> list = userFollowService
				.findUserFollowByPage(userFollow);
		model.addAttribute("list", list);
		
		list = null;
		Channel channel = new Channel();
		channel.setParentId("0");
		channel.setStatus("0");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		
		if(StringUtil.isNotEmpty(userFollow.getChannelId())){
			Channel cc = ChannelCache.getChannelById(userFollow.getChannelId());
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
							userFollow.setAreaId(cc3.getId());
						}
					}else{
						userFollow.setAreaId(cc2.getId());
					}
				}
			}
		}
		model.addAttribute("userFollow", userFollow);
		return "live/listFollowChannel";
	}

	/**
	 * 最近观看频道信息查询
	 * 
	 * 创建人：pananz 创建时间：2016-11-1
	 * 
	 * @param @param request
	 * @param @param userFollow
	 * @param @param model
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findWatchChannel", method = RequestMethod.GET)
	public String findWatchChannel(HttpServletRequest request,
			UserFollow userFollow, Model model) {
		userFollow.setCurrentPage(getPageNo(request));
		userFollow.setShowCount(getPageRow(request));
		userFollow.setType("2");
		List<UserFollow> list = userFollowService
				.findUserFollowByPage(userFollow);
		model.addAttribute("list", list);
		list = null;
		Channel channel = new Channel();
		channel.setParentId("0");
		channel.setStatus("0");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		
		if(StringUtil.isNotEmpty(userFollow.getChannelId())){
			Channel cc = ChannelCache.getChannelById(userFollow.getChannelId());
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
							userFollow.setAreaId(cc3.getId());
						}
					}else{
						userFollow.setAreaId(cc2.getId());
					}
				}
			}
		}
		model.addAttribute("userFollow", userFollow);
		return "live/listWatchChannel";
	}

	/**
	 * 用户预定节目信息
	 * 
	 * 创建人：pananz 创建时间：2016-11-1
	 * 
	 * @param @param request
	 * @param @param up
	 * @param @param model
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findUserPrograms", method = RequestMethod.GET)
	public String findUserPrograms(HttpServletRequest request, UserPrograms up,
			Model model) {
		up.setCurrentPage(getPageNo(request));
		up.setShowCount(getPageRow(request));
		List<UserPrograms> list = userProgramsService
				.findUserProgramsByPage(up);
		model.addAttribute("list", list);
		model.addAttribute("up", up);
		list = null;
		return "live/listUserPrograms";
	}
	
	/**
	 * 批量删除用户收藏频道
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delFollowList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFollowList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("用户收藏频道删除失败,请选择要删除的用户收藏频道");
		}
		try {
			String[] arr = ids.split(",");
			userFollowService.delFollowList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("用户收藏频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, "用户收藏频道管理",
					"用户收藏频道编号为：" + ids);
		}
		return Feedback.success("用户收藏频道删除成功");
	}
	
	/**
	 * 批量删除最近收看频道
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delWatchChannelList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delWatchChannelList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("最近收看频道删除失败,请选择要删除的最近收看频道");
		}
		try {
			String[] arr = ids.split(",");
			userFollowService.delFollowList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("最近收看频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, "最近收看频道管理",
					"最近收看频道编号为：" + ids);
		}
		return Feedback.success("最近收看频道删除成功");
	}
	
	/**
	 * 删除最近收看频道
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delWatchChannel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delWatchChannel(@RequestParam("id") String id, HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("删除最近收看频道失败,请选择要删除的最近收看频道");
		}
		try {
			userFollowService.delWatchChannel(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除最近收看频道失败");
		}
		return Feedback.success("删除最近收看频道成功");
	}
	/**
	 * 删除用户收藏频道
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delFollow", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFollow(@RequestParam("id") String id, HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("删除用户收藏频道失败,请选择要删除的用户收藏频道");
		}
		try {
			userFollowService.delWatchChannel(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除用户收藏频道失败");
		}
		return Feedback.success("删除用户收藏频道成功");
	}
	
	/**
	 * 删除热门频道
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param id
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delHot", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delHot(@RequestParam("id") String id, HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("删除热门频道失败,请选择要删除的热门频道频道");
		}
		try {
			ChannelLogService.delHot(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除热门频道失败");
		}
		return Feedback.success("删除热门频道成功");
	}
	
	@RequestMapping(value = "delHotList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delHotList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("热门频道删除失败,请选择要删除的热门频道");
		}
		try {
			String[] arr = ids.split(",");
			ChannelLogService.delHotList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("热门频道删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, "热门频道管理",
					"热门频道编号为：" + ids);
		}
		return Feedback.success("热门频道删除成功");
	}
}
