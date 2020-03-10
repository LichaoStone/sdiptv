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
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.service.CityOperatorsService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：CityOperatorsController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-12 - 下午2:48:15
 */
@Controller
@RequestMapping(value = "/live")
public class CityOperatorsController extends BaseController {

	@Autowired
	private CityOperatorsService operatorsService;
	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "运营商管理";

	/**
	 * 按分页查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param Operators
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCityOperators", method = RequestMethod.GET)
	public String findOperators(HttpServletRequest request,
			CityOperators operators, Model model) {
		operators.setCurrentPage(getPageNo(request));
		operators.setShowCount(getPageRow(request));
		String parentId=request.getParameter("parentId");
		String areaId=request.getParameter("areaId");
		operators.setAreaId(areaId);
		operators.setParentId(parentId);
		List<CityOperators> list = operatorsService
				.findCityOperatorsByPage(operators);
		model.addAttribute("list", list);
		model.addAttribute("operators", operators);
		list = null;
		return "live/listOperators";
	}

	/**
	 * 转到运营商页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:37
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toCityOperators", method = RequestMethod.GET)
	public String toCityOperators(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		CityOperators operators = new CityOperators();
		if (StringUtil.isNotEmpty(id)) {
			operators = operatorsService.getCityOperatorsById(id);
		}
		model.addAttribute("operators", operators);
		return "live/operatorsInfo";
	}

	@RequestMapping(value = "changeCityOperators")
	public @ResponseBody
	List<CityOperators> changeCityOperators(HttpServletRequest request) {
		String areaId = request.getParameter("areaId");
		CityOperators operators = new CityOperators();
		operators.setStatus("1");
		operators.setAreaId(areaId);
		List<CityOperators> list = operatorsService
				.findCityOperatorsByCond(operators);
		return list;
	}

	/**
	 * 增加运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:18
	 * 
	 * @param request
	 * @param Operators
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addCityOperators")
	public @ResponseBody
	Feedback addCityOperators(HttpServletRequest request,
			CityOperators operators) {
		try {
			operators.setStatus("1");
			operatorsService.addCityOperators(operators);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加运营商失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增运营商名称为：" + operators.getName());
		return Feedback.success("增加运营商成功");
	}

	/**
	 * 修改运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:09
	 * 
	 * @param request
	 * @param Operators
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateCityOperators")
	public @ResponseBody
	Feedback updateCityOperators(HttpServletRequest request,
			CityOperators operators) {
		CityOperators old = operatorsService.getCityOperatorsById(operators
				.getId());
		try {
			operatorsService.updateCityOperators(operators);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改运营商失败");
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
		return Feedback.success("修改运营商成功");
	}

	/**
	 * 运营商停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:46
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeCityOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeCityOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商停用失败,请选择要停用的运营商");
		}
		try {
			operatorsService.updateCityOperatorsStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用运营商编号为：" + id);
		}
		return Feedback.success("运营商停用成功");
	}

	/**
	 * 运营商启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseCityOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseCityOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商启用失败,请选择要启用的运营商");
		}
		try {
			operatorsService.updateCityOperatorsStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用运营商编号为：" + id);
		}
		return Feedback.success("运营商启用成功");
	}

	@RequestMapping(value = "passCityOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback passCityOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商完善失败,请选择要完善的运营商");
		}
		try {
			operatorsService.updateCityOperatorsStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商完善失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"完善运营商编号为：" + id);
		}
		return Feedback.success("运营商完善成功");
	}

	@RequestMapping(value = "delCityOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delCityOperators(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商删除失败,请选择要删除的运营商");
		}
		try {
			operatorsService.delCityOperatorsById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商编号为：" + id);
		}
		return Feedback.success("运营商删除成功");
	}
	@RequestMapping(value = "delCityOperatorsList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delCityOperatorsList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("运营商删除失败,请选择要删除的运营商");
		}
		try {
			String[] arr = ids.split(",");
			operatorsService.delCityOperatorsList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商编号为：" + ids);
		}
		return Feedback.success("运营商删除成功");
	}
	
}
