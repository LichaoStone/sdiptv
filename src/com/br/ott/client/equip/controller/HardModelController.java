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
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.service.BrandService;
import com.br.ott.client.equip.service.HardModelService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ModelController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:56:11
 */
@Controller
@RequestMapping(value = "/equip")
public class HardModelController extends BaseController {

	@Autowired
	private HardModelService hardModelService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "型号管理";

	/**
	 * 按分页查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:09
	 * 
	 * @param request
	 * @param brand
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findHardModel", method = RequestMethod.GET)
	public String findHardModel(HttpServletRequest request, HardModel m,
			Model model) {
		m.setCurrentPage(getPageNo(request));
		m.setShowCount(getPageRow(request));
		List<HardModel> list = hardModelService.findHardModelByPage(m);
		model.addAttribute("list", list);
		model.addAttribute("m", m);

		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);
		list = null;
		brands = null;
		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;

		return "equip/listModel";
	}

	/**
	 * 按条件取得型号信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-3 - 上午11:23:26
	 * 
	 * @param request
	 * @return 返回类型：List<HardModel>
	 * @exception throws
	 */
	@RequestMapping(value = "changeModel")
	public @ResponseBody
	List<HardModel> changeModel(HttpServletRequest request) {
		String brandId = request.getParameter("brandId");
		String type = request.getParameter("type");
		HardModel hModel = new HardModel();
		hModel.setStatus("1");
		hModel.setBrandId(brandId);
		hModel.setMachType(type);
		List<HardModel> models = hardModelService.findHardModelByCond(hModel);
		return models;
	}

	/**
	 * 转到型号编辑页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:23
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toHardModel", method = RequestMethod.GET)
	public String toHardModel(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		HardModel m = new HardModel();
		if (StringUtil.isNotEmpty(id)) {
			m = hardModelService.getHardModelById(id);
		}
		model.addAttribute("m", m);
		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);
		brands = null;

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;
		return "equip/modelInfo";
	}

	/**
	 * 校验型号名称是否使用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:39
	 * 
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "checkModelByNumber", method = RequestMethod.GET)
	public void checkModelByNumber(HttpServletResponse response,
			HttpServletRequest request) {
		String number = request.getParameter("number");
		String oldNumber = request.getParameter("oldNumber");
		String brandId = request.getParameter("brandId");
		String machType = request.getParameter("machType");
		
		if (StringUtil.isEmpty(number) || oldNumber.equals(number)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = hardModelService.checkModelByNumber(number, brandId,machType);
		writeAjaxResult(response, String.valueOf(result));
	}

	/**
	 * 修改型号信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:55
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateHardModel")
	public @ResponseBody
	Feedback updateHardModel(HttpServletRequest request, HardModel m) {
		HardModel old = hardModelService.getHardModelById(m.getId());
		try {
			if (!old.getNumber().equals(m.getNumber())) {
				boolean bool = hardModelService.checkModelByNumber(
						m.getNumber(), m.getBrandId(),m.getMachType());
				if (!bool) {
					return Feedback.fail("型号修改失败,型号名称已使用");
				}
			}
			hardModelService.updateHardModel(m);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改型号失败");
		}
		try {
			m.setStatus(old.getStatus());
			String diffStr = ObjectUtil.getDiffColumnDescript(old, m);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改型号成功");
	}

	/**
	 * 增加型号信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:06:05
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addHardModel")
	public @ResponseBody
	Feedback addHardModel(HttpServletRequest request, HardModel m) {
		try {
			boolean bool = hardModelService.checkModelByNumber(m.getNumber(),
					m.getBrandId(),m.getMachType());
			if (!bool) {
				return Feedback.fail("型号保存失败,型号名称已使用");
			}
			m.setStatus("1");
			hardModelService.addHardModel(m);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加型号失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增型号名称为：" + m.getNumber());
		return Feedback.success("增加型号成功");
	}

	/**
	 * 型号停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午1:55:28
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "hardModelClose", method = RequestMethod.POST)
	public @ResponseBody
	Feedback hardModelClose(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("型号停用失败,请选择要停用的型号");
		}
		try {
			hardModelService.updateModelStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("型号停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用型号编号为：" + id);
		}
		return Feedback.success("型号停用成功");
	}

	/**
	 * 型号启用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "hardModelReverse", method = RequestMethod.POST)
	public @ResponseBody
	Feedback hardModelReverse(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("型号启用失败,请选择要启用的型号");
		}
		try {
			hardModelService.updateModelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("型号启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用型号编号为：" + id);
		}
		return Feedback.success("型号启用成功");
	}

	/**
	 * 型号完善
	 * 
	 * 创建人：pananz 创建时间：2015-6-4 - 下午3:52:25
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "hardModelPass", method = RequestMethod.POST)
	public @ResponseBody
	Feedback hardModelPass(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("型号完善失败,请选择要完善的型号");
		}
		try {
			hardModelService.updateModelStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("型号完善失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"完善型号编号为：" + id);
		}
		return Feedback.success("型号完善成功");
	}

	@RequestMapping(value = "delModel", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delModel(@RequestParam("id") String id, HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("品牌型号删除失败,请选择要删除的品牌型号");
		}
		try {
			hardModelService.delHardModelById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("品牌型号删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除品牌型号编号为：" + id);
		}
		return Feedback.success("品牌型号删除成功");
	}

	/**
	 * 按ID集合删除品牌型号
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:59:08
	 * 
	 * @param ids
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delModelList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delModelList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("品牌型号删除失败,请选择要删除的品牌型号");
		}
		String[] arr = ids.split(",");
		try {
			hardModelService.delHardModelByList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("品牌型号删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除品牌型号编号为：" + ids);
		}
		return Feedback.success("品牌型号删除成功");
	}
}
