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
import com.br.ott.client.equip.entity.FeedbackOperators;
import com.br.ott.client.equip.service.FeedbackOperatorsService;
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
public class FeedbackOperatorsController extends BaseController {

	@Autowired
	private FeedbackOperatorsService feedbackService;
	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "运营商反馈管理";

	/**
	 * 按分页查询运营商反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:17:24
	 * 
	 * @param request
	 * @param feedback
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findFeedbackOperators", method = RequestMethod.GET)
	public String findFeedbackOperators(HttpServletRequest request,
			FeedbackOperators feedback, Model model) {
		feedback.setCurrentPage(getPageNo(request));
		feedback.setShowCount(getPageRow(request));
		List<FeedbackOperators> list = feedbackService
				.findFeedbackOperatorsByPage(feedback);
		model.addAttribute("list", list);
		model.addAttribute("feedback", feedback);
		list = null;

		List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
		model.addAttribute("dicts", dicts);
		dicts = null;

		return "equip/listFeedbackOperators";
	}

	/**
	 * 查看反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:17:13
	 * 
	 * @param request
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toFeedbackOperators", method = RequestMethod.GET)
	public String toFeedbackOperators(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		FeedbackOperators feedback = feedbackService
				.getFeedbackOperatorsById(id);
		model.addAttribute("feedback", feedback);
		return "equip/feedbackOperatorsInfo";
	}

	/**
	 * 反馈内容添加到品牌型号中
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午2:55:14
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：com.br.ott.common.util.Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateFeedbackOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback updateFeedbackOperators(HttpServletRequest request) {
		boolean flag = false;
		String remark = request.getParameter("remark");
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		try {
			feedbackService.updateFeedbackOperators(remark, status, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商反馈加入品牌型号失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"运营商反馈加入品牌型号编号为：" + id);
		}
		return Feedback.success("运营商反馈加入品牌型号成功");
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
	@RequestMapping(value = "delFeedbackOperators", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFeedback(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("运营商反馈删除失败,请选择要删除的反馈");
		}
		try {
			feedbackService.delFeedbackOperatorsById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商反馈删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商反馈编号为：" + id);
		}
		return Feedback.success("运营商反馈删除成功");
	}

	@RequestMapping(value = "delFeedbackOperatorsList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delFeedbackList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("运营商反馈删除失败,请选择要删除的运营商反馈");
		}
		try {
			String[] arr = ids.split(",");
			feedbackService.delFeedbackOperatorsList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("运营商反馈删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除运营商反馈编号为：" + ids);
		}
		return Feedback.success("运营商反馈删除成功");
	}
}
