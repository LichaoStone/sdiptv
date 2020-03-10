package com.br.ott.client.common.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.ott.Global;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.CTypeMapper;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.SSOUtil;
import com.br.ott.common.util.SecondsUtils;
import com.br.ott.common.util.SendEmailUtils;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.spring.SpringContextHolder;

/* 
 *  
 *  文件名：TvSouQuartz.java
 *  创建人：pananz
 *  创建时间：2014-6-25 - 下午4:42:27
 */
public class TvSouQuartz {
	private static final Logger logger = Logger.getLogger(TvSouQuartz.class);

	private static String DATE_PATTERN = "yyyy-MM-dd";
	private static final String PRE_FILE_NAME = "tvsou_data";
	private static final String TVSOU_EPG = "https://www.tvsou.com/epg";

	/**
	 * 按时间范围获取节目单
	 * 
	 * 创建人：pananz 创建时间：2015-3-23 - 上午11:39:27 返回类型：void
	 * 
	 * @exception throws
	 */
	public void checkProgramsByTS() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		Channel c = new Channel();
		c.setSecondNode("0");
		c.setOrderColumn("id");
		c.setStatus("1");
		List<Channel> cs = channelMapper.findChannelByCond(c);
		List<Date> dates = DateUtil.getDatesOfWeek();
		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		for (Date date : dates) {
			List<Programs> pList2 = new ArrayList<Programs>();
			for (Channel channel : cs) {
				if (StringUtil.isNotEmpty(channel.getTvsouRel())) {
					try {
						logger.debug("通过tvsou获取节目");
						updatePrograms(programsMapper, channel, date, pList2,
								tsMap);
						Thread.sleep(200);// 200毫秒钟请求一次
					} catch (Exception e) {
						logger.error("通过tvsou获取节目异常:" + e.getMessage());
						continue;
					}
				}
			}
			logger.debug("pList2 当前数据量为：" + pList2.size());
			if (CollectionUtils.isNotEmpty(pList2)) {
				try {
					ProgramsUtils.createDataFile(PRE_FILE_NAME, date, pList2,
							Global.PROGRAM_UPLOAD_SOURCE_URL);
					pList2 = null;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		cs = null;
		dates = null;
	}

	private static void sendEmail(Channel channel, Date date) {
		if (Global.EMAIL_ENABLE
				&& StringUtil.isNotEmpty(Global.RECEIVE_EMAIL_ADRRESS)) {
			String[] address = Global.RECEIVE_EMAIL_ADRRESS.split(",");
			for (String to : address) {
				logger.error("邮件通知管理:" + to);
				try {
					SendEmailUtils.sendHtml(
							to,
							"频道于tvsou: " + channel.getName() + "抓取"
									+ DateUtil.toString(date, "yyyy-MM-dd")
									+ "出错",
							channel.getName() + "于"
									+ DateUtil.toString(date, "yyyy-MM-dd")
									+ "的内容抓取不到");
				} catch (OTTException e) {
					e.printStackTrace();
					logger.error("邮件通知" + to + "出错,抓取不到频道: "
							+ channel.getName() + "于"
							+ DateUtil.toString(date, "yyyy-MM-dd") + "的内容");
				}
			}
		}
	}

	/**
	 * 取得新版样式节目单
	 * 
	 * 创建人：pananz 创建时间：2015-8-19
	 * 
	 * @param @param channel
	 * @param @param date
	 * @param @param num
	 * @param @param tsMap
	 * @param @return 返回类型：List<Programs>
	 * @exception throws
	 */
	public static List<Programs> getProgramsByTS(Channel channel, Date date,
			int num, Map<String, Dictionary> tsMap) {
		if (StringUtil.isEmpty(channel.getTvsouRel())) {
			logger.debug("无tvsou抓取源");
			return null;
		}
		// /CCTV-5/20170811
		String day = DateUtil.toString(date, "yyyyMMdd");
		String url = TVSOU_EPG + "/" + channel.getTvsouRel() + "/" + day;
		logger.debug("频道-》" + channel.getName() + "访问地址：" + url);
		String html = SSOUtil.getResultByUTF8(url);
		if (StringUtil.isEmpty(html)) {
			try {
				Thread.sleep(3 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (num <= 3) {
				num++;
				logger.debug("频道于tvsou：" + channel.getName() + "请求到的内容为空: "
						+ url + "继续抓取第" + num + "次");
				return getProgramsByTS(channel, date, num, tsMap);
			} else {
				// 邮件通知管理人员
				logger.error("邮件通知管理管理人员:" + channel.getName());
				sendEmail(channel, date);
				return null;
			}
		}
		Document doc = Jsoup.parse(html);
		Element div = doc.select("div.play-time-more").first();
		if (div == null) {
			logger.debug("频道-》" + channel.getName() + "无div.play-time-more属性返回");
			return null;
		}
		String playDate = DateUtil.toString(date, DATE_PATTERN);
		List<Programs> list = new ArrayList<Programs>();
		Elements lis = div.getElementsByTag("li");
		if (lis.isEmpty()) {
			return null;
		}
		for (Element el : lis) {
			Element e1 = el.getElementsByTag("span").first();
			Element e2 = el.getElementsByTag("a").first();
			String time = e1.text();
			String name = e2.text();
			Date startTime;
			try {
				if ("正在播出".equals(time)) {
					logger.debug("正在播出->" + name);
					String timeStr = el.attr("data-mainstars");
					String[] arr = timeStr.split("-");
					time = arr[0];
				}
				startTime = DateUtil.formatDate(playDate + " " + time,
						"yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}

			Programs tp = new Programs(name, channel.getId(),
					DateUtil.toString(startTime, "yyyy-MM-dd HH:mm"), "");
			String basicName = StringUtil.filterWord(name);
			logger.debug("名称由: " + name + "过虑前缀后为: " + basicName);
			Dictionary dict = tsMap.get(basicName);
			if (dict == null) {
				String tempName = StringUtil.filterHaveNumber(basicName);
				if (StringUtil.isEmpty(tempName)) {
					tp.setBasicName(name);
				} else {
					tp.setBasicName(tempName);
				}
				String queue = StringUtil.getHaveLastNum(name);
				logger.debug("名称由: " + name + "数字过虑后为: " + queue);
				tp.setQueue(queue);
			} else {
				logger.debug("特殊名称: " + name);
				tp.setBasicName(name);
				tp.setQueue("0");
			}
			tp.setChannelName(channel.getName());
			list.add(tp);
			tp = null;
		}
		return list;
	}

	/**
	 * 计算时长节目单 创建人：pananz 创建时间：2014-7-23 - 上午12:10:33
	 * 
	 * @param list
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	public static List<Programs> setTime(List<Programs> list, Channel channel,
			Map<String, Dictionary> tsMap) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Programs> tps = new ArrayList<Programs>();
		for (int i = 0; i < list.size(); i++) {
			Programs tp = list.get(i);
			if (i < list.size() - 1) {
				String preTime = list.get(i + 1).getPlayTime();
				String thisTime = tp.getPlayTime();
				long millis = 0;
				try {
					millis = DateUtil.dateDiff(
							DateUtil.formatDate(thisTime, "yyyy-MM-dd HH:mm"),
							DateUtil.formatDate(preTime, "yyyy-MM-dd HH:mm"));
				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
				String timeLongth = String
						.valueOf(SecondsUtils.minutes(millis));
				tp.setTimeLongth(timeLongth);
			} else {// 当天最后一个时
				String playTime = tp.getPlayTime();
				long millis = 0;
				try {
					Date thisTime = DateUtil.formatDate(playTime,
							"yyyy-MM-dd HH:mm");
					Date preTime = DateUtil.setTime(thisTime, 24, 0, 0);
					millis = DateUtil.dateDiff(thisTime, preTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tp.setTimeLongth(String.valueOf(SecondsUtils.minutes(millis)));
			}
			tps.add(tp);
		}
		return tps;
	}

	/**
	 * 更新指定日期的数据
	 * 
	 * 创建人：pananz 创建时间：2015-5-5 - 下午5:09:07
	 * 
	 * @param programsMapper
	 * @param channel
	 * @param date
	 * @param pList2
	 *            返回类型：void
	 * @exception throws
	 */
	private boolean updatePrograms(ProgramsMapper programsMapper,
			Channel channel, Date date, List<Programs> pList2,
			Map<String, Dictionary> tsMap) {
		List<Programs> pps = getProgramsByTS(channel, date, 1, tsMap);
		List<Programs> list = setTime(pps, channel, tsMap);
		if (CollectionUtils.isNotEmpty(list)) {// 删除那一天，再增加那一天的数据
			programsMapper.delProgramsByCidAndDate(channel.getId(),
					DateUtil.parseDate(date, DATE_PATTERN));
			programsMapper.addProgramsByList(list);
			pList2.addAll(list);
		}
		pps = null;
		list = null;
		return false;
	}

	/**
	 * 删除前三天的数据
	 * 
	 * 创建人：pananz 创建时间：2015-5-5 - 下午4:39:52 返回类型：void
	 * 
	 * @exception throws
	 */
	public void removeProgramsByTS() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		Channel channel = new Channel();
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> list = channelMapper.findChannelByCond(channel);
		for (Channel c : list) {
			if (!("0".equals(c.getParentId()))) {
				try {
					// 去除前三天历史数据
					programsMapper.delProgramBy7Date(c.getId(),
							DateUtil.getCurrentDate());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		list = null;
	}

	/**
	 * 校验今天是否有抓取数据
	 * 
	 * 创建人：pananz 创建时间：2015-8-19
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public void checkTodayPrograms() {
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		int total = programsMapper.getCountProgramsByCreateTime(DateUtil
				.getCurrentDate());
		logger.debug("total update programs is:" + total);
		if (total <= 0) {
			logger.error("今天暂无更新节目，开始进行节目抓取");
			updateProgramsByTS();
		}
	}

	/**
	 * 每天更新一次内容，并删除前三天的数据 创建人：pananz 创建时间：2014-8-26 - 上午11:11:25 返回类型：void
	 * 
	 * @exception throws
	 */
	public void updateProgramsByTS() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		Channel channel = new Channel();
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> list = channelMapper.findChannelByCond(channel);
		List<Programs> pList2 = new ArrayList<Programs>();
		Date now = new Date();
		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		for (Channel c : list) {
			if (StringUtil.isNotEmpty(c.getTvsouRel())
					&& !("0".equals(c.getParentId()))) {
				try {
					logger.debug("通过tvsou更新节目");
					// 删除今天，增加今天的数据
					List<Programs> pList = getProgramsByTS(c, now, 1, tsMap);
					List<Programs> programs = setTime(pList, c, tsMap);
					if (CollectionUtils.isNotEmpty(programs)) {
						logger.debug("更新频道" + c.getName() + "节目");
						programsMapper.delProgramsByCidAndToday(c.getId());
						programsMapper.addProgramsByList(programs);
						pList2.addAll(programs);
					}
					pList = null;
					programs = null;
					Thread.sleep(200);// 200毫秒钟请求一次
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(pList2)) {
			ProgramsUtils.createDataFile(PRE_FILE_NAME, now, pList2,
					Global.PROGRAM_UPLOAD_SOURCE_URL);
			pList2 = null;
		}
		list = null;
	}

	/**
	 * 获取央视卫视节目单信息
	 * 
	 * 创建人：pananz 创建时间：2015-11-9
	 * 
	 * @param @param url
	 * @param @throws IOException 返回类型：void
	 * @exception throws
	 */
	public void updateProgramsOfYW() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		CTypeMapper cTypeMapper = (CTypeMapper) SpringContextHolder
				.getBean(CTypeMapper.class);
		Channel channel = new Channel();
		channel.setSecondNode("0");
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<CType> types = cTypeMapper.findCTypeByTypeList("1,2");
		List<String> temp = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(types)) {
			for (CType type : types) {
				temp.add(type.getChannelId());
			}
		}
		channel.setTypeList(StringUtils.join(temp, ","));
		temp = null;
		types = null;
		List<Channel> cs = channelMapper.findChannelByCond(channel);
		logger.debug("开始抓取央视卫视频道节目...............");
		List<Programs> pList2 = new ArrayList<Programs>();
		Date now = new Date();
		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		for (Channel c : cs) {
			if (StringUtil.isNotEmpty(c.getTvsouRel())
					&& !("0".equals(c.getParentId()))) {
				logger.debug("通过tvsou更新节目");
				try {
					// 去除前三天历史数据
					programsMapper.delProgramBy7Date(c.getId(),
							DateUtil.getCurrentDate());
					// 删除今天，增加今天的数据
					List<Programs> pList = getProgramsByTS(c, now, 1, tsMap);
					List<Programs> programs = setTime(pList, c, tsMap);
					if (CollectionUtils.isNotEmpty(programs)) {
						programsMapper.delProgramsByCidAndToday(c.getId());
						programsMapper.addProgramsByList(programs);
						pList2.addAll(programs);
					}
					pList = null;
					programs = null;
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (CollectionUtils.isNotEmpty(pList2)) {
			ProgramsUtils.createDataFile(PRE_FILE_NAME, now, pList2,
					Global.PROGRAM_UPLOAD_SOURCE_URL);
			pList2 = null;
		}
		cs = null;
	}

	public static void main(String[] args) {
		Channel channel = new Channel();
		Date date = new Date();
		String day = DateUtil.toString(DateUtil.addDay(date, -1), "yyyyMMdd");
		String url = TVSOU_EPG + "/SDTV_1hd/" + day;
		logger.debug("频道-》" + channel.getName() + "访问地址：" + url);
		String html = SSOUtil.getResultByUTF8(url);
		if (StringUtil.isEmpty(html)) {
			logger.debug("频道于tvsou：" + channel.getName() + "请求到的内容为空: " + url);
		}
		Document doc = Jsoup.parse(html);
		Element div = doc.select("div.play-time-more").first();
		if (div == null) {
			logger.debug("频道-》" + channel.getName() + "无div.play-time-more属性返回");
			return;
		}
		String playDate = DateUtil.toString(date, DATE_PATTERN);
		List<Programs> list = new ArrayList<Programs>();
		Elements lis = div.getElementsByTag("li");
		if (lis.isEmpty()) {
			return;
		}
		for (Element el : lis) {
			Element e1 = el.getElementsByTag("span").first();
			Element e2 = el.getElementsByTag("a").first();
			String time = e1.text();
			String name = e2.text();
			logger.debug(name + "==" + time);
			Date startTime;
			try {
				if ("正在播出".equals(time)) {
					String timeStr = el.attr("data-mainstars");
					String[] arr = timeStr.split("-");
					time = arr[0];
				}
				startTime = DateUtil.formatDate(playDate + " " + time,
						"yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}

			String playTime = DateUtil.toString(startTime, "yyyy-MM-dd HH:mm");
			Programs tp = new Programs(name, channel.getId(), playTime, "");
			String basicName = StringUtil.filterWord(name);
			logger.debug("名称由: " + name + "过虑前缀后为: " + basicName);
			String tempName = StringUtil.filterHaveNumber(basicName);
			if (StringUtil.isEmpty(tempName)) {
				tp.setBasicName(name);
			} else {
				tp.setBasicName(tempName);
			}
			String queue = StringUtil.getHaveLastNum(name);
			logger.debug("名称由: " + name + "数字过虑后为: " + queue);
			tp.setQueue(queue);
			tp.setChannelName(channel.getName());
			logger.debug("名称: " + name + ",播放开始时间：" + playTime);
			list.add(tp);
			tp = null;
		}
	}
}
