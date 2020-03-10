package com.br.ott.client.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.market.entity.AppMarket;
import com.br.ott.client.market.service.AppMarketService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/**
 * 文件名：AppMarketController.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
@Controller
@RequestMapping(value = "/market")
public class AppMarketController extends BaseController {

	@Autowired
	private AppMarketService appMarketService;

	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "应用上架信息管理";

	/**
	 * 按分页查询应用上架信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param area
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findAppMarket", method = RequestMethod.GET)
	public String findAppMarket(HttpServletRequest request,
			AppMarket appMarket, Model model) {
		appMarket.setCurrentPage(getPageNo(request));
		appMarket.setShowCount(getPageRow(request));
		List<AppMarket> list = appMarketService.findAppMarketByPage(appMarket);
		model.addAttribute("list", list);
		model.addAttribute("appMarket", appMarket);
		list = null;
		return "market/listAppMarket";
	}

	/**
	 * 转到应用上架信息页面
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param model
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toAppMarket", method = RequestMethod.GET)
	public String toAppMarket(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		AppMarket appMarket = new AppMarket();
		if (StringUtil.isNotEmpty(id)) {
			appMarket = appMarketService.getAppMarketById(id);
		}
		model.addAttribute("appMarket", appMarket);
		model.addAttribute("isUp", request.getParameter("isUp"));
		return "market/appMarketInfo";
	}

	/**
	 * 增加应用上架信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param customer
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addAppMarket")
	public @ResponseBody
	Feedback addAppMarket(HttpServletRequest request, AppMarket appMarket) {
		try {
			String isUp = request.getParameter("isUp");
			if ("true".equals(isUp)) {
				AppMarket old = appMarketService.getAppMarketById(appMarket
						.getId());
				appMarket.setIncr(old.getIncr() + 1);
			} else {
				appMarket.setIncr(1);
			}
			appMarketService.addAppMarket(appMarket);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加应用市场信息失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增应用市场信息名称为：" + appMarket.getMarketName());
		return Feedback.success("增加应用市场信息成功");
	}

	/**
	 * 修改应用上架信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param customer
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateAppMarket")
	public @ResponseBody
	Feedback updateAppMarket(HttpServletRequest request, AppMarket appMarket) {
		AppMarket old = appMarketService.getAppMarketById(appMarket.getId());
		try {
			appMarketService.updateAppMarket(appMarket);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改应用市场信息失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, appMarket);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改应用市场信息成功");
	}
	
	/**
	 * 删除应用市场信息
		 * 
		 * 创建人：pananz 创建时间：2015-9-2
		 * 
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delAppMarket")
	public @ResponseBody
	Feedback delAppMarket(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			appMarketService.delAppMarketById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除应用市场信息失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"删除应用市场信息编号为：" + id);
		return Feedback.success("删除应用市场信息成功");
	}
}
