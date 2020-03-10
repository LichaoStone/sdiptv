package com.br.ott.client.equip.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.equip.entity.BrandSupport;
import com.br.ott.client.equip.mapper.BrandSupportMapper;

/* 
 *  
 *  文件名：BrandSupportService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-5 - 下午3:27:17
 */
@Service
public class BrandSupportService {

	@Autowired
	private BrandSupportMapper brandSupportMapper;

	/**
	 * 增加支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:25:26
	 * 
	 * @param brandSupport
	 *            返回类型：void
	 * @exception throws
	 */
	public void addBrandSupport(BrandSupport brandSupport) {
		brandSupportMapper.addBrandSupport(brandSupport);
	}

	/**
	 * 修改支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:25:57
	 * 
	 * @param brandSupport
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateBrandSupport(BrandSupport brandSupport) {
		brandSupportMapper.updateBrandSupport(brandSupport);
	}

	/**
	 * 修改支持红外品牌状态
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:01
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateSupportStatus(String status, String id) {
		brandSupportMapper.updateSupportStatus(status, id);
	}

	/**
	 * 删除支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:30
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void delBrandSupportById(String id) {
		brandSupportMapper.delBrandSupportById(id);
	}

	/**
	 * 按分页查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:36
	 * 
	 * @param brandSupport
	 * @return 返回类型：List<BrandSupport>
	 * @exception throws
	 */
	public List<BrandSupport> findBrandSupportByPage(BrandSupport brandSupport) {
		int totalResult=brandSupportMapper.getCountBrandSupport(brandSupport);
		brandSupport.setTotalResult(totalResult);
		return brandSupportMapper.findBrandSupportByPage(brandSupport);
	}

	/**
	 * 按条件查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:45
	 * 
	 * @param brandSupport
	 * @return 返回类型：List<BrandSupport>
	 * @exception throws
	 */
	public List<BrandSupport> findBrandSupportByCond(BrandSupport brandSupport) {
		return brandSupportMapper.findBrandSupportByCond(brandSupport);
	}

	/**
	 * 按ID查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:54
	 * 
	 * @param id
	 * @return 返回类型：BrandSupport
	 * @exception throws
	 */
	public BrandSupport getBrandSupportById(String id) {
		return brandSupportMapper.getBrandSupportById(id);
	}

	public boolean checkBrandSupportByName(String name) {
		BrandSupport brandSupport = new BrandSupport();
		brandSupport.setName(name);
		List<BrandSupport> list = findBrandSupportByCond(brandSupport);
		if (CollectionUtils.isNotEmpty(list)) {
			list = null;
			return false;
		}
		return true;
	}
}
