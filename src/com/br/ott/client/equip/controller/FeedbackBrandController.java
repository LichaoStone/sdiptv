package com.br.ott.client.equip.controller;

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
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.FeedbackBrand;
import com.br.ott.client.equip.service.FeedbackBrandService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：FeedbackController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 下午12:07:25
 */
@Controller
@RequestMapping(value = "/equip")
public class FeedbackBrandController extends BaseController {

	@Autowired
	private FeedbackBrandService feedbackService;

	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "品牌型号反馈管理";

	/**
	 * 按分页查询品牌型号反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:17:24
	 * 
	 * @param request
	 * @param feedback
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findFeedbackBrand", method = RequestMethod.GET)
	public String findFeedbackBrand(HttpServletRequest request,
			FeedbackBrand feedback, Model model) {
		feedback.setCurrentPage(getPageNo(request));
		feedback.setShowCount(getPageRow(request));
		List<FeedbackBrand> list = feedbackService
				.findFeedbackBrandByPage(feedback);
		model.addAttribute("list", list);
		model.addAttribute("feedback", feedback);
		list = null;
		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;
		return "equip/listFeedbackBrand";
	}

	/**
	 * 查看品牌型号反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:17:13
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toFeedbackBrand", method = RequestMethod.GET)
	public String toFeedbackBrand(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		FeedbackBrand feedback = feedbackService.getFeedbackBrandById(id);
		model.addAttribute("feedback", feedback);
		return "equip/feedbackBrandInfo";
	}

	/**
	 * 品牌型号反馈处理
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午2:55:14
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：com.br.ott.common.util.Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateFeedbackBrand", method = RequestMethod.POST)
	public @ResponseBody
	Feedback updateFeedbackBrand(HttpServletRequest request) {
		boolean flag = false;
		String remark = request.getParameter("remark");
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		try {
			feedbackService.updateFeedbackBrand(remark, status, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("处理反馈品牌型号失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"处理反馈品牌型号编号为：" + id);
		}
		return Feedback.success("处理反馈品牌型号成功");
	}

	/**
	 * 删除反馈
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:17:04
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：com.br.ott.common.util.Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delFeedbackBrand", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFeedbackBrand(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("反馈删除失败,请选择要删除的反馈");
		}
		try {
			feedbackService.delFeedbackBrandById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("反馈删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除反馈编号为：" + id);
		}
		return Feedback.success("反馈删除成功");
	}

	@RequestMapping(value = "delFeedbackBrandList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFeedbackBrandList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("反馈删除失败,请选择要删除的反馈");
		}
		try {
			String[] arr = ids.split(",");
			feedbackService.delFeedbackBrandList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("反馈删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除反馈编号为：" + ids);
		}
		return Feedback.success("反馈删除成功");
	}
}
