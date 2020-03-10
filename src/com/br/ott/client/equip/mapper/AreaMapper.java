package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.Area;

/* 
 *  
 *  文件名：AreaMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午3:22:15
 */
public interface AreaMapper {

	/**
	 * 增加区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:10
	 * 
	 * @param area
	 *            返回类型：void
	 * @exception throws
	 */
	void addArea(Area area);

	/**
	 * 修改区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:18
	 * 
	 * @param area
	 *            返回类型：void
	 * @exception throws
	 */
	void updateArea(Area area);

	/**
	 * 修改区域状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:25
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateAreaStatus(@Param("status") String status, @Param("id") String id);

	/**
	 * 按ID查询区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:25:07
	 * 
	 * @param id
	 * @return 返回类型：Area
	 * @exception throws
	 */
	Area getAreaById(String id);

	/**
	 * 按分页查询区域信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:26:32
	 * 
	 * @param area
	 * @return 返回类型：List<Area>
	 * @exception throws
	 */
	List<Area> findAreaByPage(Area area);
	int getCountArea(Area area);
	
	/**
	 * 按条件查询区域信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:26:43
	 * 
	 * @param area
	 * @return 返回类型：List<Area>
	 * @exception throws
	 */
	List<Area> findAreaByCond(Area area);

	/**
	 * 通过区域查询省会城市
	 * 
	 * 创建人：pananz 创建时间：2015-5-26 - 下午5:14:20
	 * 
	 * @param name
	 * @return 返回类型：Area
	 * @exception throws
	 */
	Area getAreaCapitalByName(String name);
}
