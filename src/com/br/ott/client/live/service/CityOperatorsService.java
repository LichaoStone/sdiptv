package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.mapper.CityOperatorsMapper;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;

/* 
 *  
 *  文件名：CityOperatorsService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-12 - 下午2:44:09
 */
@Service
public class CityOperatorsService {

	@Autowired
	private CityOperatorsMapper cityOperatorsMapper;
	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;

	public void addCityOperators(CityOperators cityOperators) {
		cityOperatorsMapper.addCityOperators(cityOperators);
	}

	public void updateCityOperators(CityOperators cityOperators) {
		cityOperatorsMapper.updateCityOperators(cityOperators);
	}

	public List<CityOperators> findCityOperatorsByPage(
			CityOperators cityOperators) {
		int totalResult = cityOperatorsMapper
				.getCountCityOperators(cityOperators);
		cityOperators.setTotalResult(totalResult);
		return cityOperatorsMapper.findCityOperatorsByPage(cityOperators);
	}

	public List<CityOperators> findCityOperatorsByCond(
			CityOperators cityOperators) {
		return cityOperatorsMapper.findCityOperatorsByCond(cityOperators);
	}

	public CityOperators getCityOperatorsById(String id) {
		return cityOperatorsMapper.getCityOperatorsById(id);
	}

	@Transactional
	public void delCityOperatorsById(String id) {
		cityOperatorsMapper.delCityOperatorsById(id);
		operatorsChannelMapper.delOperatorsChannelByOperators(id);
	}

	@Transactional
	public void delCityOperatorsList(List<String> ids) {
		for (String id : ids) {
			cityOperatorsMapper.delCityOperatorsById(id);
			operatorsChannelMapper.delOperatorsChannelByOperators(id);
		}
	}

	public void updateCityOperatorsStatus(String status, String id) {
		cityOperatorsMapper.updateCityOperatorsStatus(status, id);
	}

	public List<CityOperators> findCityOperatorsByArea(String areaName) {
		CityOperators operators = new CityOperators();
		operators.setStatus("1");
		operators.setAreaName(areaName);
		return findCityOperatorsByCond(operators);
	}

	public void reloadOperators() {
		CityOperators co = new CityOperators();
		co.setStatus("1");
		List<CityOperators> list = cityOperatorsMapper
				.findCityOperatorsByCond(co);
		OperatorsCache.reload(list);
	}
}
