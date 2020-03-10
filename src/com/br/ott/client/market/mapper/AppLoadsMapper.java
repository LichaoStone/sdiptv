package com.br.ott.client.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.market.entity.AppLoads;

/**
 * 文件名：AppLoadsMapper.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
public interface AppLoadsMapper {

	void addAppLoads(AppLoads appLoads);

	void delAppLoadsByMarketId(String marketId);

	void delAppLoadsById(String id);

	List<AppLoads> findAppLoadsByPage(AppLoads appLoads);

	int getCountAppLoads(AppLoads appLoads);

	AppLoads getAppLoadsById(String id);

	AppLoads getAppLoadsByMarketId(String marketId);

	List<AppLoads> findAppLoadsByMarketIdAndDate(@Param("marketId") String marketId,
			@Param("recordTime") String recordTime);
}
