package com.br.ott.client.live.controller;

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
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.SearchLog;
import com.br.ott.client.live.service.SearchLogService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：SearchLogController.java
 *  创建人：pananz
 *  创建时间：2014-7-23 - 下午10:45:05
 */
@Controller
@RequestMapping(value = "/search")
public class SearchLogController extends BaseController {

	@Autowired
	private SearchLogService searchLogService;
	@Autowired
	private OperaLogService operaLogService;
	
	@RequestMapping(value = "findSearchLog", method = RequestMethod.GET)
	public String findSearchLog(HttpServletRequest request,
			SearchLog searchLog, Model model) {
		searchLog.setCurrentPage(getPageNo(request));
		searchLog.setShowCount(getPageRow(request));
		List<SearchLog> list = searchLogService.findSearchLogByPage(searchLog);
		model.addAttribute("list", list);
		model.addAttribute("searchLog", searchLog);
		list = null;
		return "live/listLog";
	}

	@RequestMapping(value = "toSNumber", method = RequestMethod.GET)
	public String toSNumber(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String virtualNumber = request.getParameter("virtualNumber");
		model.addAttribute("id", id);
		model.addAttribute("virtualNumber", virtualNumber);
		return "live/sNumber";
	}

	@RequestMapping(value = "updateVNumber")
	public @ResponseBody
	Feedback updateVNumber(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String virtualNumber = request.getParameter("virtualNumber");
			searchLogService.updateVirtualNumber(virtualNumber, id);
			return Feedback.success("修改搜索干预值成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改搜索干预值失败");
		}
	}

	@RequestMapping(value = "searchLogClose", method = RequestMethod.POST)
	public @ResponseBody
	Feedback searchLogClose(@RequestParam("id") String id,
			HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("搜索内容停用失败,请选择要停用的搜索内容");
		}
		try {
			searchLogService.updateSearchLogStatus("0", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("搜索内容停用失败");
		}
		return Feedback.success("搜索内容停用成功");
	}

	/**
	 * 类型类型启用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "searchLogReverse", method = RequestMethod.POST)
	public @ResponseBody
	Feedback searchLogReverse(@RequestParam("id") String id,
			HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("搜索内容启用失败,请选择要启用的搜索内容");
		}
		try {
			searchLogService.updateSearchLogStatus("1", id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("搜索内容启用失败");
		}
		return Feedback.success("搜索内容启用成功");
	}

	@RequestMapping(value = "delSearch", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delSearch(@RequestParam("id") String id, HttpServletRequest request) {
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("搜索内容删除败,请选择要删除的搜索内容");
		}
		try {
			searchLogService.delSearchLog(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("搜索内容删除失败");
		}
		return Feedback.success("搜索内容删除成功");
	}
	/**
	 * 批量删除节目热门搜索
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delLogList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delLogList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("节目热门搜索删除失败,请选择要删除的节目热门搜索");
		}
		try {
			String[] arr = ids.split(",");
			searchLogService.delLogList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目热门搜索删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, "节目热门搜索管理",
					"节目热门搜索编号为：" + ids);
		}
		return Feedback.success("节目热门搜索删除成功");
	}
}
