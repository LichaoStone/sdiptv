package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.OperatorsCode;

/* 
 *  
 *  文件名：InfraredCodeMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:17:33
 */
public interface OperatorsCodeMapper {

	/**
	 * 增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:01
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	void addOperatorsCode(OperatorsCode operatorsCode);

	/**
	 * 修改红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:14
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperatorsCode(OperatorsCode operatorsCode);

	/**
	 * 修改红外码状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:27
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperatorsCodeStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 按分页查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:40
	 * 
	 * @param infraredCode
	 * @return 返回类型：List<InfraredCode>
	 * @exception throws
	 */
	List<OperatorsCode> findOperatorsCodeByPage(OperatorsCode operatorsCode);
	int getCountOperatorsCode(OperatorsCode operatorsCode);
	
	/**
	 * 按ID查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:52
	 * 
	 * @param id
	 * @return 返回类型：InfraredCode
	 * @exception throws
	 */
	OperatorsCode getOperatorsCodeById(String id);

	/**
	 * 按条件查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:04
	 * 
	 * @param infraredCode
	 * @return 返回类型：List<InfraredCode>
	 * @exception throws
	 */
	List<OperatorsCode> findOperatorsCodeByCond(OperatorsCode operatorsCode);

	List<OperatorsCode> findOperatorsCodeByOPList(
			@Param("opList") String opList, @Param("keyList") String keyList);

	/**
	 * 按ID删除红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:17
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperatorsCode(String id);

	/**
	 * 按ID集合删除红外码
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:52:49
	 * 
	 * @param ids
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperatorsCodeList(List<String> ids);

	/**
	 * 按运营商删除红外码
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午3:53:49
	 * 
	 * @param operators
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperatorsCodeByOperators(String operators);

	/**
	 * 按集合方式增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:28
	 * 
	 * @param list
	 *            返回类型：void
	 * @exception throws
	 */
	void addOperatorsCodeList(List<OperatorsCode> list);
}
