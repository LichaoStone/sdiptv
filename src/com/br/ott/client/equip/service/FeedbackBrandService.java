package com.br.ott.client.equip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.SessionUtil;
import com.br.ott.client.equip.entity.FeedbackBrand;
import com.br.ott.client.equip.mapper.FeedbackBrandMapper;

/* 
 *  
 *  文件名：FeedbackService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-11 - 下午12:05:01
 */
@Service
public class FeedbackBrandService {

	@Autowired
	private FeedbackBrandMapper feedbackMapper;

	/**
	 * 增加反馈信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午12:03:23
	 * 
	 * @param feedback
	 *            返回类型：void
	 * @exception throws
	 */
	public void addFeedbackBrand(FeedbackBrand feedback) {
		feedbackMapper.addFeedbackBrand(feedback);
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
	public List<FeedbackBrand> findFeedbackBrandByPage(FeedbackBrand feedback) {
		int totalResult=feedbackMapper.getCountFeedbackBrand(feedback);
		feedback.setTotalResult(totalResult);
		return feedbackMapper.findFeedbackBrandByPage(feedback);
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
	public List<FeedbackBrand> findFeedbackBrandByCond(FeedbackBrand feedback) {
		return feedbackMapper.findFeedbackBrandByCond(feedback);
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
	public FeedbackBrand getFeedbackBrandById(String id) {
		return feedbackMapper.getFeedbackBrandById(id);
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
	public void delFeedbackBrandById(String id) {
		feedbackMapper.delFeedbackBrandById(id);
	}

	public void delFeedbackBrandList(List<String> ids) {
		feedbackMapper.delFeedbackBrandList(ids);
	}

	public void updateFeedbackBrand(String remark, String status, String id) {
		FeedbackBrand feedback = new FeedbackBrand();
		feedback.setStatus(status);
		feedback.setRemark(remark);
		feedback.setTransactor(SessionUtil.getLoginName());
		feedback.setId(id);
		feedbackMapper.updateFeedbackBrand(feedback);
	}

}
