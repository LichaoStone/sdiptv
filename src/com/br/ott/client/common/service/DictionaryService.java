/*
 * @# DictionaryService.java Aug 6, 2012 3:36:22 PM
 * 
 * Copyright (C) 2011 - 2012 博瑞立方
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.client.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.mapper.DictionaryMapper;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.jackson.TinyTreeBean;
import com.br.ott.common.jackson.TinyTreeUtils;
import com.br.ott.common.util.Constants.DicType;

/*
 * @author pananz
 * TODO
 */
@Component
public class DictionaryService {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	private static final String ROOT_GOOS_TYPE_ID = "0";

	/**
	 * 查询某一类型字典数据
	 * 
	 * @param dicType
	 *            数据类型
	 * @return
	 */
	public List<Dictionary> findDictionaryByDicType() {
		return dictionaryMapper.findDictionaryByDicType();
	}

	/**
	 * 查询类型（按树形结构展现)
	 * 
	 * @return
	 */
	public String buildTreeDict() {
		// List<Dictionary> dicts = findDictionaryByDicType();
		List<Dictionary> dicts = DictCache.getDictList(DicType.TOP_TYPE);
		return TinyTreeUtils.toJson(buildTinyTreeBean(dicts));
	}

	private List<TinyTreeBean> buildTinyTreeBean(List<Dictionary> dicts) {
		// TODO 此方法后续需优化(当前只能用于查询数据量不大的情况)
		TinyTreeBean root = new TinyTreeBean(ROOT_GOOS_TYPE_ID, null);
		Map<String, TinyTreeBean> ptTreeMap = new HashMap<String, TinyTreeBean>();
		ptTreeMap.put(ROOT_GOOS_TYPE_ID, root);
		if (CollectionUtils.isEmpty(dicts)) {
			root.setName("暂无类型");
			return ptTreeMap.get(ROOT_GOOS_TYPE_ID).getChildren();
		}
		for (Dictionary dict : dicts) {
			TinyTreeBean node = new TinyTreeBean(dict.getDicValue(),
					dict.getDicName());
			ptTreeMap.put(dict.getDicValue(), node);
		}
		for (Dictionary pt : dicts) {
			TinyTreeBean parent = ptTreeMap.get(pt.getDicType());
			if (null == parent) {
				continue;
			}
			parent.addChild(ptTreeMap.get(pt.getDicValue()));
		}
		return ptTreeMap.get(ROOT_GOOS_TYPE_ID).getChildren();
	}
}
