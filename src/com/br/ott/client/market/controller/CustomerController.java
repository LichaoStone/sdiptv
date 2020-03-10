package com.br.ott.client.market.controller;

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
import com.br.ott.client.market.entity.Customer;
import com.br.ott.client.market.service.CustomerService;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;

/**
 * 文件名：CustomerController.java
 *  版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-8-31
 */
@Controller
@RequestMapping(value = "/market")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OperaLogService operaLogService;
	private static final String BUSI_NAME = "客服信息管理";

	/**
	 * 按分页查询客服信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:51:53
	 * 
	 * @param request
	 * @param area
	 * @param model
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCustomer", method = RequestMethod.GET)
	public String findCustomer(HttpServletRequest request, Customer customer, Model model) {
		customer.setCurrentPage(getPageNo(request));
		customer.setShowCount(getPageRow(request));
		List<Customer> list = customerService.findCustomerByPage(customer);
		model.addAttribute("list", list);
		model.addAttribute("customer", customer);
		list = null;
		return "market/listCustomer";
	}
	
	/**
	 * 转到客服维护页面
		 * 
		 * 创建人：pananz 创建时间：2015-8-31
		 * 
		 * @param @param request
		 * @param @param model
		 * @param @return
		 * 返回类型：String
		 * @exception throws
	 */
	@RequestMapping(value = "toCustomer", method = RequestMethod.GET)
	public String toCustomer(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		Customer customer = new Customer();
		if (StringUtil.isNotEmpty(id)) {
			customer = customerService.getCustomerById(id);
		}
		model.addAttribute("customer", customer);
		return "market/customerInfo";
	}
	
	/**
	 * 增加客服信息
		 * 
		 * 创建人：pananz 创建时间：2015-8-31
		 * 
		 * @param @param request
		 * @param @param customer
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "addCustomer")
	public @ResponseBody
	Feedback addCustomer(HttpServletRequest request, Customer customer) {
		try {
			customerService.addCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加客服失败");
		}
		operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
				"新增客服名称为：" + customer.getCustomerName());
		return Feedback.success("增加客服成功");
	}
	
	/**
	 * 修改客服信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param customer
	 * @param @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "updateCustomer")
	public @ResponseBody
	Feedback updateCustomer(HttpServletRequest request, Customer customer) {
		Customer old = customerService.getCustomerById(customer.getId());
		try {
			customerService.updateCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改客服失败");
		}
		try {
			String diffStr = ObjectUtil.getDiffColumnDescript(old, customer);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改客服成功");
	}
	
	/**
	 * 客服停用
		 * 
		 * 创建人：pananz 创建时间：2015-8-31
		 * 
		 * @param @param id
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "closeCustomer", method = RequestMethod.POST)
	public @ResponseBody
	Feedback closeCustomer(@RequestParam("id") String id, HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("客服停用失败,请选择要停用的客服");
		}
		try {
			customerService.updateCustomerStatus("0", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("客服停用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"停用客服编号为：" + id);
		}
		return Feedback.success("客服停用成功");
	}

	/**
	 * 客服启用
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午4:53:43
	 * 
	 * @param id
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "reverseCustomer", method = RequestMethod.POST)
	public @ResponseBody
	Feedback reverseCustomer(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("客服启用失败,请选择要启用的客服");
		}
		try {
			customerService.updateCustomerStatus("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("客服启用失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"启用客服编号为：" + id);
		}
		return Feedback.success("客服启用成功");
	}
	
	/**
	 * 删除客服
		 * 
		 * 创建人：pananz 创建时间：2015-8-31
		 * 
		 * @param @param id
		 * @param @param request
		 * @param @return
		 * 返回类型：Feedback
		 * @exception throws
	 */
	@RequestMapping(value = "delCustomer", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delCustomer(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("客服删除失败,请选择要删除的客服");
		}
		try {
			customerService.delCustomerById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("客服删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除客服编号为：" + id);
		}
		return Feedback.success("客服删除成功");
	}
}


