package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.Channel;

/* 
 *  
 *  文件名：ChannelMapper.java
 *  创建人：pananz
 *  创建时间：2014-5-16 - 下午5:41:44
 */
public interface ChannelMapper {

	void addChannel(Channel channel);

	void updateChannel(Channel channel);

	List<Channel> findChannelByPage(Channel channel);
	int getCountChannel(Channel channel);
	
	Channel getChannelById(String id);

	List<Channel> findChannelByCond(Channel channel);

	void addChannels(List<Channel> list);

	void updateChannelStatus(@Param("status") String status,
			@Param("id") String id);

	List<Channel> findChannelByName(String name);

	void delChannel(String id);
	
}