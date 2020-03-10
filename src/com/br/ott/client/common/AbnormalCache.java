package com.br.ott.client.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.br.ott.client.common.entity.AbnormalInfo;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.ChannelRecover;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.common.util.DateUtil;

/**
 * 文件名：AbnormalCache.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-11-30
 */
public class AbnormalCache {
	private static final Logger logger = Logger.getLogger(AbnormalCache.class);

	public static List<AbnormalInfo> aiList = new ArrayList<AbnormalInfo>();

	public static void reloadAbnormalInfo(List<AbnormalInfo> list) {
		if (list.equals(aiList)) {
			logger.debug("虚拟内存异常信息与缓存异常信息相同:" + list.size());
			return;
		} else {
			aiList = list;
			logger.debug("虚拟内存异常信息更新:" + list.size());
		}
		list = null;
	}

	public static List<AbnormalInfo> findAbnormalInfo(List<Channel> cList,
			List<Programs> pList, List<ChannelRecover> crList) {
		List<AbnormalInfo> aList = new ArrayList<AbnormalInfo>();
		if (CollectionUtils.isNotEmpty(cList)) {
			for (Channel c : cList) {
				AbnormalInfo ai = new AbnormalInfo(c.getId(), "1");
				aList.add(ai);
			}
		}
		if (CollectionUtils.isNotEmpty(pList)) {
			for (Programs p : pList) {
				AbnormalInfo ai = new AbnormalInfo(p.getId(), "2");
				aList.add(ai);
			}
		}
		if (CollectionUtils.isNotEmpty(crList)) {
			for (ChannelRecover cr : crList) {
				AbnormalInfo ai = new AbnormalInfo(cr.getChannelId()
						+ "-"
						+ DateUtil.toString(cr.getRecoverTime(),
								"yyyyMMddHHmmss") + "00", "3");
				aList.add(ai);
			}
		}
		logger.debug("异常频道节目信息更新");
		aiList = aList;
		logger.debug("虚拟内存异常信息更新:" + aList.size());
		return aList;
	}

}
