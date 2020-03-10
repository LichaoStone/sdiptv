package com.br.ott.client.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ott.client.market.entity.AppMarket;
import com.br.ott.client.market.mapper.AppLoadsMapper;
import com.br.ott.client.market.mapper.AppMarketMapper;

/**
 * 文件名：AppMarketService.java
 *  版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-9-1
 */
@Service
public class AppMarketService {

	@Autowired
	private AppMarketMapper appMarketMapper;
	
	@Autowired
	private AppLoadsMapper appLoadsMapper;

	/**
	 * 增加登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket 返回类型：void
	 * @exception throws
	 */
	public void addAppMarket(AppMarket appMarket){
		appMarketMapper.addAppMarket(appMarket);
	}

	/**
	 * 修改登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket 返回类型：void
	 * @exception throws
	 */
	public void updateAppMarket(AppMarket appMarket){
		appMarketMapper.updateAppMarket(appMarket);
	}

	public void updateAppMarketLoads(AppMarket appMarket){
		appMarketMapper.updateAppMarketLoads(appMarket);
	}
	
	/**
	 * 按ID查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	@Transactional
	public void delAppMarketById(String id){
		appMarketMapper.delAppMarketById(id);
		appLoadsMapper.delAppLoadsByMarketId(id);
	}

	/**
	 * 按ID查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param id
	 * @param @return 返回类型：AppMarket
	 * @exception throws
	 */
	public AppMarket getAppMarketById(String id){
		return appMarketMapper.getAppMarketById(id);
	}

	/**
	 * 按分页查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：List<AppMarket>
	 * @exception throws
	 */
	public List<AppMarket> findAppMarketByPage(AppMarket appMarket){
		int totalResult= appMarketMapper.getCountAppMarket(appMarket);
		appMarket.setTotalResult(totalResult);
		return appMarketMapper.findAppMarketByPage(appMarket);
	}

	/**
	 * 统计登陆应用市场信息总记录数
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：int
	 * @exception throws
	 */
	public int getCountAppMarket(AppMarket appMarket){
		return appMarketMapper.getCountAppMarket(appMarket);
	}

	/**
	 * 按条件查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：List<AppMarket>
	 * @exception throws
	 */
	public List<AppMarket> findAppMarketByCond(AppMarket appMarket){
		return appMarketMapper.findAppMarketByCond(appMarket);
	}
}


