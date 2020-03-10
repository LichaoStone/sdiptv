package com.br.ott.client.live.controller;

import java.util.ArrayList;
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
import com.br.ott.client.live.entity.RecProgram;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.RecProgramService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.Constants.DicType;

/* 
 *  
 *  文件名：RecProgramController.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午4:13:09
 */
@Controller
@RequestMapping(value = "/rec")
public class RecProgramController extends BaseController {

	@Autowired
	private RecProgramService recProgramService;

	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private ChannelService channelService;

	private static final String BUSI_NAME = "节目推荐管理";

	/**
	 * 分页查询节目推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:30:55
	 * 
	 * @param request
	 * @param RecProgram
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findRecProgram", method = RequestMethod.GET)
	public String findRecProgram(HttpServletRequest request,
			RecProgram recProgram, Model model) {
		recProgram.setCurrentPage(getPageNo(request));
		recProgram.setShowCount(getPageRow(request));
		if (StringUtil.isEmpty(recProgram.getOrderColumn())) {
			recProgram.setOrderColumn("rc.sequence");
		}
		List<RecProgram> list = recProgramService.findRecProgramByPage(recProgram);
		model.addAttribute("list", list);
		model.addAttribute("recProgram", recProgram);
		list = null;
		List<Dictionary> dictlist = DictCache.getDictList(DicType.TJLX);
		model.addAttribute("dictlist", dictlist);
		List<Channel> channelList = new ArrayList<Channel>();
		for(Channel channel : ChannelCache.cList){
			if("1".equals(channel.getStatus()) && !"0".equals(channel.getParentId()))
				channelList.add(channel);
		}
		model.addAttribute("channelList", channelList);
		return "live/listRecProgram";
	}

	/**
	 * 转到节目推荐页面 创建人：pananz 创建时间：2014-8-20 - 下午4:31:19
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toRecProgram", method = RequestMethod.GET)
	public String toChannelType(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		RecProgram recProgram = new RecProgram();
		if (StringUtil.isNotEmpty(id)) {
			recProgram = recProgramService.getRecProgramById(id);
			if(recProgram!=null){
				Channel cc = ChannelCache.getChannelById(recProgram.getChannelId());
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
								recProgram.setAreaId(cc3.getId());
							}
						}else{
							recProgram.setAreaId(cc2.getId());
						}
					}
				}
			}
		}
		model.addAttribute("recProgram", recProgram);
		Channel channel = new Channel();
		channel.setParentId("0");
		channel.setStatus("0");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		channels = null;
		List<Dictionary> dictlist = DictCache.getDictList(DicType.TJLX);
		model.addAttribute("dictlist", dictlist);
		dictlist=null;
		
		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		return "live/recProgramInfo";
	}

	@RequestMapping(value = "changeChannel")
	public @ResponseBody
	List<Channel> changeChannel(HttpServletRequest request) {
		String parentId = request.getParameter("parentId");
		Channel channel = new Channel();
		channel.setAreaId(parentId);
		List<Channel> list = channelService
				.findChannelByCond(channel);
		return list;
	}
	
	/**
	 * 增加节目推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:31:31
	 * 
	 * @param request
	 * @param RecProgram
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addRecProgram")
	public @ResponseBody
	Feedback addRecProgram(HttpServletRequest request, RecProgram recProgram) {
		try {
			int maxSequence = this.recProgramService.getMaxSequence(recProgram.getType(), recProgram.getChannelId());
			recProgram.setSequence(String.valueOf(maxSequence));
			recProgramService.addRecProgram(recProgram);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加节目推荐失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增节目推荐编号为：" + recProgram.getId());
		return Feedback.success("增加节目推荐成功");
	}

	/**
	 * 删除节目推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:31:56
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delRecProgram")
	public @ResponseBody
	Feedback delRecProgram(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recProgramService.deleteRecProgramById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除节目推荐失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
				"删除节目推荐编号为：" + id);
		return Feedback.success("删除节目推荐成功");
	}

	/**
	 * 修改节目推荐内容 创建人：pananz 创建时间：2014-8-20 - 下午4:32:08
	 * 
	 * @param request
	 * @param RecProgram
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateRecProgram")
	public @ResponseBody
	Feedback updateRecProgram(HttpServletRequest request, RecProgram RecProgram) {
		boolean flag = false;
		RecProgram old = recProgramService
				.getRecProgramById(RecProgram.getId());
		try {
			recProgramService.updateRecProgram(RecProgram);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改节目推荐失败");
		}
		if (flag) {
			try {
				String diffStr = ObjectUtil.getDiffColumnDescript(old,
						RecProgram);
				if (null != diffStr) {
					// 写入系统操作日志
					operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
							BUSI_NAME, diffStr);
				}
			} catch (Exception e) {
			}
		}
		return Feedback.success("修改节目推荐成功");
	}

	/**
	 * 节目推荐停用
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:26:22
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeRecProgram")
	public @ResponseBody
	Feedback closeRecProgram(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recProgramService.updateRecProgramStatus("0", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目推荐停用失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
				"节目推荐停用编号为：" + id);
		return Feedback.success("节目推荐停用成功");
	}

	/**
	 * 节目推荐启用
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:28:32
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseRecProgram")
	public @ResponseBody
	Feedback reverseRecProgram(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			recProgramService.updateRecProgramStatus("1", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目推荐启用失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
				"节目推荐启用编号为：" + id);
		return Feedback.success("节目推荐启用成功");
	}
	
	/**
	 * 改变推荐位顺序
	 * @author  lizhenghg  2017-03-17
	 * @param request
	 * @return Feedback
	 */
	@RequestMapping(value = "changeDNSequence", method = RequestMethod.POST)
	@ResponseBody
	public Feedback changeDNSequence(HttpServletRequest request) {
		String id = request.getParameter("id");
		String queue = request.getParameter("queue");
		String oldQueue = request.getParameter("oldQueue");
		boolean flag = false;
		try {
			RecProgram rec = recProgramService.getRecProgramById(id);
			if (rec != null) {
				int maxSort = recProgramService.getMaxSequence(rec.getType(), rec.getChannelId());
				if (Integer.parseInt(queue) > (maxSort - 1)) {
					return Feedback.fail("最大值不能超过" + (maxSort - 1) + ",修改排序值失败");
				}
			}
			recProgramService.changeDNSequence(rec, oldQueue, queue);
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
