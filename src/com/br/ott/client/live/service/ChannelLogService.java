package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.ChannelLog;
import com.br.ott.client.live.mapper.ChannelLogMapper;

/* 
 *  
 *  文件名：ChannelLogService.java
 *  创建人：pananz
 *  创建时间：2014-9-23 - 下午1:59:14
 */
@Service
public class ChannelLogService {

	@Autowired
	private ChannelLogMapper channelLogMapper;

	public void updateVirtualNumber(String virtualNumber, String id) {
		channelLogMapper.updateVirtualNumber(virtualNumber, id);
	}

	public List<ChannelLog> findChannelLogByPage(ChannelLog channelLog) {
		int totalResult = channelLogMapper.getCountChannelLog(channelLog);
		channelLog.setTotalResult(totalResult);
		return channelLogMapper.findChannelLogByPage(channelLog);
	}

	public ChannelLog getChannelLogByChannelId(String channelId) {
		return channelLogMapper.getChannelLogByChannelId(channelId);
	}

	public void delHot(String id) {
		channelLogMapper.delHot(id);
	}

	public void delHotList(List<String> ids) {
		channelLogMapper.delHotList(ids);
	}
}
