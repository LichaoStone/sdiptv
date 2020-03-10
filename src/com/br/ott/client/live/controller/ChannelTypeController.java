package com.br.ott.client.live.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelType;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.ChannelTypeService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelTypeController.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午7:40:41
 */
@Controller
@RequestMapping(value = "/type")
public class ChannelTypeController extends BaseController {

	@Autowired
	private ChannelTypeService channelTypeService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "频道类型管理";

	@RequestMapping(value = "findChannelType", method = RequestMethod.GET)
	public String findChannelType(HttpServletRequest request, ChannelType type,
			Model model) {
		type.setCurrentPage(getPageNo(request));
		type.setShowCount(getPageRow(request));
		type.setOrderColumn("c.operators,c.sequence");
		List<ChannelType> list = channelTypeService.findChannelTypeByPage(type);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		list = null;
		return "live/listType";
	}

	@RequestMapping(value = "toChannelType", method = RequestMethod.GET)
	public String toChannelType(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		ChannelType type = new ChannelType();
		if (StringUtil.isNotEmpty(id)) {
			type = channelTypeService.getChannelTypeById(id);
		}
		model.addAttribute("type", type);
		List<CityOperators> olist = OperatorsCache.opList;
		model.addAttribute("olist", olist);
		return "live/typeInfo";
	}

	@RequestMapping(value = "addChannelType")
	public @ResponseBody
	Feedback addChannelType(HttpServletRequest request, ChannelType type) {
		boolean flag = false;
		try {
			boolean bool = channelTypeService.findChannelTypeByTypeAndName(
					type.getOperators(), type.getName());
			if (!bool) {
				return Feedback.fail("频道类型保存失败,频道类型名称已使用");
			}
			channelTypeService.addChannelType(type);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加频道类型失败");
		}
		if (flag) {
			operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
					"新增频道编号为：" + type.getName());
		}
		return Feedback.success("增加频道类型成功");
	}

	@RequestMapping(value = "updateChannelType")
	public @ResponseBody
	Feedback updateChannelType(HttpServletRequest request, ChannelType type) {
		boolean flag = false;
		ChannelType old = channelTypeService.getChannelTypeById(type.getId());
		try {
			if (!old.getName().equals(type.getName())) {
				boolean bool = channelTypeService.findChannelTypeByTypeAndName(
						type.getOperators(), type.getName());
				if (!bool) {
					return Feedback.fail("频道类型修改失败,频道类型名称已使用");
				}
			}
			channelTypeService.updateChannelType(type);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改频道类型失败");
		}
		if (flag) {
			try {
				type.setStatus(old.getStatus());
				String diffStr = ObjectUtil.getDiffColumnDescript(old, type);
				if (null != diffStr) {
					// 写入系统操作日志
					operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
							BUSI_NAME, diffStr);
				}
			} catch (Exception e) {
			}
		}
		return Feedback.success("修改频道类型成功");
	}

	@RequestMapping(value = "checkName", method = RequestMethod.GET)
	public void checkPartnerId(HttpServletResponse response,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		String type = request.getParameter("type");
		if (StringUtil.isEmpty(name) || oldName.equals(name)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = channelTypeService.findChannelTypeByTypeAndName(type,
				name);
		writeAjaxResult(response, String.valueOf(result));
	}

	@RequestMapping(value = "channelTypeClose", method = RequestMethod.POST)
	public @ResponseBody
	Feedback channelTypeClose(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道类型停用失败,请选择要停用的频道类型");
		}
		try {
			channelTypeService.updateChannelTypeStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道类型停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用频道类型编号为：" + id);
		}
		return Feedback.success("频道类型停用成功");
	}

	/**
	 * 类型类型启用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "channelTypeReverse", method = RequestMethod.POST)
	public @ResponseBody
	Feedback channelTypeReverse(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("频道类型启用失败,请选择要启用的频道类型");
		}
		try {
			channelTypeService.updateChannelTypeStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("频道类型启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用频道类型编号为：" + id);
		}
		return Feedback.success("频道类型启用成功");
	}
	
	@RequestMapping(value = "toCTypeList", method = RequestMethod.GET)
	public String toCTypeList(HttpServletRequest request, Model model) {
		Channel c = new Channel();
		c.setStatus("1");
		c.setSecondNode("yes");
		List<Channel> channels = channelService.findChannelByCond(c);
		String typeId = request.getParameter("typeId");
		String typeName = request.getParameter("typeName");

		CType cType = new CType();
		cType.setTypeId(typeId);
		cType.setTypeName(typeName);
		model.addAttribute("cType", cType);

		List<CType> list2 = channelTypeService.findCTypeByTypeId(typeId);
		List<String> cList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(list2)) {
			for (CType ct : list2) {
				cList.add(ct.getChannelId());
			}
		}
		for(Channel channel: channels){
			if(cList.contains(channel.getId())){
				channel.setCheck(true);
			}else{
				channel.setCheck(false);
			}
		}
		model.addAttribute("channels", channels);
		model.addAttribute("oldType",
				org.apache.commons.lang.StringUtils.join(cList, ","));
		list2 = null;
		cList = null;
		channels = null;
		return "live/cTypeListInfo";
	}
	
	@RequestMapping(value = "addCTypeList")
	public @ResponseBody
	Feedback addCTypeList(HttpServletRequest request, CType type) {
		boolean flag = false;
		try {
			channelTypeService.addCTypeList(type,request.getParameter("oldType"));
			flag= true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加频道类型的频道失败");
		}
		if (flag) {
			operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
					"新增频道类型的频道为：" + type.getChannelId());
		}
		return Feedback.success("增加频道类型的频道成功");
	}
}
