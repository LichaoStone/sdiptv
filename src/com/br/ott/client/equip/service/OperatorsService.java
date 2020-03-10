package com.br.ott.client.equip.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ott.client.equip.entity.Operators;
import com.br.ott.client.equip.mapper.OperatorsCodeMapper;
import com.br.ott.client.equip.mapper.OperatorsMapper;

/* 
 *  
 *  文件名：OperatorsService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午4:19:54
 */
@Service
public class OperatorsService {

	@Autowired
	private OperatorsMapper operatorsMapper;

	@Autowired
	private OperatorsCodeMapper operatorsCodeMapper;

	/**
	 * 增加运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:40:52
	 * 
	 * @param operators
	 *            返回类型：void
	 * @exception throws
	 */
	public void addOperators(Operators operators) {
		operatorsMapper.addOperators(operators);
	}

	/**
	 * 修改运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:05
	 * 
	 * @param operators
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateOperators(Operators operators) {
		operatorsMapper.updateOperators(operators);
	}

	/**
	 * 修改状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午5:02:48
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateOperatorsStatus(String status, String id) {
		operatorsMapper.updateOperatorsStatus(status, id);
	}

	/**
	 * 按ID删除运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:11
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	@Transactional
	public void delOperators(String id) {
		operatorsMapper.delOperators(id);
		operatorsCodeMapper.delOperatorsCodeByOperators(id);

	}

	/**
	 * 按ID集合删除运营商
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:57:28
	 * 
	 * @param ids
	 *            返回类型：void
	 * @exception throws
	 */
	@Transactional
	public void delOperatorsList(List<String> ids) {
		operatorsMapper.delOperatorsList(ids);
		for (String operators : ids) {
			operatorsCodeMapper.delOperatorsCodeByOperators(operators);
		}
	}

	/**
	 * 按ID查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:18
	 * 
	 * @param id
	 * @return 返回类型：Operators
	 * @exception throws
	 */
	public Operators getOperatorsById(String id) {
		return operatorsMapper.getOperatorsById(id);
	}

	/**
	 * 按条件查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:28
	 * 
	 * @param operators
	 * @return 返回类型：List<Operators>
	 * @exception throws
	 */
	public List<Operators> findOperatorsByCond(Operators operators) {
		return operatorsMapper.findOperatorsByCond(operators);
	}

	public boolean checkOperatorsByCode(String code) {
		Operators operators = new Operators();
		operators.setCode(code);
		List<Operators> list = findOperatorsByCond(operators);
		if (CollectionUtils.isNotEmpty(list)) {
			list = null;
			return false;
		}
		return true;
	}

	public List<Operators> findOperatorsByArea(String area) {
		return operatorsMapper.findOperatorsByArea(area);
	}

	public List<Operators> findOperatorsBySpName(String spName) {
		return operatorsMapper.findOperatorsBySpName(spName);
	}

	/**
	 * 按分页查询运营商
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:41:51
	 * 
	 * @param operators
	 * @return 返回类型：List<Operators>
	 * @exception throws
	 */
	public List<Operators> findOperatorsByPage(Operators operators) {
		operators.setTotalResult(operatorsMapper.getCountOperators(operators));
		return operatorsMapper.findOperatorsByPage(operators);
	}
}
