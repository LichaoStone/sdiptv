package com.br.ott.client.live.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.api.entity.ResponsePrograms;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.BasicCProgram;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.entity.RecChannel;
import com.br.ott.client.live.entity.RecProgram;
import com.br.ott.client.live.mapper.BasicProgramMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.client.live.mapper.RecChannelMapper;
import com.br.ott.client.live.mapper.RecProgramMapper;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：RecChannelService.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午4:09:20
 */
@Service
public class RecChannelService {
	private static Logger log = Logger.getLogger(RecChannelService.class);
	@Autowired
	private RecChannelMapper recChannelMapper;

	@Autowired
	private ProgramsMapper programsMapper;

	@Autowired
	private RecProgramMapper recProgramMapper;

	@Autowired
	private BasicProgramMapper basicProgramMapper;

	/**
	 * 增加频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:02:57
	 * 
	 * @param recChannel
	 *            返回类型：void
	 * @exception throws
	 */
	public void addRecChannel(RecChannel recChannel) {
		recChannelMapper.addRecChannel(recChannel);
	}

	/**
	 * 修改频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:03:05
	 * 
	 * @param recChannel
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateRecChannel(RecChannel recChannel) {
		recChannelMapper.updateRecChannel(recChannel);
	}

	/**
	 * 按ID查询频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:03:13
	 * 
	 * @param id
	 * @return 返回类型：RecChannel
	 * @exception throws
	 */
	public RecChannel getRecChannelById(String id) {
		return recChannelMapper.getRecChannelById(id);
	}

	/**
	 * 按分页查询频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:03:22
	 * 
	 * @param recChannel
	 * @return 返回类型：List<RecChannel>
	 * @exception throws
	 */
	public List<RecChannel> findRecChannelByPage(RecChannel recChannel) {
		int totalResult = recChannelMapper.getCountRecChannel(recChannel);
		recChannel.setTotalResult(totalResult);
		return recChannelMapper.findRecChannelByPage(recChannel);
	}

	/**
	 * 按条件查询频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:03:34
	 * 
	 * @param recChannel
	 * @return 返回类型：List<RecChannel>
	 * @exception throws
	 */
	public List<RecChannel> findRecChannelByCond(RecChannel recChannel) {
		return recChannelMapper.findRecChannelByCond(recChannel);
	}

	/**
	 * 删除频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午4:03:44
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void deleteRecChannelById(String id) {
		recChannelMapper.deleteRecChannelById(id);
	}

	/**
	 * 修改频道推荐状态
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:25:51
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateRecChannel(String status, String id) {
		recChannelMapper.updateRecChannelStatus(status, id);
	}

	private static final int PHONE_CHANNEL_REC_SIZE = 4;

	@SuppressWarnings("unchecked")
	public void findRecIndex() {
		List<Dictionary> dictList = DictCache.getDictList(DicType.TJLX);
		BeanComparator menuBC = new BeanComparator("orderId");// 排序
		Collections.sort(dictList, menuBC);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> indexRecMap = new HashMap<String, String>();
		for (CityOperators co : OperatorsCache.opList) {
			for (Dictionary dict : dictList) {
				String tid = dict.getDicValue();
				Map<String, String> map = new HashMap<String, String>();
				map.put("tid", tid);
				map.put("name", dict.getDicName());
				String operators = co.getId();
				if ("1".equals(dict.getOrderId())) {
					if ("4".equals(tid)) {
						List<ResponsePrograms> tempList = findRecProgram(
								operators, tid, PHONE_CHANNEL_REC_SIZE);
						JSONArray jsonArray = (JSONArray) JSONSerializer
								.toJSON(tempList);
						map.put("channel", jsonArray.toString());
					} else if ("1".equals(tid) || "2".equals(tid)
							|| "3".equals(tid)) {// 1：央视直播频道，2：卫视直播频道,3：地方直播频道
						List<ResponsePrograms> tempList = findRecChannel(
								operators, tid, PHONE_CHANNEL_REC_SIZE);
						JSONArray jsonArray = (JSONArray) JSONSerializer
								.toJSON(tempList);
						map.put("channel", jsonArray.toString());
					}
				}
				list.add(map);
			}
			JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
			indexRecMap.put(co.getCode(), jsonArray.toString());
			list.clear();
		}
		ChannelCache.reloadIndexRec(indexRecMap);
	}

	public void findIndexChannel() {
		List<Dictionary> dictList = DictCache.getDictList(DicType.TJLX);
		Map<String, String> indexRecMap = new HashMap<String, String>();
		for (CityOperators co : OperatorsCache.opList) {
			for (Dictionary dict : dictList) {
				String tid = dict.getDicValue();
				List<ResponsePrograms> list = null;
				if ("4".equals(tid)) {// 1：热门直播频道
					list = findRecProgram(co.getId(), tid,
							PHONE_CHANNEL_REC_SIZE);
				} else if ("1".equals(tid) || "2".equals(tid)
						|| "3".equals(tid)) {// 1：央视直播频道，2：卫视直播频道,3：地方直播频道
					list = findRecChannel(co.getId(), tid,
							PHONE_CHANNEL_REC_SIZE);
				}
				JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
				indexRecMap.put(co.getCode() + "-" + tid, jsonArray.toString());
			}
		}
		ChannelCache.reloadRecChannel(indexRecMap);
	}

	/**
	 * 取得推荐频道正在直播节目单信息 创建人：pananz 创建时间：2014-9-25 - 下午5:16:45
	 * 
	 * @param programs
	 * @param page
	 * @param limit
	 * @return 返回类型：String
	 * @exception throws
	 */
	private List<ResponsePrograms> findRecChannel(String operators,
			String rcType, int count) {
		Programs programs = new Programs();
		programs.setOrderColumn("rc.sequence");
		programs.setRcType(rcType);
		programs.setcStatus("1");
		programs.setPlayNow("yes");
		programs.setCurrentPage(1);
		programs.setShowCount(count);
		programs.setOperators(operators);
		List<Programs> ps = programsMapper.findCProgramByPageAndRec(programs);
		List<ResponsePrograms> list = new ArrayList<ResponsePrograms>();
		for (Programs p : ps) {
			ResponsePrograms rp = new ResponsePrograms();
			rp.setChannelId(p.getChannelId());
			rp.setChannelName(p.getChannelName());
			rp.setLogoUrl(Global.SERVER_SOURCE_URL + p.getLogoImgUrl());
			rp.setWlogoUrl(Global.SERVER_SOURCE_URL + p.getWlogoUrl());
			rp.setHlogoUrl(Global.SERVER_SOURCE_URL + p.getHlogoUrl());
			rp.setProgramId(p.getId());
			rp.setProgramName(p.getName());
			rp.setPlayDtime(p.getPlayTime());
			rp.setTimeOut(p.getTimeLongth());
			rp.setSingleLiveServer(p.getSingleLiveServer());
			rp.setPlayId(p.getPlayId());
			if (!"0".equals(p.getQueue())) {
				rp.setQueue(p.getQueue());
			}
			list.add(rp);
		}
		return list;
	}

	/**
	 * 热门推荐节目，首先以节目推荐中的热门类型为主，不足数量时，以基础节目信息补充
	 * 
	 * 创建人：pananz 创建时间：2016-12-7
	 * 
	 * @param @param operators
	 * @param @param rcType
	 * @param @param count
	 * @param @return 返回类型：List<ResponsePrograms>
	 * @exception throws
	 */
	private List<ResponsePrograms> findRecProgram(String operators,
			String rcType, int count) {
		RecProgram recProgram = new RecProgram();
		recProgram.setType(rcType);
		recProgram.setCurrentPage(1);
		recProgram.setShowCount(count);
		recProgram.setOperators(operators);
		List<RecProgram> RecPrograms = recProgramMapper
				.findRecLProByPage(recProgram);
		List<ResponsePrograms> list = new ArrayList<ResponsePrograms>();
		List<String> nList = new ArrayList<String>();
		for (RecProgram p : RecPrograms) {
			ResponsePrograms rp = new ResponsePrograms();
			rp.setChannelId(p.getChannelId());
			rp.setChannelName(p.getChannelName());
			rp.setSingleLiveServer(p.getSingleLiveServer());
			rp.setWlogoUrl(Global.SERVER_SOURCE_URL + p.getWlogoUrl());
			rp.setProgramName(p.getpName());
			rp.setPlayDtime(p.getPlayTime());
			rp.setProgramId(p.getpId());
			rp.setTimeOut(p.getTimeOut());
			rp.setPlayId(p.getPlayId());
			rp.setLocalCid(p.getLocalCid());
			rp.setQueue(p.getQueue());
			list.add(rp);
			nList.add(p.getpName());
		}
		if (list.size() < 4) {// 以基础节目直播信息补充
			log.debug("推荐直播节目个数不足，以基础节目直播信息补充");
			BasicCProgram bp = new BasicCProgram();
			bp.setCurrentPage(1);
			bp.setShowCount(20);
			bp.setOperators(operators);
			List<BasicCProgram> bps = basicProgramMapper
					.findBasicHotProgramByPage(bp);
			for (BasicCProgram p : bps) {
				String programName = p.getProgramName();
				if (nList.contains(programName)
						|| StringUtil.isEmpty(programName)) {
					continue;
				}
				ResponsePrograms rp = new ResponsePrograms();
				rp.setChannelId(p.getChannelId());
				rp.setChannelName(p.getChannelName());
				rp.setWlogoUrl(Global.SERVER_SOURCE_URL + p.getLogoImgUrl());
				rp.setProgramName(programName);
				rp.setPlayDtime(p.getPlayTime());
				rp.setProgramId(p.getProgramId());
				rp.setTimeOut(p.getTimeLongth());
				rp.setPlayId(p.getPlayId());
				rp.setLocalCid(p.getLocalCid());
				rp.setQueue(p.getQueue());
				list.add(rp);
				if (list.size() == 4) {
					break;
				}
			}
			bps = null;
			if (list.size() < 4) {// 推荐直播频道补充
				log.debug("基础节目直播个数不足，以推荐直播频道补充");
				Programs programs = new Programs();
				programs.setOrderColumn("rc.type,rc.sequence");
				programs.setcStatus("1");
				programs.setPlayNow("yes");
				programs.setCurrentPage(1);
				programs.setShowCount(12);
				programs.setOperators(operators);
				List<Programs> ps = programsMapper
						.findCProgramByPageAndRec(programs);
				for (Programs p : ps) {
					String programName = p.getName();
					if (nList.contains(programName)
							|| StringUtil.isEmpty(programName)) {
						continue;
					}
					ResponsePrograms rp = new ResponsePrograms();
					rp.setChannelId(p.getChannelId());
					rp.setChannelName(p.getChannelName());
					rp.setLogoUrl(Global.SERVER_SOURCE_URL + p.getLogoImgUrl());
					rp.setWlogoUrl(Global.SERVER_SOURCE_URL + p.getWlogoUrl());
					rp.setHlogoUrl(Global.SERVER_SOURCE_URL + p.getHlogoUrl());
					rp.setProgramId(p.getId());
					rp.setProgramName(programName);
					rp.setPlayDtime(p.getPlayTime());
					rp.setTimeOut(p.getTimeLongth());
					rp.setSingleLiveServer(p.getSingleLiveServer());
					rp.setPlayId(p.getPlayId());
					if (!"0".equals(p.getQueue())) {
						rp.setQueue(p.getQueue());
					}
					list.add(rp);
					if (list.size() == 4) {
						break;
					}
				}
				ps = null;
			}
		}
		nList = null;
		return list;
	}

	/**
	 * 获取推荐频道最大排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-03-21
	 * 
	 * @param @param type
	 * @param @return 返回类型：int
	 * @exception throws
	 */
	public int getMaxSequence(String type){
		return this.recChannelMapper.getMaxSequence(type);
	}
	
	/**
	 * 改变推荐顺序
	 * @author lizhenghg
	 * @param dn
	 * @param oldQueue
	 * @param queue
	 */
	public void changeDNSequence(RecChannel rec, String oldQueue, String queue){
		int old = Integer.parseInt(oldQueue);
		int now = Integer.parseInt(queue);
		rec.setSequence(oldQueue);
		rec.setOldSequence(old);
		if (old > now) {// 从大到小,小于当前要修改的排序值queue要加1
			recChannelMapper.updateDNAddSequence(rec);
		} else {// 从小到大,queue的排序值要减1
			recChannelMapper.updateDNSubSequence(rec);
		}
		recChannelMapper.updateDNSequence(queue, rec.getId());
	}
}
