package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.Operators;

/* 
 *  
 *  文件名：OperatorsMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 上午10:58:04
 */
public interface OperatorsMapper {

	/**
	 * 增加运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:40:52
	 * 
	 * @param operators
	 *            返回类型：void
	 * @exception throws
	 */
	void addOperators(Operators operators);

	/**
	 * 修改运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:05
	 * 
	 * @param operators
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperators(Operators operators);

	/**
	 * 修改运营商状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午5:01:27
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperatorsStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 删除运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:11
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperators(String id);

	void delOperatorsList(List<String> ids);

	/**
	 * 按ID查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:18
	 * 
	 * @param id
	 * @return 返回类型：Operators
	 * @exception throws
	 */
	Operators getOperatorsById(String id);

	/**
	 * 按条件查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:28
	 * 
	 * @param operators
	 * @return 返回类型：List<Operators>
	 * @exception throws
	 */
	List<Operators> findOperatorsByCond(Operators operators);

	/**
	 * 按分页查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:51
	 * 
	 * @param operators
	 * @return 返回类型：List<Operators>
	 * @exception throws
	 */
	List<Operators> findOperatorsByPage(Operators operators);
	int getCountOperators(Operators operators);
	
	List<Operators> findOperatorsByArea(String area);
	
	List<Operators> findOperatorsBySpName(String spName);
	
}
