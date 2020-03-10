package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.AbnormalCache;
import com.br.ott.client.common.entity.AbnormalInfo;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelRecover;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.ChannelRecoverMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;

/* 
 *  
 *  文件名：ChannelRecoverService.java
 *  创建人：pananz
 *  创建时间：2014-9-23 - 下午1:59:14
 */
@Service
public class ChannelRecoverService {

	@Autowired
	private ChannelRecoverMapper channelRecoverMapper;

	@Autowired
	private ChannelMapper channelMapper;
	
	@Autowired
	private ProgramsMapper programsMapper;
	
	public List<ChannelRecover> findChannelRecoverByPage(ChannelRecover channelRecover) {
		int totalResult = channelRecoverMapper.getCountChannelRecover(channelRecover);
		channelRecover.setTotalResult(totalResult);
		return channelRecoverMapper.findChannelRecoverByPage(channelRecover);
	}
	
	public void reloadAbnormalInfo(){
		Channel channel = new Channel();
		channel.setStatus("2");
		List<Channel> cList = channelMapper.findChannelByCond(channel);

		Programs programs = new Programs();
		programs.setStatus("2");
		List<Programs> pList = programsMapper.findProgramsByCond(programs);

		ChannelRecover channelRecover = new ChannelRecover();
		channelRecover.setQueryTime("now");
		List<ChannelRecover> crList = channelRecoverMapper
				.findChannelRecover(channelRecover);

		List<AbnormalInfo> aList = AbnormalCache.findAbnormalInfo(cList,
				pList, crList);
		AbnormalCache.reloadAbnormalInfo(aList);
		aList = null;
		crList = null;
		pList = null;
		cList = null;
	}
}
