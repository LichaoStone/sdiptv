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
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.SSOUtil;
import com.br.ott.common.util.SecondsUtils;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.spring.SpringContextHolder;

/**
 * 文件名：CCTVQuartz.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 * 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-11-9
 */
public class CCTVQuartz {
	private static final Logger logger = Logger.getLogger(CCTVQuartz.class);

	private static String DATE_PATTERN = "yyyy-MM-dd";
	private static final String PRE_FILE_NAME = "cctv_data";
	private static final String HTTP_INDEX = "http://tv.cntv.cn/index.php?action=zhibo-jiemu3&channel=";

	// http://tv.cntv.cn/index.php?action=epg-list&date=2015-11-10&channel=cctv1&mode=
	//http://api.cntv.cn/epg/epginfo?serviceId=tvcctv&c=cctv1&d=20170810&cb=callback&t=jsonp
	/**
	 * 抓取央视网页上的节目单(后三天)
	 * cctv1
	 * 创建人：pananz 创建时间：2015-11-9
	 * 
	 * @param 返回类型
	 *            ：void
	 * @exception throws
	 */
	public void crawlProgramsByCCTV() {
		ChannelMapper channelMapper = SpringContextHolder
				.getBean(ChannelMapper.class);
		ProgramsMapper programsMapper = SpringContextHolder
				.getBean(ProgramsMapper.class);
		CTypeMapper cTypeMapper = (CTypeMapper) SpringContextHolder
				.getBean(CTypeMapper.class);
		Channel channel = new Channel();
		channel.setSecondNode("0");
		channel.setStatus("1");
		channel.setOrderColumn("id");
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
		List<Date> dates = new ArrayList<Date>();
		Date today = new Date();
		for (int i = 1; i <= 3; i++) {
			Date date = DateUtil.addDay(today, i);
			dates.add(date);
		}

		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		for (Date date : dates) {
			List<Programs> pList2 = new ArrayList<Programs>();
			for (Channel c : cs) {
				if (StringUtil.isNotEmpty(c.getCctvRel())
						&& !("0".equals(c.getParentId()))) {
					try {
						updatePrograms(programsMapper, c, date, pList2, tsMap);
						Thread.sleep(200);// 200毫秒钟请求一次
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			logger.debug("pList2 当前数据量为：" + pList2.size());
			if (CollectionUtils.isNotEmpty(pList2)) {
				try {
					ProgramsUtils.createDataFile(PRE_FILE_NAME, date, pList2,Global.PROGRAM_UPLOAD_SOURCE_URL);
					pList2.clear();
					pList2 = null;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}

	private boolean updatePrograms(ProgramsMapper programsMapper,
			Channel channel, Date date, List<Programs> pList2,
			Map<String, Dictionary> tsMap) {
		List<Programs> pps = getProgramsByYS(channel, date, 1, tsMap);
		List<Programs> list = setTime(pps, channel, tsMap);
		if (CollectionUtils.isNotEmpty(list)) {// 删除那一天，再增加那一天的数据
			programsMapper.delProgramsByCidAndDate(channel.getId(),
					DateUtil.parseDate(date, DATE_PATTERN));
			programsMapper.addProgramsByList(list);
			//TvSouQuartz.syncNameTodbcms(programsMapper, list);
			pList2.addAll(list);
		}
		pps = null;
		list = null;
		return false;
	}

	public static List<Programs> getProgramsByYS(Channel channel, Date date,
			int num, Map<String, Dictionary> tsMap) {
		if (StringUtil.isEmpty(channel.getCctvRel())) {
			return null;
		}
		num++;
		String day = DateUtil.toString(date, DATE_PATTERN);
		// cctv4&date=2015-11-10
		String url = HTTP_INDEX + channel.getCctvRel() + "&date=" + day;
		logger.debug("频道-》" + channel.getName() + "访问地址：" + url);
		String html = SSOUtil.getResultByUTF8(url);
		if (StringUtil.isEmpty(html)) {
			try {
				Thread.sleep(3 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (num <= 3) {
				logger.debug("频道于CCTV：" + channel.getName() + "请求到的内容为空: "
						+ url + "继续抓取第" + num + "次");
				return getProgramsByYS(channel, date, num++, tsMap);
			} else {
				// 邮件通知管理人员
//				sendEmail(channel, date);
				return null;
			}
		}
		Document doc = Jsoup.parse(html);
		Elements lis = doc.getElementsByTag("li");
		if (lis.isEmpty()) {
			return null;
		}
		List<Programs> list = new ArrayList<Programs>();
		for (Element el : lis) {
			Element e1 = el.select("span.sp_1").first();
			Element e2 = el.select("span.sp_3").first();
			if (e1 == null || e1 == null) {
				continue;
			}
			logger.debug(e1.text() + "==" + e2.text());
			String name = e2.text();
			Date play;
			try {
				play = DateUtil.formatDate(day + " " + e1.text(),
						"yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}
			Programs tp = new Programs(name, channel.getId(),
					DateUtil.toString(play, "yyyy-MM-dd HH:mm"), "");
			String basicName = StringUtil.filterWord(name);
			logger.debug("名称由: " + name + "过虑后为: " + basicName);
			Dictionary dict = tsMap.get(basicName);
			if (dict == null) {
				tp.setBasicName(StringUtil.filterHaveNumber(basicName));
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
			} else {// 当天最后一个时，以第二天的第条数据的时间为准
				String playTime = tp.getPlayTime();
				long millis = 0;
				try {
					List<Programs> pList = getProgramsByYS(
							channel,
							DateUtil.addDay(
									DateUtil.formatDate(playTime, "yyyy-MM-dd"),
									1), 1, tsMap);
					if (CollectionUtils.isEmpty(pList)) {// 取不到第二天的数据时
						try {
							Date thisTime = DateUtil.formatDate(playTime,
									"yyyy-MM-dd HH:mm");
							Date preTime = DateUtil
									.setTime(thisTime, 23, 59, 0);
							millis = DateUtil.dateDiff(thisTime, preTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						Programs p = pList.get(0);
						try {
							millis = DateUtil.dateDiff(DateUtil.formatDate(
									playTime, "yyyy-MM-dd HH:mm"), DateUtil
									.formatDate(p.getPlayTime(),
											"yyyy-MM-dd HH:mm"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					pList = null;
					tp.setTimeLongth(String.valueOf(SecondsUtils
							.minutes(millis)));
				} catch (Exception e1) {
					e1.printStackTrace();
					continue;
				}

			}
			tps.add(tp);
		}
		return tps;
	}

	public static void main(String[] args) {
		String html = SSOUtil
				.getResultByUTF8("http://api.cntv.cn/epg/epginfo?serviceId=tvcctv&c=cctv2&d=20170801");
		System.out.println(html);
//		Document doc = Jsoup.parse(html);
//		Elements lis = doc.getElementsByTag("li");
//		if (lis.isEmpty()) {
//			logger.debug("ffffffffffffffff");
//		}
//		for (Element el : lis) {
//			Element e1 = el.select("span.sp_1").first();
//			Element e2 = el.select("span.sp_3").first();
//			logger.debug(e1.text() + "==" + e2.text());
//		}
	}
}
