package com.br.ott.client.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.api.entity.Item;
import com.br.ott.client.api.entity.PrgItem;
import com.br.ott.client.api.entity.TimeTable;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;
import com.thoughtworks.xstream.XStream;

/* 
 *  
 *  文件名：EPGRecordService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-22 - 下午3:07:29
 */
@Service
public class EPGRecordService {

	@Autowired
	private ProgramsMapper programsMapper;
	
	private static final String item_header = "<?xml version=\"1.0\" encoding=\"utf-8\"?><timeTable xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String YMD_PATTERN = "yyyyMMdd";

	private static final Logger logger = Logger
			.getLogger(EPGRecordService.class);

	public static long DJBHash(String str) {
		long hash = 5381;
		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) + str.charAt(i);
		}
		return hash;
	}

	/**
	 * 通过第三方频道ID查询节目单
	 * 
	 * 创建人：pananz 创建时间：2015-4-22 - 下午3:20:57
	 * 
	 * @param cid
	 * @return
	 * @throws OTTException
	 *             返回类型：String <?xml version="1.0" encoding="utf-8"?> <timeTable
	 *             xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	 *             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> <item
	 *             name="凤凰资讯HD" date="20150416"> <prgItem>
	 *             <time>00:00-00:30</time> <name>凤凰子夜快车(16/4/15)</name>
	 *             </prgItem> ...... <item name="凤凰资讯HD" date="20150417"> ......
	 *             </item> </timeTable>
	 * 
	 * @exception throws
	 */
	public String findProgramsByCid(String cid) throws OTTException {
		Programs programs = new Programs();
		Date minDate = DateUtil.setTime(new Date(), 0, 0, 0);
		Date maxDate = DateUtil.addDay(new Date(), 1);
		programs.setPlayTimeMin(DateUtil.toString(minDate, DATE_PATTERN));
		programs.setPlayTimeMax(DateUtil.toString(maxDate, DATE_PATTERN));
		programs.setCid(cid);
		List<Programs> list = programsMapper.findProgramsByCid(programs);
		if (CollectionUtils.isNotEmpty(list)) {
			List<Item> items = new ArrayList<Item>();
			Item item = new Item();
			String first = DateUtil.toString(minDate, YMD_PATTERN);
			item.setDate(first);
			Programs p = list.get(0);
			item.setName(p.getChannelName());
			List<PrgItem> prgItem = new ArrayList<PrgItem>();

			Item item2 = new Item();
			String second = DateUtil.toString(maxDate, YMD_PATTERN);
			item2.setDate(second);
			item2.setName(p.getChannelName());
			List<PrgItem> prgItem2 = new ArrayList<PrgItem>();

			for (Programs pro : list) {
				if (DateUtil.parseDate2(pro.getPlayTime()).equals(first)) {
					prgItem.add(new PrgItem(pro.getPlayDtime(), pro.getName()));
				} else if (DateUtil.parseDate2(pro.getPlayTime())
						.equals(second)) {
					prgItem2.add(new PrgItem(pro.getPlayDtime(), pro.getName()));
				}
			}
			item.setPrgItem(prgItem);
			item2.setPrgItem(prgItem2);
			items.add(item);
			items.add(item2);
			TimeTable timeTable = new TimeTable();
			timeTable.setList(items);

			XStream stream = new XStream();
			stream.processAnnotations(TimeTable.class);
			String xml = stream.toXML(timeTable);

			items = null;
			prgItem = null;
			prgItem2 = null;
			list = null;
			if (StringUtil.isNotEmpty(xml)) {
				logger.debug("返回参数格式：" + xml);
				return xml.replaceAll("<timeTable>", item_header);
			}
		}
		return "";
	}

}
