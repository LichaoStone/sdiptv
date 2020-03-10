package com.br.ott.client.equip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.SessionUtil;
import com.br.ott.client.equip.entity.FeedbackOperators;
import com.br.ott.client.equip.mapper.FeedbackOperatorsMapper;

/* 
 *  
 *  文件名：FeedbackService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 下午12:05:01
 */
@Service
public class FeedbackOperatorsService {

	@Autowired
	private FeedbackOperatorsMapper feedbackOperatorsMapper;

	/**
	 * 增加反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:23
	 * 
	 * @param feedback
	 *            返回类型：void
	 * @exception throws
	 */
	public void addFeedbackOperators(FeedbackOperators feedback) {
		feedbackOperatorsMapper.addFeedbackOperators(feedback);
	}

	/**
	 * 按分页查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:52
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	public List<FeedbackOperators> findFeedbackOperatorsByPage(
			FeedbackOperators feedback) {
		int totalResult=feedbackOperatorsMapper.getCountFeedbackOperators(feedback);
		feedback.setTotalResult(totalResult);
		return feedbackOperatorsMapper.findFeedbackOperatorsByPage(feedback);
	}

	/**
	 * 按条件查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:59
	 * 
	 * @param feedback
	 * @return 返回类型：List<Feedback>
	 * @exception throws
	 */
	public List<FeedbackOperators> findFeedbackOperatorsByCond(
			FeedbackOperators feedback) {
		return feedbackOperatorsMapper.findFeedbackOperatorsByCond(feedback);
	}

	/**
	 * 按ID查询反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:14
	 * 
	 * @param id
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	public FeedbackOperators getFeedbackOperatorsById(String id) {
		return feedbackOperatorsMapper.getFeedbackOperatorsById(id);
	}

	/**
	 * 按ID删除反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:04:22
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void delFeedbackOperatorsById(String id) {
		feedbackOperatorsMapper.delFeedbackOperatorsById(id);
	}

	public void delFeedbackOperatorsList(List<String> ids) {
		feedbackOperatorsMapper.delFeedbackOperatorsList(ids);
	}

	public void updateFeedbackOperators(String remark, String status, String id) {
		FeedbackOperators feedback = new FeedbackOperators();
		feedback.setStatus(status);
		feedback.setRemark(remark);
		feedback.setTransactor(SessionUtil.getLoginName());
		feedback.setId(id);
		feedbackOperatorsMapper.updateFeedbackOperators(feedback);
	}
}
