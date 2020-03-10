package com.br.ott.client.equip.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.service.BrandService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：BrandController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:55:55
 */
@Controller
@RequestMapping(value = "/equip")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "品牌管理";

	/**
	 * 按分页查询品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:09
	 * 
	 * @param request
	 * @param brand
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findBrand", method = RequestMethod.GET)
	public String findBrand(HttpServletRequest request, Brand brand, Model model) {
		brand.setCurrentPage(getPageNo(request));
		brand.setShowCount(getPageRow(request));
		List<Brand> list = brandService.findBrandByPage(brand);
		model.addAttribute("list", list);
		model.addAttribute("brand", brand);
		list = null;
		return "equip/listBrand";
	}

	/**
	 * 转到品牌编辑页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:23
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toBrand", method = RequestMethod.GET)
	public String toBrand(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		Brand brand = new Brand();
		if (StringUtil.isNotEmpty(id)) {
			brand = brandService.getBrandById(id);
		}
		model.addAttribute("brand", brand);

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		List<Dictionary> list = new ArrayList<Dictionary>();
		if (CollectionUtils.isNotEmpty(dicts)) {
			for (Dictionary ct : dicts) {
				Dictionary dictionary = new Dictionary();
				dictionary.setDicValue(ct.getDicValue());
				dictionary.setDicName(ct.getDicName());
				if (CollectionUtils.isNotEmpty(brand.getRecTypes())
						&& brand.getRecTypes().contains(
								dictionary.getDicValue())) {
					dictionary.setCheck("true");
				}
				list.add(dictionary);
			}
		}
		model.addAttribute("dicts", list);
		list = null;
		return "equip/brandInfo";
	}

	/**
	 * 校验品牌名称是否使用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:39
	 * 
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "checkBrandName", method = RequestMethod.GET)
	public void checkBrandName(HttpServletResponse response,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		if (StringUtil.isEmpty(name) || oldName.equals(name)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = brandService.checkBrandByName(name);
		writeAjaxResult(response, String.valueOf(result));
	}

	/**
	 * 修改品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:55
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateBrand")
	public @ResponseBody
	Feedback updateBrand(HttpServletRequest request, Brand brand) {
		Brand old = brandService.getBrandById(brand.getId());
		try {
			if (!old.getName().equals(brand.getName())) {
				boolean bool = brandService.checkBrandByName(brand.getName());
				if (!bool) {
					return Feedback.fail("品牌修改失败,品牌名称已使用");
				}
			}
			brandService.updateBrand(brand, request.getParameter("oldType"));
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改品牌失败");
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
		return Feedback.success("修改品牌成功");
	}

	/**
	 * 增加品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:06:05
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addBrand")
	public @ResponseBody
	Feedback addBrand(HttpServletRequest request, Brand brand) {
		try {
			boolean bool = brandService.checkBrandByName(brand.getName());
			if (!bool) {
				return Feedback.fail("品牌保存失败,品牌名称已使用");
			}
			brandService.addBrand(brand);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加品牌失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增品牌名称为：" + brand.getName());
		return Feedback.success("增加品牌成功");
	}
}
