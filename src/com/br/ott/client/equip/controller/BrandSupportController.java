package com.br.ott.client.equip.controller;

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
import com.br.ott.client.equip.entity.BrandSupport;
import com.br.ott.client.equip.service.BrandSupportService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：BrandSupportController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-5 - 下午5:14:17
 */
@Controller
@RequestMapping(value = "/equip")
public class BrandSupportController extends BaseController {

	@Autowired
	private BrandSupportService brandSupportService;

	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "红外手机品牌管理";

	/**
	 * 按分页查询红外手机品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:09
	 * 
	 * @param request
	 * @param brand
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findBrandSupport", method = RequestMethod.GET)
	public String findBrandSupport(HttpServletRequest request,
			BrandSupport brand, Model model) {
		brand.setCurrentPage(getPageNo(request));
		brand.setShowCount(getPageRow(request));
		List<BrandSupport> list = brandSupportService
				.findBrandSupportByPage(brand);
		model.addAttribute("list", list);
		model.addAttribute("brand", brand);
		list = null;
		return "equip/listBrandSupport";
	}

	/**
	 * 转到红外手机品牌编辑页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:23
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toBrandSupport", method = RequestMethod.GET)
	public String toBrandSupport(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		BrandSupport brand = new BrandSupport();
		if (StringUtil.isNotEmpty(id)) {
			brand = brandSupportService.getBrandSupportById(id);
		}
		model.addAttribute("brand", brand);
		return "equip/brandSupportInfo";
	}

	/**
	 * 校验红外手机品牌名称是否使用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:39
	 * 
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "checkBrandSupportByName", method = RequestMethod.GET)
	public void checkBrandSupportByName(HttpServletResponse response,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		if (StringUtil.isEmpty(name) || oldName.equals(name)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = brandSupportService.checkBrandSupportByName(name);
		writeAjaxResult(response, String.valueOf(result));
	}

	/**
	 * 修改红外手机品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:55
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateBrandSupport")
	public @ResponseBody
	Feedback updateBrandSupport(HttpServletRequest request, BrandSupport brand) {
		BrandSupport old = brandSupportService.getBrandSupportById(brand
				.getId());
		try {
			if (!old.getName().equals(brand.getName())) {
				boolean bool = brandSupportService
						.checkBrandSupportByName(brand.getName());
				if (!bool) {
					return Feedback.fail("红外手机品牌修改失败,品牌名称已使用");
				}
			}
			brandSupportService.updateBrandSupport(brand);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改红外手机品牌失败");
		}
		try {
			brand.setStatus(old.getStatus());
			String diffStr = ObjectUtil.getDiffColumnDescript(old, brand);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改红外手机品牌成功");
	}

	/**
	 * 增加红外手机品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:06:05
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addBrandSupport")
	public @ResponseBody
	Feedback addBrandSupport(HttpServletRequest request, BrandSupport brand) {
		try {
			boolean bool = brandSupportService.checkBrandSupportByName(brand
					.getName());
			if (!bool) {
				return Feedback.fail("红外手机品牌保存失败,品牌名称已使用");
			}
			brandSupportService.addBrandSupport(brand);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加红外手机品牌失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增红外手机品牌名称为：" + brand.getName());
		return Feedback.success("增加红外手机品牌成功");
	}

	/**
	 * 红外手机品牌停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午1:55:28
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeBrandSupport", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeBrandSupport(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外手机品牌停用失败,请选择要停用的品牌");
		}
		try {
			brandSupportService.updateSupportStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外手机品牌停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用红外手机品牌编号为：" + id);
		}
		return Feedback.success("红外手机品牌停用成功");
	}

	/**
	 * 红外手机品牌启用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "reverseBrandSupport", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseBrandSupport(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外手机品牌启用失败,请选择要启用的品牌");
		}
		try {
			brandSupportService.updateSupportStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外手机品牌启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用红外手机品牌编号为：" + id);
		}
		return Feedback.success("红外手机品牌启用成功");
	}

}
