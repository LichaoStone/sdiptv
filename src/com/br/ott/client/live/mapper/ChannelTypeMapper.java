package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.ChannelType;

/* 
 *  
 *  文件名：ChannelTypeMapper.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午6:21:44
 */
public interface ChannelTypeMapper {

	void addChannelType(ChannelType channelType);

	void updateChannelType(ChannelType channelType);

	List<ChannelType> findChannelTypeByPage(ChannelType channelType);
	int getCountChannelType(ChannelType channelType);
	
	List<ChannelType> findChannelTypeByCond(ChannelType channelType);

	ChannelType getChannelTypeById(String id);

	void updateChannelTypeStatus(@Param("status") String status,
			@Param("id") String id);

	List<ChannelType> findChannelTypeByTypeAndName(@Param("type") String type,
			@Param("name") String name);

}
