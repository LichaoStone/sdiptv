package com.br.ott.client.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.market.entity.Customer;
import com.br.ott.client.market.mapper.CustomerMapper;

/**
 * 文件名：CustomerService.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-8-31
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	/**
	 * 增加客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer 返回类型：void
	 * @exception throws
	 */
	public void addCustomer(Customer customer) {
		customerMapper.addCustomer(customer);
	}

	/**
	 * 修改客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer 返回类型：void
	 * @exception throws
	 */
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}

	/**
	 * 修改客服状态
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	public void updateCustomerStatus(String status, String id) {
		customerMapper.updateCustomerStatus(status, id);
	}

	/**
	 * 按ID删除客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	public void delCustomerById(String id) {
		customerMapper.delCustomerById(id);
	}

	/**
	 * 按ID查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param id
	 * @param @return 返回类型：Customer
	 * @exception throws
	 */
	public Customer getCustomerById(String id) {
		return customerMapper.getCustomerById(id);
	}

	/**
	 * 按分页查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer
	 * @param @return 返回类型：List<Customer>
	 * @exception throws
	 */
	public List<Customer> findCustomerByPage(Customer customer) {
		int totalResult=customerMapper.getCountCustomer(customer);
		customer.setTotalResult(totalResult);
		return customerMapper.findCustomerByPage(customer);
	}

	public int getCountCustomer(Customer customer) {
		return customerMapper.getCountCustomer(customer);
	}

	/**
	 * 按条件查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer
	 * @param @return 返回类型：List<Customer>
	 * @exception throws
	 */
	public List<Customer> findCustomerByCond(Customer customer) {
		return customerMapper.findCustomerByCond(customer);
	}
}
