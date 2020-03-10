package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.InfraredCode;

/* 
 *  
 *  文件名：InfraredCodeMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:17:33
 */
public interface InfraredCodeMapper {

	/**
	 * 增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:01
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	void addInfraredCode(InfraredCode infraredCode);

	/**
	 * 修改红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:14
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	void updateInfraredCode(InfraredCode infraredCode);

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
	void updateInfraredCodeStatus(@Param("status") String status,
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
	List<InfraredCode> findInfraredCodeByPage(InfraredCode infraredCode);
	int getCountInfraredCode(InfraredCode infraredCode);
	
	/**
	 * 按ID查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:52
	 * 
	 * @param id
	 * @return 返回类型：InfraredCode
	 * @exception throws
	 */
	InfraredCode getInfraredCodeById(String id);

	/**
	 * 按条件查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:04
	 * 
	 * @param infraredCode
	 * @return 返回类型：List<InfraredCode>
	 * @exception throws
	 */
	List<InfraredCode> findInfraredCodeByCond(InfraredCode infraredCode);

	List<InfraredCode> findInfraredCodeByCond2(
			@Param("brandCode") String brandCode, @Param("type") String type,
			@Param("keyNameList") String keyNameList);

	/**
	 * 按ID删除红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:17
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delInfraredCode(String id);

	/**
	 * 
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 下午4:24:28
	 * 
	 * @param ids
	 *            返回类型：void
	 * @exception throws
	 */
	void delInfraredCodeList(List<String> ids);

	void delInfraredCodeByCond(@Param("brandId") String brandId,
			@Param("type") String type, @Param("modelName") String modelName);

	/**
	 * 按集合方式增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:28
	 * 
	 * @param list
	 *            返回类型：void
	 * @exception throws
	 */
	void addInfraredCodeList(List<InfraredCode> list);
}
