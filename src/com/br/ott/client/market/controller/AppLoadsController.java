package com.br.ott.client.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseController;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.market.entity.AppLoads;
import com.br.ott.client.market.entity.AppMarket;
import com.br.ott.client.market.service.AppLoadsService;
import com.br.ott.client.market.service.AppMarketService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.StringUtil;

/**
 * 文件名：AppLoadsController.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
@Controller
@RequestMapping(value = "/market")
public class AppLoadsController extends BaseController {

	@Autowired
	private AppLoadsService appLoadsService;
	@Autowired
	private AppMarketService appMarketService;

	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "应用版本下载量管理";

	/**
	 * 按分页查询应用版本日下载信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param area
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findAppLoads", method = RequestMethod.GET)
	public String findAppLoads(HttpServletRequest request, AppLoads appLoads,
			Model model) {
		appLoads.setMarketId(request.getParameter("marketId"));
		appLoads.setCurrentPage(getPageNo(request));
		appLoads.setShowCount(getPageRow(request));
		List<AppLoads> list = appLoadsService.findAppLoadsByPage(appLoads);
		model.addAttribute("list", list);
		model.addAttribute("appLoads", appLoads);
		list = null;
		return "market/listAppLoads";
	}

	/**
	 * 转到应用版本日下载页面
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param model
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "toAppLoads", method = RequestMethod.GET)
	public String toAppLoads(HttpServletRequest request, Model model) {
		AppLoads appLoads = new AppLoads();
		String marketId = request.getParameter("marketId");
		AppMarket appMarket = appMarketService.getAppMarketById(marketId);
		if (appMarket != null) {
			appLoads.setMarketId(marketId);
			appLoads.setMarketName(appMarket.getMarketName());
			appLoads.setVersion(appMarket.getVersion());
		}
		model.addAttribute("appLoads", appLoads);
		return "market/appLoadsInfo";
	}

	/**
	 * 
		 * 
		 * 创建人：pananz 创建时间：2015-9-2
		 * 
		 * @param @param response
		 * @param @param request
		 * 返回类型：void
		 * @exception throws
	 */
	@RequestMapping(value = "checkAppLoadsByDate", method = RequestMethod.GET)
	public void checkAppLoadsByDate(HttpServletResponse response,
			HttpServletRequest request) {
		String recordTime = request.getParameter("recordTime");
		String marketId = request.getParameter("marketId");
		if (StringUtil.isEmpty(recordTime) || StringUtil.isEmpty(marketId)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = appLoadsService.checkAppLoadsByDate(marketId,
				recordTime);
		writeAjaxResult(response, String.valueOf(result));
	}

	/**
	 * 增加应用版本日下载信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param customer
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "addAppLoads")
	public @ResponseBody
	Feedback addAppLoads(HttpServletRequest request, AppLoads appLoads) {
		try {
			boolean result = appLoadsService.checkAppLoadsByDate(appLoads.getMarketId(),
					appLoads.getRecordTime());
			if(!result){
				return Feedback.fail("增加应用版本下载量信息失败,已有当天的下载量信息");
			}
			appLoadsService.addAppLoads(appLoads);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加应用版本下载量信息失败");
		}
		operaLogService.addOperaLog(
				OPERA_TYPE_ADD,
				request,
				BUSI_NAME,
				"新增应用版本下载量信息名称为：" + appLoads.getMarketName() + "时间："
						+ appLoads.getRecordTime() + "下载量："
						+ appLoads.getDayDownLoads());
		return Feedback.success("增加应用版本下载量信息成功");
	}

	/**
	 * 删除应用版本日下载信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param customer
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "delAppLoads")
	public @ResponseBody
	Feedback delAppLoads(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			appLoadsService.delAppLoadsById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("删除应用版本下载量信息失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
				"删除应用版本下载量信息编号号" + id);
		return Feedback.success("删除应用版本下载量信息成功");
	}
}
