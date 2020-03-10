package com.br.ott.client.equip.controller;

import java.io.IOException;
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
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.service.AreaService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：AreaController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午4:38:43
 */
@Controller
@RequestMapping(value = "/equip")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService;
	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "区域管理";

	/**
	 * 按分页查询区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param area
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findArea", method = RequestMethod.GET)
	public String findArea(HttpServletRequest request, Area area, Model model) {
		area.setCurrentPage(getPageNo(request));
		area.setShowCount(getPageRow(request));
		List<Area> list = areaService.findAreaByPage(area);
		model.addAttribute("list", list);
		model.addAttribute("area", area);
		list = null;
		
		Area a = new Area();
		a.setParentId("0");
		a.setStatus("1");
		List<Area> areas = areaService.findAreaByCond(a);
		model.addAttribute("areas", areas);
		areas = null;
		return "equip/listArea";
	}

	@RequestMapping(value = "changeArea")
	public @ResponseBody
	List<Area> changeArea(HttpServletRequest request) {
		Area area = new Area();
		area.setStatus("1");
		area.setParentId(request.getParameter("parentId"));
		List<Area> areas = areaService.findAreaByCond(area);
		return areas;
	}

	/**
	 * 转到区域页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:37
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toArea", method = RequestMethod.GET)
	public String toArea(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		Area area = new Area();
		if (StringUtil.isNotEmpty(id)) {
			area = areaService.getAreaById(id);
		}
		Area a = new Area();
		a.setStatus("1");
		a.setParentId("0");
		List<Area> areas = areaService.findAreaByCond(a);
		model.addAttribute("area", area);
		model.addAttribute("areas", areas);
		areas = null;
		return "equip/areaInfo";
	}

	/**
	 * 增加区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:18
	 * 
	 * @param request
	 * @param area
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addArea")
	public @ResponseBody
	Feedback addArea(HttpServletRequest request, Area area) {
		try {
			areaService.addArea(area);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加区域失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增区域名称为：" + area.getName());
		return Feedback.success("增加区域成功");
	}

	/**
	 * 修改区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:09
	 * 
	 * @param request
	 * @param area
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateArea")
	public @ResponseBody
	Feedback updateArea(HttpServletRequest request, Area area) {
		Area old = areaService.getAreaById(area.getId());
		try {
			areaService.updateArea(area);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改区域失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, area);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改区域成功");
	}

	/**
	 * 区域停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:46
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeArea", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeArea(@RequestParam("id") String id, HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("区域停用失败,请选择要停用的区域");
		}
		try {
			areaService.updateAreaStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("区域停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用区域编号为：" + id);
		}
		return Feedback.success("区域停用成功");
	}

	/**
	 * 区域启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseArea", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseArea(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("区域启用失败,请选择要启用的区域");
		}
		try {
			areaService.updateAreaStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("区域启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用区域编号为：" + id);
		}
		return Feedback.success("区域启用成功");
	}

	/**
	 * 
	 * 
	 * 创建人：pananz 创建时间：2015-5-26 - 下午6:01:02
	 * 
	 * @param msr
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "importArea", method = RequestMethod.POST)
	public void importArea(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("fileArea");
			msg = areaService.addAreaByExcel(file.getInputStream(), request);
		} catch (IOException e) {
			e.printStackTrace();
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}
}
