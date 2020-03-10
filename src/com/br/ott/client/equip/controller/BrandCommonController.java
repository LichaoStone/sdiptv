package com.br.ott.client.equip.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.BrandCommon;
import com.br.ott.client.equip.service.BrandCommonService;
import com.br.ott.client.equip.service.BrandService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：BrandCommonController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-2 - 上午10:23:54
 */
@Controller
@RequestMapping(value = "/equip")
public class BrandCommonController extends BaseController {

	@Autowired
	private BrandCommonService brandCommonService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "品牌类型推荐管理";

	/**
	 * 按分页查询品牌类型推荐
	 * 
	 * 创建人：pananz 创建时间：2015-7-2 - 上午10:38:10
	 * 
	 * @param request
	 * @param brandCommon
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findBrandCommon", method = RequestMethod.GET)
	public String findBrandCommon(HttpServletRequest request,
			BrandCommon brandCommon, Model model) {
		brandCommon.setCurrentPage(getPageNo(request));
		brandCommon.setShowCount(getPageRow(request));
		List<BrandCommon> list = brandCommonService
				.findBrandCommonByPage(brandCommon);
		model.addAttribute("list", list);
		model.addAttribute("brandCommon", brandCommon);

		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		brands = null;
		dicts = null;
		list = null;
		return "equip/listBrandCommon";
	}

	/**
	 * 转到品牌类型推荐页面
	 * 
	 * 创建人：pananz 创建时间：2015-7-2 - 上午10:37:55
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toBrandCommon", method = RequestMethod.GET)
	public String toBrandCommon(HttpServletRequest request, Model model) {
		BrandCommon brandCommon = new BrandCommon();
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			brandCommon = brandCommonService.getBrandCommonById(id);
		}
		model.addAttribute("brandCommon", brandCommon);
		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		brands = null;
		dicts = null;
		return "equip/brandCommonInfo";
	}

	/**
	 * 增加品牌类型推荐
	 * 
	 * 创建人：pananz 创建时间：2015-7-2 - 上午10:37:49
	 * 
	 * @param request
	 * @param brandCommon
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addBrandCommon")
	public @ResponseBody
	Feedback addBrandCommon(HttpServletRequest request, BrandCommon brandCommon) {
		try {
			List<BrandCommon> list = brandCommonService
					.findBrandCommonByCond(brandCommon);
			if (CollectionUtils.isNotEmpty(list)) {
				return Feedback.fail("增加品牌类型推荐失败,已存在此品牌类型推荐");
			}
			list = null;
			brandCommonService.addBrandCommon(brandCommon);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加品牌类型推荐失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增品牌类型推荐名称为：" + brandCommon.getBrandName());
		return Feedback.success("增加品牌类型推荐成功");
	}

	/**
	 * 修改品牌类型推荐
	 * 
	 * 创建人：pananz 创建时间：2015-7-21 - 下午6:16:59
	 * 
	 * @param request
	 * @param brandCommon
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateBrandCommon")
	public @ResponseBody
	Feedback updateBrandCommon(HttpServletRequest request,
			BrandCommon brandCommon) {
		BrandCommon old = brandCommonService.getBrandCommonById(brandCommon
				.getId());
		try {
			if (!old.getBrandId().equals(brandCommon.getBrandId())
					|| !old.getMachType().equals(brandCommon.getMachType())) {
				List<BrandCommon> list = brandCommonService
						.findBrandCommonByCond(brandCommon);
				if (CollectionUtils.isNotEmpty(list)) {
					list = null;
					return Feedback.fail("修改品牌类型推荐失败,已存在此品牌类型推荐");
				}
			}
			brandCommonService.updateBrandCommon(brandCommon);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改品牌类型推荐失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, brandCommon);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改品牌类型推荐成功");
	}

	/**
	 * 删除品牌类型推荐
	 * 
	 * 创建人：pananz 创建时间：2015-7-2 - 上午10:37:35
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delBrandCommon", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delBrandCommon(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("品牌类型推荐删除失败,请选择要删除的品牌类型推荐");
		}
		try {
			brandCommonService.deleteBrandCommonById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("品牌类型推荐删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除品牌类型推荐编号为：" + id);
		}
		return Feedback.success("品牌类型推荐删除成功");
	}

	@RequestMapping(value = "delBrandCommonList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delBrandCommonList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("品牌类型推荐删除失败,请选择要删除的品牌类型推荐");
		}
		String[] arr = ids.split(",");
		try {
			brandCommonService.delBrandCommonList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("品牌类型推荐删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除品牌类型推荐编号为：" + ids);
		}
		return Feedback.success("品牌类型推荐删除成功");
	}
}
