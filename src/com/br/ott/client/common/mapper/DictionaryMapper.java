/*
 * @# DictionaryMapper.java Aug 6, 2012 3:28:09 PM
 * 
 * Copyright (C) 2011 - 2012 博瑞立方
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.client.common.mapper;

import java.util.List;

import com.br.ott.client.dict.entity.Dictionary;

/*
 * @author pananz
 * TODO
 */
public interface DictionaryMapper {

	/**
	 * 查询某一类型字典数据
	 * @param dicType
	 * @return
	 */
	List<Dictionary> findDictionaryByDicType();

}
