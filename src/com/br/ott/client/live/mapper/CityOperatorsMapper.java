package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.CityOperators;

/* 
 *  
 *  文件名：CityOperatorsMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-6-12 - 下午2:34:39
 */
public interface CityOperatorsMapper {

	void addCityOperators(CityOperators cityOperators);

	void updateCityOperators(CityOperators cityOperators);

	List<CityOperators> findCityOperatorsByPage(CityOperators cityOperators);
	int getCountCityOperators(CityOperators cityOperators);
	
	List<CityOperators> findCityOperatorsByCond(CityOperators cityOperators);

	CityOperators getCityOperatorsById(String id);

	void delCityOperatorsById(String id);

	void updateCityOperatorsStatus(@Param("status") String status,
			@Param("id") String id);

}
