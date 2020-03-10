package com.br.ott.client.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.api.entity.ResponseCPrograms;
import com.br.ott.client.api.entity.ResponsePrograms;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.BasicCProgram;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.entity.SearchLog;
import com.br.ott.client.live.entity.UserFollow;
import com.br.ott.client.live.mapper.BasicProgramMapper;
import com.br.ott.client.live.mapper.CTypeMapper;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.client.live.mapper.SearchLogMapper;
import com.br.ott.client.live.mapper.UserFollowMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelApiService.java
 *  创建人：pananz
 *  创建时间：2014-7-1 - 下午12:15:39
 */
@Service
public class LiveApiService {

	private static Logger log = Logger.getLogger(LiveApiService.class);
	@Autowired
	private UserFollowMapper userFollowMapper;

	@Autowired
	private ProgramsMapper programsMapper;

	@Autowired
	private CTypeMapper cTypeMapper;

	@Autowired
	private SearchLogMapper searchLogMapper;

	@Autowired
	private BasicProgramMapper basicProgramMapper;

	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;

	/**
	 * 首页推荐信息(包含分类)
	 * 
	 * 创建人：pananz 创建时间：2016-11-16
	 * 
	 * @param @param json
	 * @param @return
	 * @param @throws OTTException 返回类型：String
	 * @exception throws
	 */
	public String findRecType(String operators) throws OTTException {
		String result = ChannelCache.getIndexRecByOperators(operators);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnCode", "0");
		jsonObject.put("result", result);
		return jsonObject.toString();
	}

	/**
	 * 首页推荐查询
	 * 
	 * 创建人：pananz 创建时间：2016-11-16
	 * 
	 * @param @param json
	 * @param @return
	 * @param @throws OTTException 返回类型：String
	 * @exception throws
	 */
	public String findRecChannelLive(JSONObject object) {
		String operators = object.get("operators") == null ? "" : (String)object.get("operators");
		String tid = object.get("tid") == null ? "" : (String)object.get("tid");
		String result = ChannelCache.getRecChannelByOperators(operators + "-" + tid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		jsonObject.put("returnCode", "0");
		return jsonObject.toString();
	}

	/**
	 * 节目单列表获取接口 创建人：pananz 创建时间：2014-7-1 - 下午3:55:10
	 * 
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String findPrograms(JSONObject object){
		String opCode = object.get("operators") == null ? "" : (String)object.get("operators");
		String cname = object.get("cname") == null ? "" : (String)object.get("cname");
		String page = object.get("page") == null ? "1" : (String)object.get("page");
		String limit = object.get("limit") == null ? "15" : (String)object.get("limit");
		String cid = object.get("cid") == null ? "" : (String)object.get("cid");
		String playDate = object.get("playDate") == null ? "" : (String)object.get("playDate");
		
		log.debug("节目单列查询参数cid=" + cid + " cname=" + cname + "playDate="
				+ playDate + " page=" + page + "limit=" + limit);
		Programs programs = new Programs();
		String operators = OperatorsCache.getOperatorsIdByCode(opCode);
		programs.setPlayTime(playDate);
		isHaveSearch(cname);
		programs.setName(cname);
		if (StringUtil.isEmpty(cid) && StringUtil.isEmpty(playDate)) {
			programs.setPlayTimeMin(DateUtil.toString(
					DateUtil.addDay(new Date(), -3), "yyyy-MM-dd HH:mm:ss"));
			programs.setPlayTimeMax(DateUtil.toString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
		}
		programs.setChannelId(cid);
		return findProgramsInfo(programs, page, limit, operators);
	}

	/**
	 * 判断是否有热门搜索内容 创建人：pananz 创建时间：2014-9-25 - 下午3:23:39
	 * 
	 * @param cname
	 *            返回类型：void
	 * @exception throws
	 */
	private void isHaveSearch(String cname) {
		if (StringUtil.isNotEmpty(cname)) {// 记录搜索节目关键内容
			if(cname.length() > 60) cname = cname.substring(0, 60);  //数据库该字段最长长度为64
			List<SearchLog> searchLogs = searchLogMapper
					.findSearchLogByName(cname);
			if (CollectionUtils.isEmpty(searchLogs)) {// 内容不存在时，增加一条记录，搜索值初始化为1
				SearchLog searchLog = new SearchLog();
				searchLog.setName(cname);
				searchLog.setRealNumber("1");
				searchLog.setVirtualNumber("0");
				searchLogMapper.addSearchLog(searchLog);
			} else {// 内容存在时,初始值加1
				SearchLog searchLog = searchLogs.get(0);
				searchLogMapper.updateRealNumber(searchLog.getId());
			}
			searchLogs = null;
		}
	}

	/**
	 * 取得节目单信息 创建人：pananz 创建时间：2014-9-25 - 下午5:46:43
	 * 
	 * @param programs
	 * @param tmid
	 * @param page
	 * @param limit
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String findProgramsInfo(Programs programs, String page,
			String limit, String operators) {
		programs.setOrderColumn("op.playTime");
		programs.setCurrentPage(Integer.valueOf(page));
		programs.setShowCount(Integer.valueOf(limit));
		programs.setOperators(operators);
		int totalResult = programsMapper.getCountCProgramsOperators(programs);
		programs.setTotalResult(totalResult);
		List<Programs> plist = programsMapper
				.findCProgramsByPageAndOperators(programs);
		List<ResponsePrograms> list = new ArrayList<ResponsePrograms>();
		for (Programs p : plist) {
			ResponsePrograms rp = new ResponsePrograms();
			rp.setChannelId(p.getChannelId());
			rp.setChannelName(p.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(p.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rp.setLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rp.setProgramId(p.getId());
			if (StringUtil.isEmpty(p.getBasicName())) {
				rp.setProgramName(p.getName());
			} else {
				rp.setProgramName(p.getBasicName());
			}
			rp.setPlayDtime(p.getPlayTime());
			rp.setTimeOut(p.getTimeLongth());
			rp.setSingleLiveServer(p.getSingleLiveServer());
			rp.setPlayId(p.getPlayId());
			rp.setLocalCid(p.getLocalCid());
			rp.setDbPlay(p.getDbPlay());
			if (!"0".equals(p.getQueue())) {
				rp.setQueue(p.getQueue());
			}
			list.add(rp);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", programs.getTotalResult());
		jsonObject.put("pageCount", programs.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		plist = null;
		list = null;
		return jsonObject.toString();
	}

	/**
	 * 查询直播节目单内容 创建人：pananz 创建时间：2014-7-14 - 下午12:49:57
	 * 
	 * @param request
	 *            tid Varchar（8） 类型id，0:所有频道,1:央视频道，2:卫视频道,3:体育 4，其他 ... 99，收藏
	 *            100，最近收看 -1,热门 是 cname Varchar（128） 频道名称，模糊检索时用 否 page
	 *            Varchar（8） 页码 否（默认为1） limit Varchar（8） ...每页显示几行 否（默认10）
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String channelId 电视频道ID channelName 频道名称 channelDesc
	 *             频道简介 channelLogoUrl 频道logo图标地址 descImgUrl 频道简介图地址 programId
	 *             正在播放的节目id programName 节目名称 playDtime 节目播放时间段（开始-结束
	 *             格式如：18：00-20:00）
	 * 
	 * @exception throws
	 */
	public String findCPrograms(JSONObject object){
		Date date = new Date();
		log.debug("查询直播节目单请求进入时间：" + DateUtil.toString(date));		
		String operatorsCode = object.get("operators") == null ? "" : (String)object.get("operators");
		String tid = object.get("tid") == null ? "" : (String)object.get("tid");
		String cname = object.get("cname") == null ? "" : (String)object.get("cname");
		String page = object.get("page") == null ? "1" : (String)object.get("page");
		String limit = object.get("limit") == null ? "15" : (String)object.get("limit");
		String uid = object.get("uid") == null ? "" : (String)object.get("uid");

		log.debug("直播节目单列查询参数 tid=" + tid + " cname=" + cname + " page=" + page
				+ "limit=" + limit + "operators=" + operatorsCode);
		log.debug("查询直播节目单解析参数时间：" + DateUtil.dateDiff(date, new Date()));
		String operators = OperatorsCache.getOperatorsIdByCode(operatorsCode);
		isHaveSearch(cname);
		if ("99".equals(tid) || "100".equals(tid)) {// 99，收藏 100，最近收看
			return getFollowInfo(page, limit, uid, cname, tid, operators);
		} else {
			return getCProgromInfo(page, limit, uid, cname, tid, operators);
		}
	}

	/**
	 * 取得收藏或最近收看频道的直播节目单内容 创建人：pananz 创建时间：2014-9-24 - 下午4:04:46
	 * 
	 * @param page
	 * @param limit
	 * @param uid
	 * @param cname
	 * @param tid
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String getFollowInfo(String page, String limit, String uid,
			String cname, String tid, String operators) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtil.isEmpty(uid)) {
			jsonObject.put("total", "");
			jsonObject.put("pageCount", "");
			jsonObject.put("result", "[]");
			jsonObject.put("returnCode", "0");
			return jsonObject.toString();
		}
		List<ResponseCPrograms> list = new ArrayList<ResponseCPrograms>();
		UserFollow uf = new UserFollow();
		uf.setCurrentPage(Integer.valueOf(page));
		uf.setShowCount(Integer.valueOf(limit));
		uf.setUid(uid);
		uf.setChannelName(cname);
		uf.setPlayNow("yes");
		uf.setcStatus("1");
		if ("99".equals(tid)) {
			uf.setType("1");
		} else {
			uf.setType("2");
		}
		uf.setOrderColumn("uf.createTime desc");
		uf.setOperators(operators);
		int totalResult = userFollowMapper.getCountUserFollowByOperators(uf);
		uf.setTotalResult(totalResult);
		List<UserFollow> ups = userFollowMapper
				.findUserFollowOperatorsByPage(uf);
		for (UserFollow u : ups) {
			ResponseCPrograms rc = new ResponseCPrograms();
			rc.setChannelId(u.getChannelId());
			rc.setChannelName(u.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(u.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setProgramId(u.getProgramId());
			rc.setProgramName(StringUtil.filterWord(u.getProgramName()));
			rc.setPlayDtime(u.getPlayTime());
			rc.setTimeOut(u.getTimeLongth());
			rc.setSingleLiveServer(u.getSingleLiveServer());
			rc.setLocalCid(u.getLocalCid());
			rc.setDbPlay(u.getDbPlay());
			if (!"0".equals(u.getQueue())) {
				rc.setQueue(u.getQueue());
			}
			list.add(rc);
		}

		jsonObject.put("total", uf.getTotalResult());
		jsonObject.put("pageCount", uf.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		ups = null;
		list = null;
		return jsonObject.toString();
	}

	/**
	 * 取得收藏之外的最近收看直播节目单内容 创建人：pananz 创建时间：2014-9-24 - 下午4:07:25
	 * 
	 * @param page
	 * @param limit
	 * @param uid
	 * @param cname
	 * @param tid
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String getCProgromInfo(String page, String limit, String uid,
			String cname, String tid, String operators) {
		OperatorsChannel oc = new OperatorsChannel();
		Date date = new Date();
		JSONObject jsonObject = new JSONObject();
		String typeCode = ChannelCache.getTypeCodeById(tid);
		List<ResponseCPrograms> list = new ArrayList<ResponseCPrograms>();
		String channelList = "";
		if ("iptv_all".equals(typeCode) || StringUtil.isEmpty(tid)) {// 所有

		} else if ("iptv_ty".equals(typeCode)) {// 体育，首个放CCTV-5等体育频道类型节目单数据,剩余放基础节目->体育类型的节目单数据(从当前时间到当天结束)
			return getSportCProgram(tid, page, limit, list, operators);
		} else if ("iptv_yl".equals(typeCode)) {// 娱乐
			return getBasicCProgram(page, limit, list, "143", operators);
		} else if ("iptv_dsj".equals(typeCode)) {// 电视剧
			return getBasicCProgram(page, limit, list, "140", operators);
		} else if ("iptv_local".equals(typeCode)) {// 本地
			oc.setIsLocal("1");
		} else {// 其他类型,如央视，卫视
			List<CType> types = cTypeMapper.findCTypeByTypeId(tid);
			if (CollectionUtils.isNotEmpty(types)) {
				List<String> temp = new ArrayList<String>();
				for (CType type : types) {
					temp.add(type.getChannelId());
				}
				channelList = StringUtils.join(temp, ",");
				temp = null;
			}
			types = null;
			log.debug("查询直播节目单取得其他类型时间：" + DateUtil.dateDiff(date, new Date()));
		}

		log.debug("查询运营商时间：" + DateUtil.dateDiff(date, new Date()));
		oc.setOperators(operators);
		oc.setStatus("1");
		oc.setCurrentPage(Integer.valueOf(page));
		oc.setShowCount(Integer.valueOf(limit));
		oc.setOrderColumn("pc.sequence");
		if (StringUtil.isNotEmpty(channelList)) {
			oc.setChannelList(channelList);
		}
		int count = operatorsChannelMapper.getCountLiveChannel(oc);
		log.debug("查询运营商频道总记录时间：" + DateUtil.dateDiff(date, new Date()));
		oc.setTotalResult(count);
		List<OperatorsChannel> ocs = operatorsChannelMapper
				.findLiveChannelByPage(oc);
		log.debug("分页查询频道直播节目单时间：" + DateUtil.dateDiff(date, new Date()));
		list = getLiveChannel(ocs);
		jsonObject.put("total", count);
		jsonObject.put("pageCount", oc.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		list = null;
		ocs = null;
		return jsonObject.toString();
	}

	/**
	 * 取得频道正在播放节目内容
	 * 
	 * 创建人：pananz 创建时间：2015-9-21
	 * 
	 * @param @param ocs
	 * @param @return 返回类型：List<ResponseCPrograms>
	 * @exception throws
	 */
	private List<ResponseCPrograms> getLiveChannel(List<OperatorsChannel> ocs) {
		List<ResponseCPrograms> list = new ArrayList<ResponseCPrograms>();
		for (OperatorsChannel oc : ocs) {
			ResponseCPrograms rc = new ResponseCPrograms();
			rc.setChannelId(oc.getChannelId());
			rc.setChannelName(oc.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(oc.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setLocalCid(oc.getPlayChannelId());
			rc.setSingleLiveServer(oc.getSingleLiveServer());
			Programs liveProgram = oc.getLiveProgram();
			if (liveProgram != null) {
				rc.setProgramId(liveProgram.getId());
				if (StringUtil.isEmpty(liveProgram.getBasicName())) {
					rc.setProgramName(liveProgram.getName());
				} else {
					rc.setProgramName(liveProgram.getBasicName());
				}
				rc.setPlayDtime(liveProgram.getPlayTime());
				rc.setTimeOut(liveProgram.getTimeLongth());
				rc.setDbPlay(liveProgram.getDbPlay());
				if (!"0".equals(liveProgram.getQueue())) {
					rc.setQueue(liveProgram.getQueue());
				}

			}
			list.add(rc);
		}
		return list;
	}

	/**
	 * 取得返回节目数据格式内容 创建人：pananz 创建时间：2014-9-24 - 下午4:06:28
	 * 
	 * @param pList
	 * @return 返回类型：List<ResponseCPrograms>
	 * @exception throws
	 */
	private List<ResponseCPrograms> getCprograms(List<Programs> pList) {
		List<ResponseCPrograms> list = new ArrayList<ResponseCPrograms>();
		for (Programs p : pList) {
			ResponseCPrograms rc = new ResponseCPrograms();
			rc.setChannelId(p.getChannelId());
			rc.setChannelName(p.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(p.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setProgramId(p.getId());
			if (StringUtil.isEmpty(p.getBasicName())) {
				rc.setProgramName(p.getName());
			} else {
				rc.setProgramName(p.getBasicName());
			}
			rc.setPlayDtime(p.getPlayTime());
			rc.setTimeOut(p.getTimeLongth());
			rc.setLocalCid(p.getLocalCid());
			rc.setSingleLiveServer(p.getSingleLiveServer());
			rc.setDbPlay(p.getDbPlay());
			if (!"0".equals(p.getQueue())) {
				rc.setQueue(p.getQueue());
			}
			list.add(rc);
		}
		return list;
	}

	/**
	 * 取得体育节目单内容 创建人：pananz 创建时间：2014-9-24 - 下午4:05:53
	 * 先查询体育频道当前的直播节目再+当前时间到当天结束的体育类型节目
	 * 
	 * @param tid
	 * @param page
	 * @param limit
	 * @param list
	 * @param programs
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String getSportCProgram(String tid, String page, String limit,
			List<ResponseCPrograms> list, String operators) {
		JSONObject jsonObject = new JSONObject();
		Programs programs = new Programs();
		List<CType> types = cTypeMapper.findCTypeByTypeId(tid);
		if (CollectionUtils.isNotEmpty(types)) {
			List<String> temp = new ArrayList<String>();
			for (CType type : types) {
				temp.add(type.getChannelId());
			}
			programs.setChannelList(StringUtils.join(temp, ","));
			temp = null;
		}
		types = null;
		programs.setPlayNow("yes");
		programs.setCurrentPage(Integer.valueOf(page));
		programs.setShowCount(Integer.valueOf(limit));
		programs.setcStatus("1");
		programs.setOperators(operators);
		int totalResult = programsMapper.getCountCProgramsOperators(programs);
		programs.setTotalResult(totalResult);
		List<Programs> pList = programsMapper
				.findCProgramsByPageAndOperators(programs);
		if (pList.size() == Integer.valueOf(limit)) {
			list = getCprograms(pList);
			jsonObject.put("total", programs.getTotalResult());
			jsonObject.put("pageCount", programs.getTotalPage());
			JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
			jsonObject.put("result", jsonArray.toString());
			pList = null;
			list = null;
			jsonObject.put("returnCode", "0");
			return jsonObject.toString();
		} else {
			if ("1".equals(page)) {
				list = getCprograms(pList);
			}
			return getBasicCProgram(page, limit, list, "144", operators);
		}
	}

	/**
	 * 取得特殊类型节目 创建人：pananz 创建时间：2014-9-24 - 上午11:24:43
	 * 
	 * 查询时间范围（当前时间到当天结束时间）
	 * 
	 * @param page
	 * @param limit
	 * @param list
	 * @param ptype
	 * @return 返回类型：String
	 * @exception throws
	 */
	private String getBasicCProgram(String page, String limit,
			List<ResponseCPrograms> list, String ptype, String operators) {
		Date date = new Date();
		JSONObject jsonObject = new JSONObject();
		BasicCProgram bp = new BasicCProgram();
		bp.setPtype(ptype);
		bp.setCurrentPage(Integer.valueOf(page));
		bp.setShowCount(Integer.valueOf(limit) - list.size());
		bp.setPlayTimeMin(DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm"));
		bp.setPlayTimeMax(DateUtil.toString(
				DateUtil.setTime(new Date(), 23, 59, 0), "yyyy-MM-dd HH:mm"));
		bp.setOrderColumn("playTime");
		bp.setOperators(operators);
		int totalResult = basicProgramMapper.getCountBasicCProgram(bp);
		bp.setTotalResult(totalResult);
		List<BasicCProgram> bps = basicProgramMapper
				.findBasicCProgramByPage(bp);
		log.debug("查询频道运营商直播节目单取得特殊类型时间：" + DateUtil.dateDiff(date, new Date()));
		int sum = 0;
		if ("1".equals(page)) {
			sum = list.size();
		}

		for (BasicCProgram bcp : bps) {
			ResponseCPrograms rc = new ResponseCPrograms();
			rc.setChannelId(bcp.getChannelId());
			rc.setChannelName(bcp.getChannelName());
			String logo = ChannelCache
					.getChannelLogoUrlById(bcp.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setProgramId(bcp.getProgramId());
			if (StringUtil.isEmpty(bcp.getBasicName())) {
				rc.setProgramName(bcp.getProgramName());
			} else {
				rc.setProgramName(bcp.getBasicName());
			}
			rc.setPlayDtime(bcp.getPlayTime());
			rc.setTimeOut(bcp.getTimeLongth());
			rc.setLocalCid(bcp.getLocalCid());
			rc.setSingleLiveServer(bcp.getSingleLiveServer());
			rc.setDbPlay(bcp.getDbPlay());
			if (!"0".equals(bcp.getQueue())) {
				rc.setQueue(bcp.getQueue());
			}
			list.add(rc);
		}
		jsonObject.put("total", sum + bp.getTotalResult());
		jsonObject.put("pageCount", bp.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		bps = null;
		list = null;
		log.debug("查询直播节目单取得特殊类型最后时间：" + DateUtil.dateDiff(date, new Date()));
		return jsonObject.toString();
	}
}
