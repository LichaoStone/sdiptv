package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.Brand;

/* 
 *  
 *  文件名：BrandMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午10:52:23
 */
public interface BrandMapper {

	/**
	 * 按分页查询品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:31
	 * 
	 * @param brand
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	List<Brand> findBrandByPage(Brand brand);
	int getCountBrand(Brand brand);
	
	/**
	 * 按ID查詢品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:39
	 * 
	 * @param id
	 * @return 返回类型：Brand
	 * @exception throws
	 */
	Brand getBrandById(@Param("id") String id);

	/**
	 * 增加品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:53
	 * 
	 * @param brand
	 *            返回类型：void
	 * @exception throws
	 */
	void addBrand(Brand brand);

	/**
	 * 修改品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:20
	 * 
	 * @param brand
	 *            返回类型：void
	 * @exception throws
	 */
	void updateBrand(Brand brand);

	/**
	 * 按条件查询品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:28
	 * 
	 * @param brand
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	List<Brand> findBrandByCond(Brand brand);

	/**
	 * 按名称查询品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:44
	 * 
	 * @param name
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	List<Brand> findBrandByName(@Param("name") String name);
}
