package com.br.ott.client.equip.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.BrandCommon;
import com.br.ott.client.equip.mapper.BrandCommonMapper;
import com.br.ott.client.equip.mapper.BrandMapper;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：BrandService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:42:15
 */
@Service
public class BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private BrandCommonMapper brandCommonMapper;

	/**
	 * 按分页查询品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:31
	 * 
	 * @param brand
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	public List<Brand> findBrandByPage(Brand brand) {
		int totalResult=brandMapper.getCountBrand(brand);
		brand.setTotalResult(totalResult);
		return brandMapper.findBrandByPage(brand);
	}

	/**
	 * 按ID查詢品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:39
	 * 
	 * @param id
	 * @return 返回类型：Brand
	 * @exception throws
	 */
	public Brand getBrandById(String id) {
		return brandMapper.getBrandById(id);
	}

	/**
	 * 增加品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:42:53
	 * 
	 * @param brand
	 *            返回类型：void
	 * @exception throws
	 */
	public void addBrand(Brand brand) {
		brandMapper.addBrand(brand);

		if (StringUtil.isNotEmpty(brand.getRecType())) {
			String[] arr = brand.getRecType().split(",");
			brandCommonMapper.deleteBrandCommonByBrandId(brand.getId());
			for (String typeId : arr) {
				BrandCommon cType = new BrandCommon(brand.getId(), typeId);
				brandCommonMapper.addBrandCommon(cType);
			}
		}
	}

	/**
	 * 修改品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:20
	 * 
	 * @param brand
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateBrand(Brand brand, String oldType) {
		brandMapper.updateBrand(brand);
		if (!oldType.equals(brand.getRecType())) {
			if (StringUtil.isNotEmpty(brand.getRecType())) {
				String[] arr = brand.getRecType().split(",");
				brandCommonMapper.deleteBrandCommonByBrandId(brand.getId());
				for (String typeId : arr) {
					BrandCommon cType = new BrandCommon(brand.getId(), typeId);
					brandCommonMapper.addBrandCommon(cType);
				}
			}
		}
	}

	/**
	 * 按条件查询品牌信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:28
	 * 
	 * @param brand
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	public List<Brand> findBrandByCond(Brand brand) {
		return brandMapper.findBrandByCond(brand);
	}

	/**
	 * 按名称查询品牌
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:43:44
	 * 
	 * @param name
	 * @return 返回类型：List<Brand>
	 * @exception throws
	 */
	public boolean checkBrandByName(String name) {
		List<Brand> list = brandMapper.findBrandByName(name);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}
}
