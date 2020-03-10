package com.br.ott.client.market.mapper;

import java.util.List;

import com.br.ott.client.market.entity.AppMarket;

/**
 * 文件名：AppMarketMapper.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-9-1
 */
public interface AppMarketMapper {

	/**
	 * 增加登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket 返回类型：void
	 * @exception throws
	 */
	void addAppMarket(AppMarket appMarket);

	/**
	 * 修改登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket 返回类型：void
	 * @exception throws
	 */
	void updateAppMarket(AppMarket appMarket);

	void updateAppMarketLoads(AppMarket appMarket);

	/**
	 * 按ID查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void delAppMarketById(String id);

	/**
	 * 按ID查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param id
	 * @param @return 返回类型：AppMarket
	 * @exception throws
	 */
	AppMarket getAppMarketById(String id);

	/**
	 * 按分页查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：List<AppMarket>
	 * @exception throws
	 */
	List<AppMarket> findAppMarketByPage(AppMarket appMarket);

	/**
	 * 统计登陆应用市场信息总记录数
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：int
	 * @exception throws
	 */
	int getCountAppMarket(AppMarket appMarket);

	/**
	 * 按条件查询登陆应用市场信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-1
	 * 
	 * @param @param appMarket
	 * @param @return 返回类型：List<AppMarket>
	 * @exception throws
	 */
	List<AppMarket> findAppMarketByCond(AppMarket appMarket);

}
