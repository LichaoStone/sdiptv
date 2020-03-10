package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.OperatorsChannel;

/* 
 *  
 *  文件名：OperatorsChannelMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-5-28 - 下午12:20:25
 */
public interface OperatorsChannelMapper {

	/**
	 * 增加运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:42:27
	 * 
	 * @param operatorsChannel
	 *            返回类型：void
	 * @exception throws
	 */
	void addOperatorsChannel(OperatorsChannel operatorsChannel);

	/**
	 * 修改运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:38:10
	 * 
	 * @param operatorsChannel
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperatorsChannel(OperatorsChannel operatorsChannel);

	/**
	 * 修改运营商频道状态
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:42:51
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateOperatorsChannelStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 按ID查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:05
	 * 
	 * @param id
	 * @return 返回类型：OperatorsChannel
	 * @exception throws
	 */
	OperatorsChannel getOperatorsChannelById(String id);

	/**
	 * 按分页查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:14
	 * 
	 * @param operatorsChannel
	 * @return 返回类型：List<OperatorsChannel>
	 * @exception throws
	 */
	List<OperatorsChannel> findOperatorsChannelByPage(
			OperatorsChannel operatorsChannel);

	int getCountOperatorsChannel(OperatorsChannel operatorsChannel);

	/**
	 * 按条件查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:39
	 * 
	 * @param operatorsChannel
	 * @return 返回类型：List<OperatorsChannel>
	 * @exception throws
	 */
	List<OperatorsChannel> findOperatorsChannelByCond(
			OperatorsChannel operatorsChannel);

	/**
	 * 按ID删除运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:47
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperatorsChannelById(String id);

	/**
	 * 
	 * 
	 * 创建人：pananz 创建时间：2015-6-9 - 下午2:05:11
	 * 
	 * @param list
	 *            返回类型：void
	 * @exception throws
	 */
	void delOperatorsChannelList(List<String> list);

	void delOperatorsChannelByOperators(String operators);

	/**
	 * 查询某一运营商下的频道及其当前直播信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-17
	 * 
	 * @param @param oc
	 * @param @return 返回类型：List<OperatorsChannel>
	 * @exception throws
	 */
	List<OperatorsChannel> findLiveChannelByPage(OperatorsChannel oc);

	/**
	 * 统计查询某一运营商下的频道总数量
	 * 
	 * 创建人：pananz 创建时间：2015-8-17
	 * 
	 * @param @param oc
	 * @param @return 返回类型：int
	 * @exception throws
	 */
	int getCountLiveChannel(OperatorsChannel oc);

	List<OperatorsChannel> findOperatorsChannelByMoreName(
			@Param("operators") String operators,
			@Param("channelName") String channelName);
}
