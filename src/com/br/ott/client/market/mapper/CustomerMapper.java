package com.br.ott.client.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.market.entity.Customer;

/**
 * 文件名：CustomerMapper.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-8-31
 */
public interface CustomerMapper {

	/**
	 * 增加客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer 返回类型：void
	 * @exception throws
	 */
	void addCustomer(Customer customer);

	/**
	 * 修改客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer 返回类型：void
	 * @exception throws
	 */
	void updateCustomer(Customer customer);

	/**
	 * 修改客服状态
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void updateCustomerStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 按ID删除客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void delCustomerById(String id);

	/**
	 * 按ID查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param id
	 * @param @return 返回类型：Customer
	 * @exception throws
	 */
	Customer getCustomerById(String id);

	/**
	 * 按分页查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer
	 * @param @return 返回类型：List<Customer>
	 * @exception throws
	 */
	List<Customer> findCustomerByPage(Customer customer);

	int getCountCustomer(Customer customer);

	/**
	 * 按条件查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param customer
	 * @param @return 返回类型：List<Customer>
	 * @exception throws
	 */
	List<Customer> findCustomerByCond(Customer customer);

}
