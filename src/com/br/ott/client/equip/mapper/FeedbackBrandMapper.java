package com.br.ott.client.equip.mapper;

import java.util.List;

import com.br.ott.client.equip.entity.FeedbackBrand;

/* 
 *  
 *  文件名：FeedbackMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 下午12:00:43
 */
public interface FeedbackBrandMapper {

	/**
	 * 增加反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:23
	 * 
	 * @param feedback
	 *            返回类型：void
	 * @exception throws
	 */
	void addFeedbackBrand(FeedbackBrand feedback);

	/**
	 * 按分页查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:52
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	List<FeedbackBrand> findFeedbackBrandByPage(FeedbackBrand feedback);
	int getCountFeedbackBrand(FeedbackBrand feedback);
	
	/**
	 * 按条件查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:59
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	List<FeedbackBrand> findFeedbackBrandByCond(FeedbackBrand feedback);

	/**
	 * 按ID查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:14
	 * 
	 * @param id
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	FeedbackBrand getFeedbackBrandById(String id);

	/**
	 * 按ID删除反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:22
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delFeedbackBrandById(String id);

	/**
	 * 批量删除
	 * 
	 * 创建人：pananz 创建时间：2015-7-22 - 下午3:48:24
	 * 
	 * @param ids
	 *            返回类型：void
	 * @exception throws
	 */
	void delFeedbackBrandList(List<String> ids);

	/**
	 * 修改反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-7-22 - 下午5:03:42
	 * 
	 * @param feedback
	 *            返回类型：void
	 * @exception throws
	 */
	void updateFeedbackBrand(FeedbackBrand feedback);

}
