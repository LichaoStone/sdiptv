package com.br.ott.client.live.mapper;

import java.util.List;

import com.br.ott.client.live.entity.ChannelRecover;

/* 
 *  
 *  文件名：ChannelRecoverMapper.java
 *  创建人：pananz
 *  创建时间：2014-8-9 - 下午12:11:01
 */
public interface ChannelRecoverMapper {

	void addChannelRecover(ChannelRecover channelRecover);

	void updateChannelRecover(ChannelRecover channelRecover);

	List<ChannelRecover> findChannelRecoverByPage(ChannelRecover channelRecover);

	int getCountChannelRecover(ChannelRecover channelRecover);

	List<ChannelRecover> findChannelRecover(ChannelRecover channelRecover);

	ChannelRecover getChannelRecoverByCid(String channelId);

	void delChannelRecoverList(List<String> id);

}
