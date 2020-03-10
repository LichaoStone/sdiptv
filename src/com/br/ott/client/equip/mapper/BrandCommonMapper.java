package com.br.ott.client.equip.mapper;

import java.util.List;

import com.br.ott.client.equip.entity.BrandCommon;

/* 
 *  
 *  文件名：BrandCommonMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-1 - 下午4:39:54
 */
public interface BrandCommonMapper {

	void addBrandCommon(BrandCommon brandCommon);

	void updateBrandCommon(BrandCommon brandCommon);

	List<BrandCommon> findBrandCommonByCond(BrandCommon brandCommon);

	List<BrandCommon> findBrandCommonByBrandId(String brandId);

	List<BrandCommon> findBrandCommonByPage(BrandCommon brandCommon);
	int getCountBrandCommon(BrandCommon brandCommon);
	
	void deleteBrandCommonByBrandId(String brandId);

	void deleteBrandCommonById(String id);

	void delBrandCommonList(List<String> ids);

	BrandCommon getBrandCommonById(String id);
}
