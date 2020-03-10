package com.br.ott.client.equip.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.entity.InfraredCode;
import com.br.ott.client.equip.service.BrandService;
import com.br.ott.client.equip.service.HardModelService;
import com.br.ott.client.equip.service.InfraredCodeService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：InfraredCodeController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:56:27
 */
@Controller
@RequestMapping(value = "/equip")
public class InfraredCodeController extends BaseController {

	@Autowired
	private InfraredCodeService infraredCodeService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private HardModelService hardModelService;

	@Autowired
	private OperaLogService operaLogService;

	private static final String BUSI_NAME = "品牌红外码管理";

	/**
	 * 按分页查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:09
	 * 
	 * @param request
	 * @param brand
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findInfraredCode", method = RequestMethod.GET)
	public String findInfraredCode(HttpServletRequest request,
			InfraredCode infraredCode, Model model) {
		infraredCode.setCurrentPage(getPageNo(request));
		infraredCode.setShowCount(getPageRow(request));

		String brandId = request.getParameter("brandId");
		String type = request.getParameter("type");
		String modelName = request.getParameter("modelName");
		infraredCode.setBrandId(brandId);
		infraredCode.setType(type);
		infraredCode.setModelName(modelName);

		List<InfraredCode> list = infraredCodeService
				.findInfraredCodeByPage(infraredCode);
		model.addAttribute("list", list);
		model.addAttribute("infraredCode", infraredCode);

		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);

		HardModel hModel = new HardModel();
		hModel.setStatus("1");
		hModel.setBrandId(brandId);
		hModel.setMachType(type);
		List<HardModel> models = hardModelService.findHardModelByCond(hModel);
		model.addAttribute("models", models);

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;
		models = null;
		list = null;
		brands = null;
		return "equip/listCode";
	}

	/**
	 * 转到红外码编辑页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:23
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toInfraredCode", method = RequestMethod.GET)
	public String toBrand(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		InfraredCode infraredCode = new InfraredCode();
		if (StringUtil.isNotEmpty(id)) {
			infraredCode = infraredCodeService.getInfraredCodeById(id);
		}
		model.addAttribute("infraredCode", infraredCode);
		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);

		HardModel hModel = new HardModel();
		hModel.setStatus("1");
		List<HardModel> models = hardModelService.findHardModelByCond(hModel);
		model.addAttribute("models", models);

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;
		brands = null;
		models = null;
		return "equip/codeInfo";
	}

	/**
	 * 修改品牌红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:05:55
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateInfraredCode")
	public @ResponseBody
	Feedback updateInfraredCode(HttpServletRequest request, InfraredCode ic) {
		InfraredCode old = infraredCodeService.getInfraredCodeById(ic.getId());
		try {
			if (!old.getBrandId().equals(ic.getBrandId())
					&& !old.getType().equals(ic.getType())
					&& !old.getModelName().equals(ic.getModelName())
					&& !old.getKeyName().equals(ic.getKeyName())) {
				boolean bool = infraredCodeService.checkCode(ic.getBrandId(),
						ic.getType(), ic.getModelName(), ic.getKeyName());
				if (!bool) {
					return Feedback.fail("品牌红外键值名已使用,请重新输入");
				}
			}
			infraredCodeService.updateInfraredCode(ic);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改品牌红外码失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, ic);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改品牌红外码成功");
	}

	/**
	 * 增加红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午12:06:05
	 * 
	 * @param request
	 * @param brand
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addInfraredCode")
	public @ResponseBody
	Feedback addInfraredCode(HttpServletRequest request, InfraredCode ic) {
		try {
			boolean bool = infraredCodeService.checkCode(ic.getBrandId(),
					ic.getType(), ic.getModelName(), ic.getKeyName());
			if (!bool) {
				return Feedback.fail("品牌红外键值名已使用，请重新输入");
			}
			infraredCodeService.addInfraredCode(ic);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加品牌红外码失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增品牌红外码为：" + ic.getBrandName() + ic.getClientCode());
		return Feedback.success("增加品牌红外码成功");
	}

	/**
	 * 红外码停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午2:24:22
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "codeClose", method = RequestMethod.POST)
	public @ResponseBody
	Feedback codeClose(@RequestParam("id") String id, HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外码停用失败,请选择要停用的红外码");
		}
		try {
			infraredCodeService.updateInfraredCodeStatus("0", id);
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
	 * 创建人：pananz 创建时间：2015-4-2 - 下午2:24:15
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "codeReverse", method = RequestMethod.POST)
	public @ResponseBody
	Feedback codeReverse(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外码启用失败,请选择要启用的红外码");
		}
		try {
			infraredCodeService.updateInfraredCodeStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外码启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用红外码编号为：" + id);
		}
		return Feedback.success("红外码启用成功");
	}

	/**
	 * 红外码删除
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 下午2:24:05
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delCode", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delCode(@RequestParam("id") String id, HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外码删除失败,请选择要删除的红外码");
		}
		try {
			infraredCodeService.delInfraredCode(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外码删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除红外码编号为：" + id);
		}
		return Feedback.success("红外码删除成功");
	}

	/**
	 * 红外码删除失败
	 * 
	 * 创建人：pananz 创建时间：2015-6-9 - 下午1:58:05
	 * 
	 * @param ids
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delCodeList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delCodeList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("红外码删除失败,请选择要删除的红外码");
		}
		String[] arr = ids.split(",");
		try {
			infraredCodeService.delInfraredCodeList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("红外码删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除红外码编号为：" + ids);
		}
		return Feedback.success("红外码删除成功");
	}

	/**
	 * 通过EXCEL导入红外码
	 * 
	 * 创建人：pananz 创建时间：2015-5-14 - 下午3:37:31
	 * 
	 * @param msr
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "importInfraredCode", method = RequestMethod.POST)
	public void importInfraredCode(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("fileCode");
			msg = infraredCodeService.addInfraredCode(file.getInputStream(),
					request);
		} catch (IOException e) {
			e.printStackTrace();
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}
}
