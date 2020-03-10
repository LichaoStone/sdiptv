package com.br.ott.client.live.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.Global;
import com.br.ott.base.BaseController;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.RecChannel;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.RecChannelService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：RecChannelController.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午4:13:09
 */
@Controller
@RequestMapping(value = "/rec")
public class RecChannelController extends BaseController {

	@Autowired
	private RecChannelService recChannelService;

	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private ChannelService channelService;

	private static final String BUSI_NAME = "频道推荐管理";

	/**
	 * 分页查询频道推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:30:55
	 * 
	 * @param request
	 * @param recChannel
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findRecChannel", method = RequestMethod.GET)
	public String findRecChannel(HttpServletRequest request,
			RecChannel recChannel, Model model) {
		recChannel.setCurrentPage(getPageNo(request));
		recChannel.setShowCount(getPageRow(request));
		if(recChannel.getOrderColumn() == null) recChannel.setOrderColumn("rc.type, rc.sequence");
		List<RecChannel> list = recChannelService
				.findRecChannelByPage(recChannel);
		model.addAttribute("list", list);
		model.addAttribute("recChannel", recChannel);
		list = null;
		return "live/listRecChannel";
	}

	/**
	 * 转到频道推荐页面 创建人：pananz 创建时间：2014-8-20 - 下午4:31:19
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toRecChannel", method = RequestMethod.GET)
	public String toChannelType(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		RecChannel recChannel = new RecChannel();
		if (StringUtil.isNotEmpty(id)) {
			recChannel = recChannelService.getRecChannelById(id);
			if(recChannel!=null){
				Channel cc = ChannelCache.getChannelById(recChannel.getChannelId());
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
								recChannel.setAreaId(cc3.getId());
							}
						}else{
							recChannel.setAreaId(cc2.getId());
						}
					}
				}
			}
		}
		Channel channel = new Channel();
		channel.setStatus("0");
		channel.setParentId("0");
		List<Channel> list = channelService.findChannelByCond(channel);
		
		model.addAttribute("recChannel", recChannel);
		model.addAttribute("list", list);
		model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		List<Dictionary> dictlist = DictCache.getDictList(DicType.TJLX);
		model.addAttribute("dictlist", dictlist);
		
		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		
		list = null;
		dictlist=null;
		return "live/recChannelInfo";
	}

	/**
	 * 增加频道推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:31:31
	 * 
	 * @param request
	 * @param recChannel
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addRecChannel")
	public @ResponseBody
	Feedback addRecChannel(HttpServletRequest request, RecChannel recChannel) {
		try {
			int maxSequence = recChannelService.getMaxSequence(recChannel.getType());
			recChannel.setSequence(String.valueOf(maxSequence));
			recChannelService.addRecChannel(recChannel);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加频道推荐失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增频道推送编号为：" + recChannel.getId());
		return Feedback.success("增加频道推荐成功");
	}

	/**
	 * 删除频道推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:31:56
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delRecChannel")
	public @ResponseBody
	Feedback delRecChannel(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recChannelService.deleteRecChannelById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除频道推荐失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
				"删除频道推荐编号为：" + id);
		return Feedback.success("删除频道推荐成功");
	}

	/**
	 * 修改频道推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:32:08
	 * 
	 * @param request
	 * @param recChannel
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateRecChannel")
	public @ResponseBody
	Feedback updateRecChannel(HttpServletRequest request, RecChannel recChannel) {
		boolean flag = false;
		RecChannel old = recChannelService
				.getRecChannelById(recChannel.getId());
		try {
			recChannelService.updateRecChannel(recChannel);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改频道推荐失败");
		}
		if (flag) {
			try {
				String diffStr = ObjectUtil.getDiffColumnDescript(old,
						recChannel);
				if (null != diffStr) {
					// 写入系统操作日志
					operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
							BUSI_NAME, diffStr);
				}
			} catch (Exception e) {
			}
		}
		return Feedback.success("修改频道推荐成功");
	}

	/**
	 * 频道推荐停用
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:26:22
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeRecChannel")
	public @ResponseBody
	Feedback closeRecChannel(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recChannelService.updateRecChannel("0", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道推荐停用失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
				"频道推荐停用编号为：" + id);
		return Feedback.success("频道推荐停用成功");
	}

	/**
	 * 频道推荐启用
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:28:32
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseRecChannel")
	public @ResponseBody
	Feedback reverseRecChannel(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recChannelService.updateRecChannel("1", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道推荐启用失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
				"频道推荐启用编号为：" + id);
		return Feedback.success("频道推荐启用成功");
	}
	
	/**
	 * 改变推荐位顺序
	 * @author  lizhenghg  2017-03-17
	 * @param request
	 * @return Feedback
	 */
	@RequestMapping(value = "changeDNSequences", method = RequestMethod.POST)
	@ResponseBody
	public Feedback changeDNSequence(HttpServletRequest request) {
		String id = request.getParameter("id");
		String queue = request.getParameter("queue");
		String oldQueue = request.getParameter("oldQueue");
		boolean flag = false;
		try {
			RecChannel rec = recChannelService.getRecChannelById(id);
			if (rec != null) {
				int maxSort = recChannelService.getMaxSequence(rec.getType());
				if (Integer.parseInt(queue) > (maxSort - 1)) {
					return Feedback.fail("最大值不能超过" + (maxSort - 1) + ",修改排序值失败");
				}
			}
			recChannelService.changeDNSequence(rec, oldQueue, queue);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改排序值失败");
		}
		if (flag) {
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME, "排序值修改编号为：" + id);
		}
		return Feedback.success("排序值修改成功");
	}
}
