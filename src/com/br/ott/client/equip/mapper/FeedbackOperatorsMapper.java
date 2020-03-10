package com.br.ott.client.equip.mapper;

import java.util.List;

import com.br.ott.client.equip.entity.FeedbackOperators;

/* 
 *  
 *  文件名：FeedbackMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 下午12:00:43
 */
public interface FeedbackOperatorsMapper {

	/**
	 * 增加反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:23
	 * 
	 * @param feedback
	 *            返回类型：void
	 * @exception throws
	 */
	void addFeedbackOperators(FeedbackOperators feedback);

	/**
	 * 按分页查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:52
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	List<FeedbackOperators> findFeedbackOperatorsByPage(
			FeedbackOperators feedback);
	int getCountFeedbackOperators(FeedbackOperators feedback);
	
	/**
	 * 按条件查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:59
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	List<FeedbackOperators> findFeedbackOperatorsByCond(
			FeedbackOperators feedback);

	/**
	 * 按ID查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:14
	 * 
	 * @param id
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	FeedbackOperators getFeedbackOperatorsById(String id);

	/**
	 * 按ID删除反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:22
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delFeedbackOperatorsById(String id);

	/**
	 * 批量删除
	 * 
	 * 创建人：pananz 创建时间：2015-7-22 - 下午4:17:32
	 * 
	 * @param ids
	 *            返回类型：void
	 * @exception throws
	 */
	void delFeedbackOperatorsList(List<String> ids);

	/**
	 * 修改反馈运营商信息
	 * 
	 * 创建人：pananz 创建时间：2015-7-22 - 下午4:20:00
	 * 
	 * @param feedbackOperators
	 *            返回类型：void
	 * @exception throws
	 */
	void updateFeedbackOperators(FeedbackOperators feedbackOperators);

}
