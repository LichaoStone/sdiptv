package com.br.ott.client.equip.controller;

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

import com.br.ott.base.BaseController;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.entity.Operators;
import com.br.ott.client.equip.service.AreaService;
import com.br.ott.client.equip.service.OperatorsService;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.service.CityOperatorsService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：OperatorsController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午4:39:02
 */
@Controller
@RequestMapping(value = "/equip")
public class OperatorsController extends BaseController {

	private static final String BUSI_NAME = "红外红外方案管理";
	@Autowired
	private OperatorsService operatorsService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OperaLogService operaLogService;
	@Autowired
	private CityOperatorsService cityOperatorsService;

	/**
	 * 按分页查询红外方案
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param Operators
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperators", method = RequestMethod.GET)
	public String findOperators(HttpServletRequest request,
			Operators operators, Model model) {
		operators.setCurrentPage(getPageNo(request));
		operators.setShowCount(getPageRow(request));
		operators.setAreaId(request.getParameter("areaId"));
		operators.setParentId(request.getParameter("parentId"));
		List<Operators> list = operatorsService.findOperatorsByPage(operators);
		model.addAttribute("list", list);
		list = null;

		Area area = new Area();
		area.setParentId("0");
		area.setStatus("1");
		List<Area> areas = areaService.findAreaByCond(area);
		model.addAttribute("areas", areas);
		areas = null;

		if (StringUtil.isNotEmpty(operators.getAreaId())) {
			if (StringUtil.isEmpty(operators.getParentId())) {
				Area area2 = areaService.getAreaById(operators.getAreaId());
				if (area2 != null) {
					operators.setParentId(area2.getParentId());
				}
			}
		}
		if (StringUtil.isNotEmpty(operators.getParentId())) {
			Area a = new Area();
			a.setStatus("1");
			a.setParentId(operators.getParentId());
			List<Area> alist = areaService.findAreaByCond(a);
			model.addAttribute("alist", alist);
			alist = null;
		}
		model.addAttribute("operators", operators);
		return "equip/listOperators";
	}

	/**
	 * 转到红外方案页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:37
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toOperators", method = RequestMethod.GET)
	public String toOperators(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String areaId = request.getParameter("areaId");
		Operators operators = new Operators();
		operators.setAreaId(areaId);
		if (StringUtil.isNotEmpty(id)) {
			operators = operatorsService.getOperatorsById(id);
			if (operators != null) {
				if (StringUtil.isNotEmpty(operators.getAreaId())) {
					Area area2 = areaService.getAreaById(operators.getAreaId());
					if (area2 != null) {
						operators.setParentId(area2.getParentId());
						Area a = new Area();
						a.setStatus("1");
						a.setParentId(area2.getParentId());
						List<Area> alist = areaService.findAreaByCond(a);
						model.addAttribute("alist", alist);
						alist = null;
					}
				}
			}
		}
		model.addAttribute("operators", operators);

		Area area = new Area();
		area.setStatus("1");
		area.setParentId("0");
		List<Area> areas = areaService.findAreaByCond(area);
		model.addAttribute("areas", areas);
		areas = null;
		CityOperators cityOperators = new CityOperators();
		cityOperators.setStatus("1");
		if (operators != null) {
			cityOperators.setAreaId(operators.getAreaId());
		}
		List<CityOperators> coList = cityOperatorsService
				.findCityOperatorsByCond(cityOperators);
		model.addAttribute("coList", coList);
		coList=null;
		return "equip/operatorsInfo";
	}

	@RequestMapping(value = "changeOperators")
	public @ResponseBody
	List<Operators> changeOperators(HttpServletRequest request) {
		String areaId = request.getParameter("areaId");
		Operators operators = new Operators();
		operators.setStatus("1");
		operators.setAreaId(areaId);
		List<Operators> list = operatorsService.findOperatorsByCond(operators);
		return list;
	}

	/**
	 * 校验红外方案信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:26
	 * 
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "checkOperatorsByCode", method = RequestMethod.GET)
	public void checkOperatorsByCode(HttpServletResponse response,
			HttpServletRequest request) {
		String code = request.getParameter("code");
		String oldCode = request.getParameter("oldCode");
		if (StringUtil.isEmpty(code) || oldCode.equals(code)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = operatorsService.checkOperatorsByCode(code);
		writeAjaxResult(response, String.valueOf(result));
	}

	/**
	 * 增加红外方案
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:18
	 * 
	 * @param request
	 * @param Operators
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addOperators")
	public @ResponseBody
	Feedback addOperators(HttpServletRequest request, Operators operators) {
		try {
			boolean bool = operatorsService.checkOperatorsByCode(operators
					.getCode());
			if (!bool) {
				return Feedback.fail("红外方案保存失败,红外方案编码已使用");
			}
			operators.setStatus("1");
			operatorsService.addOperators(operators);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加红外方案失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增红外方案名称为：" + operators.getName());
		return Feedback.success("增加红外方案成功");
	}

	/**
	 * 修改红外方案
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:09
	 * 
	 * @param request
	 * @param Operators
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateOperators")
	public @ResponseBody
	Feedback updateOperators(HttpServletRequest request, Operators operators) {
		Operators old = operatorsService.getOperatorsById(operators.getId());
		try {
			if (!old.getCode().equals(operators.getCode())) {
				boolean bool = operatorsService.checkOperatorsByCode(operators
						.getCode());
				if (!bool) {
					return Feedback.fail("红外方案修改失败,红外方案编码已使用");
				}
			}
			operatorsService.updateOperators(operators);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改红外方案失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, operators);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改红外方案成功");
	}

	/**
	 * 红外方案停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:46
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外方案停用失败,请选择要停用的红外方案");
		}
		try {
			operatorsService.updateOperatorsStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外方案停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用红外方案编号为：" + id);
		}
		return Feedback.success("红外方案停用成功");
	}

	/**
	 * 红外方案启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外方案启用失败,请选择要启用的红外方案");
		}
		try {
			operatorsService.updateOperatorsStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外方案启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用红外方案编号为：" + id);
		}
		return Feedback.success("红外方案启用成功");
	}

	@RequestMapping(value = "passOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback passOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外方案完善失败,请选择要完善的红外方案");
		}
		try {
			operatorsService.updateOperatorsStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外方案完善失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"完善红外方案编号为：" + id);
		}
		return Feedback.success("红外方案完善成功");
	}

	/**
	 * 按ID删除红外方案
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:58:56
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外方案删除失败,请选择要删除的红外方案");
		}
		try {
			operatorsService.delOperators(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外方案删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除红外方案编号为：" + id);
		}
		return Feedback.success("红外方案删除成功");
	}

	/**
	 * 按ID集合删除红外方案
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:59:08
	 * 
	 * @param ids
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delOperatorsList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperatorsList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("红外方案删除失败,请选择要删除的红外方案");
		}
		String[] arr = ids.split(",");
		try {
			operatorsService.delOperatorsList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外方案删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除红外方案编号为：" + ids);
		}
		return Feedback.success("红外方案删除成功");
	}
}
