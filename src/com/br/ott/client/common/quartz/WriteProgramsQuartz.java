package com.br.ott.client.common.quartz;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.br.ott.Global;
import com.br.ott.client.common.AbnormalCache;
import com.br.ott.client.common.entity.AbnormalInfo;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelRecover;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.ChannelRecoverMapper;
import com.br.ott.client.live.mapper.HandleAssetsMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.client.live.service.HandleAssetsService;
import com.br.ott.client.live.service.SearchLogService;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.RandomUtil;
import com.br.ott.common.util.SendEmailUtils;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.spring.SpringContextHolder;

/* 
 *  
 *  文件名：WriteProgramsQuartz.java
 *  创建人：pananz
 *  创建时间：2014-8-12 - 上午11:46:16
 */
public class WriteProgramsQuartz {
	private static final Logger logger = Logger
			.getLogger(WriteProgramsQuartz.class);

	/**
	 * 校验每天是否有节目单的数据
	 * 
	 * 创建人：pananz 创建时间：2015-8-3
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public static void checkProgramsOfFile() {
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		Programs programs = new Programs();
		try {
			int n = RandomUtil.getRandomNumber(1);
			Thread.sleep(n * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date date = DateUtil.addDay(new Date(), 1);
		programs.setPlayTime(DateUtil.toString(date, "yyyy-MM-dd"));
		List<Programs> list = programsMapper.findProgramsByCond(programs);
		if (CollectionUtils.isEmpty(list)) {
			sendEmail(date);
		}
		File file = new File(Global.LIVE_DATA + "/" + DateUtil.getCurrentDate());
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				sendEmail(DateUtil.getCurrentDate(), "当天不存在同步的节目单文件");
			}
		} else {
			sendEmail(DateUtil.getCurrentDate(), "当天不存在同步的节目单文件夹");
		}
	}

	private static void sendEmail(Date date) {
		if (StringUtil.isNotEmpty(Global.RECEIVE_EMAIL_ADRRESS)) {
			String[] address = Global.RECEIVE_EMAIL_ADRRESS.split(",");
			for (String to : address) {
				logger.error("无节目单邮件通知管理员:" + to);
				try {
					SendEmailUtils.sendHtml(to,
							DateUtil.toString(date, "yyyy-MM-dd") + "无节目单",
							DateUtil.toString(date, "yyyy-MM-dd") + "的节目单无数据");
				} catch (OTTException e) {
					e.printStackTrace();
					logger.error("邮件通知" + to + "出错,节目单: " + "于"
							+ DateUtil.toString(date, "yyyy-MM-dd") + "的无内容");
				}
			}
		}
	}

	private static void sendEmail(String date, String tip) {
		if (StringUtil.isNotEmpty(Global.RECEIVE_EMAIL_ADRRESS)) {
			String[] address = Global.RECEIVE_EMAIL_ADRRESS.split(",");
			for (String to : address) {
				logger.error("邮件通知管理员:" + to + ",原因：" + tip);
				try {
					SendEmailUtils.sendHtml(to, date + "读取节目单出错", date
							+ "的节目单读取出错,原因：" + tip);
				} catch (OTTException e) {
					e.printStackTrace();
					logger.error("邮件通知" + to + "读取节目单出错,节目单: " + "于" + date
							+ "的读取出错");
				}
			}
		}
	}

	/**
	 * 删除前7天的数据
	 * 
	 * 创建人：pananz 创建时间：2015-5-5 - 下午4:39:52 返回类型：void
	 * 
	 * @exception throws
	 */
	public void reloadProgram() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		Channel c = new Channel();
		//删除7天前ott_programs数据
		c.setStatus("1");
		List<Channel> chList = channelMapper.findChannelByCond(c);
		if(CollectionUtils.isNotEmpty(chList)){
			Iterator<Channel> itor = chList.iterator();
			Channel channel = null;
			while(itor.hasNext()){
				channel = itor.next();
				programsMapper.delProgramBy7Date(channel.getId(), DateUtil.getCurrentDate());
			}
			chList = null;
		}
		
		// 扫描异常频道节目单信息
		ChannelRecoverMapper channelRecoverMapper = SpringContextHolder
				.getBean(ChannelRecoverMapper.class);
		List<ChannelRecover> crs = channelRecoverMapper
				.findChannelRecover(new ChannelRecover());
		Date overDate = DateUtil.addDay(new Date(), -7);
		List<String> ids = new ArrayList<String>();
		for (ChannelRecover cr : crs) {
			if (cr.getRecoverTime().before(overDate)) {
				ids.add(cr.getId());
			}
		}
		if (CollectionUtils.isNotEmpty(ids)) {
			channelRecoverMapper.delChannelRecoverList(ids);
		}
		ids = null;
		
		c.setStatus("2");
		List<Channel> cList = channelMapper.findChannelByCond(c);
		Programs programs = new Programs();
		programs.setStatus("2");
		List<Programs> pList = programsMapper.findProgramsByCond(programs);

		ChannelRecover channelRecover = new ChannelRecover();
		channelRecover.setQueryTime("now");
		List<ChannelRecover> crList = channelRecoverMapper
				.findChannelRecover(channelRecover);

		List<AbnormalInfo> aList = AbnormalCache.findAbnormalInfo(cList, pList,
				crList);
		AbnormalCache.reloadAbnormalInfo(aList);
		cList = null;
		pList = null;
		crList = null;
		aList = null;
	}

	public static List<Date> getDateList() {
		Date date = new Date();
		List<Date> list = new ArrayList<Date>();
		for (int i = 0; i <= 2; i++) {
			list.add(DateUtil.addDay(date, i));
		}
		return list;
	}
	/**
	 * 同步获取节目单
	 * 
	 * 创建人：pananz 创建时间：2017-2-13
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public void getEpgOfProgram() {
		ChannelMapper channelMapper = SpringContextHolder.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder.getBean(ProgramsMapper.class);
		HandleAssetsMapper handleAssetsMapper = SpringContextHolder.getBean(HandleAssetsMapper.class);
		Channel channel = new Channel();
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> list = channelMapper.findChannelByCond(channel);
		List<Date> dList = getDateList();   //获取未来三天
		List<Map<String, Object>> result = null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		for (Channel c : list) {
			for (Date date : dList) {
				String playDate = DateUtil.parseDate(date, "yyyyMMdd");
				paramsMap.put("channelId", c.getId());
				paramsMap.put("day", playDate);
				if(CollectionUtils.isEmpty(result = handleAssetsMapper.queryProgramsByIdAndDate(paramsMap))){
					continue;
				}
				programsMapper.delProgramsByCidAndDate(c.getId(), DateUtil.parseDate(date, "yyyy-MM-dd"));
				paramsMap.put("channelList", result);
				programsMapper.addProgramsByMap(paramsMap);
				result = null;
			}
		}
	}
	
	/**
	 * 每2小时处理一次直播节目单信息
	 * 
	 * 
	 * 创建人：lizhenghg 创建时间：2017-01-18
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public void doReadTxt() {
		HandleAssetsService handleAssetsService = SpringContextHolder.getBean(HandleAssetsService.class);
		handleAssetsService.readTxtForZB();
	}
	
	/**
	 * 删除热门搜索，删除条件：15天内真实点击量少于10的搜索
	 * @author lizhenghg  2017-08-05
	 */
	public void delSearchLog(){
		SearchLogService searchLogService = SpringContextHolder.getBean(SearchLogService.class);
		searchLogService.delSearchLogForTerminal();
	}
}
