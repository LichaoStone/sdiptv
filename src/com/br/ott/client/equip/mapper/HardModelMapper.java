package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.HardModel;

/* 
 *  
 *  文件名：ModelMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午10:59:11
 */
public interface HardModelMapper {

	/**
	 * 增加型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:18
	 * 
	 * @param model
	 *            返回类型：void
	 * @exception throws
	 */
	void addHardModel(HardModel model);

	/**
	 * 修改型号信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:28
	 * 
	 * @param model
	 *            返回类型：void
	 * @exception throws
	 */
	void updateHardModel(HardModel model);

	/**
	 * 按ID查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:36
	 * 
	 * @param id
	 * @return 返回类型：Model
	 * @exception throws
	 */
	HardModel getHardModelById(String id);

	/**
	 * 按分页查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:46
	 * 
	 * @param model
	 * @return 返回类型：List<Model>
	 * @exception throws
	 */
	List<HardModel> findHardModelByPage(HardModel model);
	int getCountHardModel(HardModel model);
	
	/**
	 * 按条件查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:59
	 * 
	 * @param model
	 * @return 返回类型：List<Model>
	 * @exception throws
	 */
	List<HardModel> findHardModelByCond(HardModel model);

	List<HardModel> findHardModelByType(String machType);

	List<HardModel> findRecHardModelByType(String machType);

	/**
	 * 修改型号状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:48:08
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateModelStatus(@Param("status") String status,
			@Param("id") String id);

	void delHardModelById(String id);

}
