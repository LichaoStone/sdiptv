package com.br.ott.client.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.api.entity.ResponseChannel;
import com.br.ott.client.api.entity.ResponseSearch;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.ChannelLog;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.entity.SearchLog;
import com.br.ott.client.live.entity.UserFollow;
import com.br.ott.client.live.mapper.CTypeMapper;
import com.br.ott.client.live.mapper.ChannelLogMapper;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;
import com.br.ott.client.live.mapper.SearchLogMapper;
import com.br.ott.client.live.mapper.UserFollowMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.exception.SystemExceptionCode.ApiCode;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ChannelApiService.java
 *  创建人：pananz
 *  创建时间：2014-9-24 - 上午11:46:49
 */
@Service
public class ChannelApiService {

	private static Logger log = Logger.getLogger(ChannelApiService.class);

	@Autowired
	private CTypeMapper cTypeMapper;

	@Autowired
	private ChannelLogMapper channelLogMapper;

	@Autowired
	private UserFollowMapper userFollowMapper;

	@Autowired
	private SearchLogMapper searchLogMapper;

	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;

	/**
	 * 频道列表获取接口 创建人：pananz 创建时间：2014-7-1 - 下午12:17:12
	 * 
	 * @param request
	 *            tid Varchar（8） 类型id，0:所有,-1热门,n其他 ... 99，收藏 100，最近收看 cname
	 *            Varchar（128） 频道名称，模糊检索时用 否 page Varchar（8） 页码 否（默认为1） limit
	 *            Varchar（8） ...每页显示几行 否（默认10）
	 * 
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String findCollects(JSONObject object) {
		String operators = object.get("operators") == null ? "" : (String)object.get("operators");
		String tid = object.get("tid") == null ? "" : (String)object.get("tid");
		String cname = object.get("cname") == null ? "" : (String)object.get("cname");
		String page = object.get("page") == null ? "1" : (String)object.get("page");
		String limit = object.get("limit") == null ? "15" : (String)object.get("limit");
		String uid = object.get("uid") == null ? "" : (String)object.get("uid");
		
		log.debug("查询频道参数 tid=" + tid + " cname=" + cname + " page=" + page
				+ " limit=" + limit + " operators=" + operators);
		// 0:所有,-1热门,n其他 ... 99，收藏 100，最近收看
		JSONObject jsonObject = new JSONObject();

		List<ResponseChannel> list = new ArrayList<ResponseChannel>();
		if ("99".equals(tid) || "100".equals(tid)) {// 99，收藏 100，最近收看
			UserFollow uf = getFollow(tid, uid, page, limit, list, operators);
			jsonObject.put("total", uf.getTotalResult());
			jsonObject.put("pageCount", uf.getTotalPage());
			JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
			jsonObject.put("result", jsonArray.toString());
		} else if ("-1".equals(tid)) {// 热门
			jsonObject = getHotChannel(page, limit, jsonObject, list);
		} else {
			jsonObject = getChannel(tid, page, limit, cname, operators,
					jsonObject, list);
		}
		jsonObject.put("returnCode", "0");
		list = null;
		return jsonObject.toString();
	}

	/**
	 * 查询关注/收藏的频道
	 * 
	 * 创建人：pananz 创建时间：2015-6-2 - 下午5:38:06
	 * 
	 * @param tid
	 * @param uid
	 * @param page
	 * @param limit
	 * @param areaId
	 * @param operators
	 * @param list
	 * @return 返回类型：UserFollow
	 * @exception throws
	 */
	private UserFollow getFollow(String tid, String uid, String page,
			String limit, List<ResponseChannel> list, String operators) {
		UserFollow uf = new UserFollow();
		uf.setCurrentPage(Integer.valueOf(page));
		uf.setShowCount(Integer.valueOf(limit));
		uf.setUid(uid);
		uf.setcStatus("1");
		if ("99".equals(tid)) {
			uf.setType("1");
		} else {
			uf.setType("2");
		}
		uf.setOrderColumn("uf.createTime desc");
		List<UserFollow> ups = null;
		if (StringUtil.isNotEmpty(operators)) {
			uf.setOperators(operators);
			int totalResult = userFollowMapper.getCountFollowOperators(uf);
			uf.setTotalResult(totalResult);
			ups = userFollowMapper.findFollowOperatorsByPage(uf);
		} else {
			int totalResult = userFollowMapper.getCountUserFollow(uf);
			uf.setTotalResult(totalResult);
			ups = userFollowMapper.findUserFollowByPage(uf);
		}
		for (UserFollow u : ups) {
			ResponseChannel rc = new ResponseChannel();
			rc.setChannelId(u.getChannelId());
			rc.setChannelName(u.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(u.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setLocalCid(u.getLocalCid());
			list.add(rc);
		}
		ups = null;
		return uf;
	}

	/**
	 * 查询热门频道
	 * 
	 * 创建人：pananz 创建时间：2015-6-2 - 下午6:15:49
	 * 
	 * @param page
	 * @param limit
	 * @param areaId
	 * @param operators
	 * @param jsonObject
	 * @param list
	 * @return 返回类型：JSONObject
	 * @exception throws
	 */
	private JSONObject getHotChannel(String page, String limit,
			JSONObject jsonObject, List<ResponseChannel> list) {
		ChannelLog channelLog = new ChannelLog();
		channelLog.setCurrentPage(Integer.valueOf(page));
		channelLog.setShowCount(Integer.valueOf(limit));
		channelLog.setOrderColumn("realNumber+virtualNumber desc");
		List<ChannelLog> cls = channelLogMapper
				.findChannelLogByPage(channelLog);
		for (ChannelLog cl : cls) {
			ResponseChannel rc = new ResponseChannel();
			rc.setChannelId(cl.getChannelId());
			OperatorsChannel channel = cl.getChannel();
			if (channel != null) {
				rc.setChannelName(channel.getChannelName());
				String logo = ChannelCache.getChannelLogoUrlById(cl
						.getChannelId());
				if (StringUtil.isNotEmpty(logo)) {
					rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
				}
				rc.setLocalCid(channel.getPlayChannelId());
				list.add(rc);
			}
		}
		jsonObject.put("total", channelLog.getTotalResult());
		jsonObject.put("pageCount", channelLog.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		cls = null;
		return jsonObject;
	}

	/**
	 * 按频道类型查询
	 * 
	 * 创建人：pananz 创建时间：2015-6-2 - 下午6:16:05
	 * 
	 * @param tid
	 * @param page
	 * @param limit
	 * @param cname
	 * @param areaId
	 * @param operators
	 * @param jsonObject
	 * @param list
	 * @return 返回类型：JSONObject
	 * @exception throws
	 */
	private JSONObject getChannel(String tid, String page, String limit,
			String cname, String operators, JSONObject jsonObject,
			List<ResponseChannel> list) {
		OperatorsChannel channel = new OperatorsChannel();
		String typeCode = ChannelCache.getTypeCodeById(tid);
		if ("iptv_local".equals(tid)) {// 本地
			channel.setIsLocal("1");
		} else if ("iptv_all".equals(typeCode)) {// 手机所有

		} else {// 其他类型
			List<CType> types = cTypeMapper.findCTypeByTypeId(tid);
			if (CollectionUtils.isNotEmpty(types)) {
				List<String> temp = new ArrayList<String>();
				for (CType type : types) {
					temp.add(type.getChannelId());
				}
				channel.setChannelList(StringUtils.join(temp, ","));
				temp = null;
			} else {
				return jsonObject;
			}
			types = null;
		}
		channel.setChannelName(cname);
		channel.setCurrentPage(Integer.valueOf(page));
		channel.setShowCount(Integer.valueOf(limit));
		channel.setStatus("1");
		channel.setOperators(operators);
		channel.setOrderColumn("oc.playChannelId");

		int count = operatorsChannelMapper.getCountOperatorsChannel(channel);
		channel.setTotalResult(count);
		List<OperatorsChannel> channels = operatorsChannelMapper
				.findOperatorsChannelByPage(channel);
		for (OperatorsChannel c : channels) {
			ResponseChannel rc = new ResponseChannel();
			rc.setChannelId(c.getChannelId());
			rc.setChannelName(c.getChannelName());
			String logo = ChannelCache.getChannelLogoUrlById(c.getChannelId());
			if (StringUtil.isNotEmpty(logo)) {
				rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
			}
			rc.setLocalCid(c.getPlayChannelId());
			list.add(rc);
		}
		jsonObject.put("total", channel.getTotalResult());
		jsonObject.put("pageCount", channel.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		channels = null;
		list = null;
		return jsonObject;
	}

	/**
	 * 查询热门搜索内容 创建人：pananz 创建时间：2014-7-23 - 下午11:02:47
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String findSearchLog(JSONObject object){
		String page = object.get("page") == null ? "1" : (String)object.get("page");
		String limit = object.get("limit") == null ? "10" : (String)object.get("limit");
		SearchLog searchLog = new SearchLog();
		searchLog.setCurrentPage(Integer.valueOf(page));
		searchLog.setShowCount(Integer.valueOf(limit));
		searchLog.setStatus("1");
		searchLog.setOrderColumn("realNumber+virtualNumber desc");
		List<SearchLog> list = searchLogMapper.findSearchLogByPage(searchLog);
		List<ResponseSearch> responseSearchs = new ArrayList<ResponseSearch>();
		for (SearchLog sl : list) {
			ResponseSearch rs = new ResponseSearch(sl.getName(),
					Integer.valueOf(sl.getRealNumber())
							+ Integer.valueOf(sl.getVirtualNumber()));
			responseSearchs.add(rs);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", searchLog.getTotalResult());
		jsonObject.put("pageCount", searchLog.getTotalPage());
		JSONArray jsonArray = (JSONArray) JSONSerializer
				.toJSON(responseSearchs);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		list = null;
		responseSearchs = null;
		return jsonObject.toString();
	}

	/**
	 * 点播频道记录接口 创建人：pananz 创建时间：2014-8-9 - 下午12:28:11
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String addChannelLog(HttpServletRequest request, JSONObject json)
			throws OTTException {
		String channelId = "";
		String channelName = "";
		try {
			channelId = json.get("cid") == null ? "1" : (String) json
					.get("cid");
			channelName = json.get("cname") == null ? "1" : (String) json
					.get("cname");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OTTException(ApiCode.DECODE_JSON_ERROR);
		}

		ChannelLog channelLog = channelLogMapper
				.getChannelLogByChannelId(channelId);
		if (channelLog == null) {
			ChannelLog cl = new ChannelLog();
			cl.setChannelId(channelId);
			cl.setChannelName(channelName);
			cl.setRealNumber("1");
			cl.setVirtualNumber("0");
			channelLogMapper.addChannelLog(cl);
		} else {
			channelLogMapper.updateRealNumber(channelLog.getId());
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnCode", "0");
		jsonObject.put("result", "0");
		return jsonObject.toString();
	}

	/**
	 * 根据频道名获取频道logo地址 创建人：pananz 创建时间：2014-9-29 - 上午11:56:37
	 * 
	 * @param request
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String findChannelByNames(String operators, String channelNames)
			throws OTTException {
		String[] cnames = channelNames.split(",");
		List<ResponseChannel> list = new ArrayList<ResponseChannel>();
		for (String name : cnames) {
			List<OperatorsChannel> channels = operatorsChannelMapper
					.findOperatorsChannelByMoreName(operators, name);
			ResponseChannel rc = new ResponseChannel();
			if (CollectionUtils.isEmpty(channels)) {
				rc.setChannelName(name);
				list.add(rc);
				continue;
			}
			OperatorsChannel c = channels.get(0);
			if (c != null) {
				rc.setChannelId(c.getChannelId());
				rc.setChannelName(c.getChannelName());
				String logo = ChannelCache.getChannelLogoUrlById(c
						.getChannelId());
				if (StringUtil.isNotEmpty(logo)) {
					rc.setChannelLogoUrl(Global.SERVER_SOURCE_URL + logo);
				}
				rc.setLocalCid(c.getPlayChannelId());
				list.add(rc);
			}
			channels = null;
		}
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(list);
		jsonObject.put("result", jsonArray.toString());
		jsonObject.put("returnCode", "0");
		list = null;
		return jsonObject.toString();
	}
}
