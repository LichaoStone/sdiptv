package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.ChannelLog;

/* 
 *  
 *  文件名：ChannelLogMapper.java
 *  创建人：pananz
 *  创建时间：2014-8-9 - 下午12:11:01
 */
public interface ChannelLogMapper {

	void addChannelLog(ChannelLog channelLog);

	void updateRealNumber(String channelId);

	void updateVirtualNumber(@Param("virtualNumber") String virtualNumber,
			@Param("id") String id);

	List<ChannelLog> findChannelLogByPage(ChannelLog channelLog);
	int getCountChannelLog(ChannelLog channelLog);
	
	ChannelLog getChannelLogByChannelId(String channelId);

	void delHot(String id);

	void delHotList(List<String > ids);

}
