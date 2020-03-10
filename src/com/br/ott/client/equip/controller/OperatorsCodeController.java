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
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.entity.Operators;
import com.br.ott.client.equip.entity.OperatorsCode;
import com.br.ott.client.equip.service.AreaService;
import com.br.ott.client.equip.service.BrandService;
import com.br.ott.client.equip.service.HardModelService;
import com.br.ott.client.equip.service.OperatorsCodeService;
import com.br.ott.client.equip.service.OperatorsService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：OperatorsCodeController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午4:39:14
 */
@Controller
@RequestMapping(value = "/equip")
public class OperatorsCodeController extends BaseController {

	@Autowired
	private OperatorsCodeService operatorsCodeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OperatorsService operatorsService;
	@Autowired
	private OperaLogService operaLogService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private HardModelService hardModelService;

	private static final String BUSI_NAME = "方案红外码管理";

	/**
	 * 按分页查询方案红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param OperatorsCode
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperatorsCode", method = RequestMethod.GET)
	public String findOperatorsCode(HttpServletRequest request,
			OperatorsCode infraredCode, Model model) {
		infraredCode.setCurrentPage(getPageNo(request));
		infraredCode.setShowCount(getPageRow(request));
		String areaId = request.getParameter("areaId");
		String operators = request.getParameter("operators");
		String parentId = request.getParameter("parentId");
		infraredCode.setAreaId(areaId);
		infraredCode.setOperators(operators);
		infraredCode.setParentId(parentId);

		List<OperatorsCode> list = operatorsCodeService
				.findOperatorsCodeByPage(infraredCode);
		model.addAttribute("list", list);
		list = null;

		Area area = new Area();
		area.setParentId("0");
		area.setStatus("1");
		List<Area> areas = areaService.findAreaByCond(area);
		model.addAttribute("areas", areas);
		areas = null;

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;

		if (StringUtil.isNotEmpty(areaId)) {
			if (StringUtil.isEmpty(parentId)) {
				Area area2 = areaService.getAreaById(areaId);
				if (area2 != null) {
					infraredCode.setParentId(area2.getParentId());
				}
			}
			Operators op = new Operators();
			op.setStatus("1");
			op.setAreaId(areaId);
			List<Operators> olist = operatorsService.findOperatorsByCond(op);
			model.addAttribute("olist", olist);
			olist = null;
		}
		if(StringUtil.isNotEmpty(infraredCode.getParentId())){
			Area a = new Area();
			a.setStatus("1");
			a.setParentId(infraredCode.getParentId());
			List<Area> alist = areaService.findAreaByCond(a);
			model.addAttribute("alist", alist);
			alist = null;
		}
		model.addAttribute("infraredCode", infraredCode);
		return "equip/listCode2";
	}

	/**
	 * 转到方案红外码页面
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:37
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toOperatorsCode", method = RequestMethod.GET)
	public String toOperatorsCode(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		OperatorsCode infraredCode = new OperatorsCode();
		String areaId = request.getParameter("areaId");
		String parentId = request.getParameter("parentId");
		String operators = request.getParameter("operators");
		infraredCode.setOperators(operators);
		infraredCode.setAreaId(areaId);
		infraredCode.setParentId(parentId);

		if (StringUtil.isNotEmpty(id)) {
			infraredCode = operatorsCodeService.getOperatorsCodeById(id);
		}
		Area area = new Area();
		area.setStatus("1");
		area.setParentId("0");
		List<Area> areas = areaService.findAreaByCond(area);
		model.addAttribute("areas", areas);
		areas = null;
		if (StringUtil.isNotEmpty(infraredCode.getAreaId())) {
			if (StringUtil.isEmpty(parentId)) {
				Area area2 = areaService.getAreaById(areaId);
				if (area2 != null) {
					parentId = area2.getParentId();
					infraredCode.setParentId(parentId);
				}
			}
			Area a = new Area();
			a.setStatus("1");
			a.setParentId(parentId);
			List<Area> alist = areaService.findAreaByCond(a);
			model.addAttribute("alist", alist);
			alist = null;

			Operators op = new Operators();
			op.setStatus("1");
			op.setAreaId(infraredCode.getAreaId());
			List<Operators> olist = operatorsService.findOperatorsByCond(op);
			model.addAttribute("olist", olist);
			olist = null;
		}

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;

		Brand brand = new Brand();
		brand.setStatus("1");
		List<Brand> brands = brandService.findBrandByCond(brand);
		model.addAttribute("brands", brands);
		brands = null;

		HardModel hModel = new HardModel();
		hModel.setStatus("1");
		List<HardModel> models = hardModelService.findHardModelByCond(hModel);
		model.addAttribute("models", models);
		models = null;
		model.addAttribute("infraredCode", infraredCode);

		return "equip/code2Info";
	}

	/**
	 * 增加方案红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:18
	 * 
	 * @param request
	 * @param OperatorsCode
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addOperatorsCode")
	public @ResponseBody
	Feedback addOperatorsCode(HttpServletRequest request, OperatorsCode ic) {
		try {
			boolean bool = operatorsCodeService.checkCode(ic.getAreaId(),
					ic.getOperators(), ic.getType(), ic.getKeyName());
			if (!bool) {
				return Feedback.fail("方案红外键值码已使用，请重新输入");
			}
			operatorsCodeService.addOperatorsCode(ic);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加方案红外码失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增方案红外码名称为：" + ic.getOperators());
		return Feedback.success("增加方案红外码成功");
	}

	/**
	 * 修改方案红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:09
	 * 
	 * @param request
	 * @param OperatorsCode
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateOperatorsCode")
	public @ResponseBody
	Feedback updateOperatorsCode(HttpServletRequest request, OperatorsCode ic) {
		OperatorsCode old = operatorsCodeService.getOperatorsCodeById(ic
				.getId());
		try {
			if (!old.getAreaId().equals(ic.getAreaId())
					&& !old.getOperators().equals(ic.getOperators())
					&& !old.getType().equals(ic.getType())
					&& !old.getKeyCode().equals(ic.getKeyCode())) {
				boolean bool = operatorsCodeService.checkCode(ic.getAreaId(),
						ic.getOperators(), ic.getType(), ic.getKeyName());
				if (!bool) {
					return Feedback.fail("方案红外键值码已使用,请重新输入");
				}
			}
			operatorsCodeService.updateOperatorsCode(ic);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改方案红外码失败");
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
		return Feedback.success("修改方案红外码成功");
	}

	/**
	 * 方案红外码停用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:46
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "closeOperatorsCode", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeOperatorsCode(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("方案红外码停用失败,请选择要停用的方案红外码");
		}
		try {
			operatorsCodeService.updateOperatorsCodeStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("方案红外码停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用方案红外码编号为：" + id);
		}
		return Feedback.success("方案红外码停用成功");
	}

	/**
	 * 方案红外码启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseOperatorsCode", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseOperatorsCode(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("方案红外码启用失败,请选择要启用的方案红外码");
		}
		try {
			operatorsCodeService.updateOperatorsCodeStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("方案红外码启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用方案红外码编号为：" + id);
		}
		return Feedback.success("方案红外码启用成功");
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
	@RequestMapping(value = "delOperatorsCode", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperatorsCode(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("红外码删除失败,请选择要删除的红外码");
		}
		try {
			operatorsCodeService.delOperatorsCode(id);
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
	@RequestMapping(value = "delOperatorsCodeList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delOperatorsCodeList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("红外码删除失败,请选择要删除的红外码");
		}
		String[] arr = ids.split(",");
		try {
			operatorsCodeService.delOperatorsCodeList(Arrays.asList(arr));
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
	 * 通过EXCEL导入方案红外码
	 * 
	 * 创建人：pananz 创建时间：2015-5-14 - 下午3:37:31
	 * 
	 * @param msr
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "importOperatorsCode", method = RequestMethod.POST)
	public void importOperatorsCode(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("fileCode");
			msg = operatorsCodeService.addInfraredCode(file.getInputStream(),
					request);
		} catch (IOException e) {
			e.printStackTrace();
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}
}
