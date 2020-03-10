package com.br.ott.client.equip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.equip.entity.BrandSupport;

/* 
 *  
 *  文件名：BrandSupportMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-5 - 下午3:22:03
 */
public interface BrandSupportMapper {

	/**
	 * 增加支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:25:26
	 * 
	 * @param brandSupport
	 *            返回类型：void
	 * @exception throws
	 */
	void addBrandSupport(BrandSupport brandSupport);

	/**
	 * 修改支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:25:57
	 * 
	 * @param brandSupport
	 *            返回类型：void
	 * @exception throws
	 */
	void updateBrandSupport(BrandSupport brandSupport);

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
	void updateSupportStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 删除支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:30
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delBrandSupportById(String id);

	/**
	 * 按分页查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:36
	 * 
	 * @param brandSupport
	 * @return 返回类型：List<BrandSupport>
	 * @exception throws
	 */
	List<BrandSupport> findBrandSupportByPage(BrandSupport brandSupport);
	int getCountBrandSupport(BrandSupport brandSupport);
	
	/**
	 * 按条件查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:45
	 * 
	 * @param brandSupport
	 * @return 返回类型：List<BrandSupport>
	 * @exception throws
	 */
	List<BrandSupport> findBrandSupportByCond(BrandSupport brandSupport);

	/**
	 * 按ID查询支持红外品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-5 - 下午3:26:54
	 * 
	 * @param id
	 * @return 返回类型：BrandSupport
	 * @exception throws
	 */
	BrandSupport getBrandSupportById(String id);
}
