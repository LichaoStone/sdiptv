package com.br.ott.client.equip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.equip.entity.BrandCommon;
import com.br.ott.client.equip.mapper.BrandCommonMapper;

/* 
 *  
 *  文件名：BrandCommonService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-2 - 上午10:20:44
 */
@Service
public class BrandCommonService {

	@Autowired
	private BrandCommonMapper brandCommonMapper;

	public void addBrandCommon(BrandCommon brandCommon) {
		brandCommonMapper.addBrandCommon(brandCommon);
	}

	public void updateBrandCommon(BrandCommon brandCommon) {
		brandCommonMapper.updateBrandCommon(brandCommon);
	}

	public List<BrandCommon> findBrandCommonByCond(BrandCommon brandCommon) {
		return brandCommonMapper.findBrandCommonByCond(brandCommon);
	}

	public List<BrandCommon> findBrandCommonByBrandId(String brandId) {
		return brandCommonMapper.findBrandCommonByBrandId(brandId);
	}

	public List<BrandCommon> findBrandCommonByPage(BrandCommon brandCommon) {
		int totalResult=brandCommonMapper.getCountBrandCommon(brandCommon);
		brandCommon.setTotalResult(totalResult);
		return brandCommonMapper.findBrandCommonByPage(brandCommon);
	}

	public void deleteBrandCommonById(String id) {
		brandCommonMapper.deleteBrandCommonById(id);
	}

	public void delBrandCommonList(List<String> ids) {
		brandCommonMapper.delBrandCommonList(ids);
	}

	public BrandCommon getBrandCommonById(String id) {
		return brandCommonMapper.getBrandCommonById(id);
	}

}
