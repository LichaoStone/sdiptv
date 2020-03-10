package com.br.ott.client.api.service;


import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.UserFollow;
import com.br.ott.client.live.mapper.UserFollowMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.exception.SystemExceptionCode.ApiCode;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：UserFollowApiService.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:45:03
 */
@Service
public class UserFollowApiService {

	@Autowired
	private UserFollowMapper userFollowMapper;

	/**
	 * 加入收藏或最近收看 创建人：pananz 创建时间：2014-6-30 - 下午10:48:25
	 * 
	 * @param request
	 *            uid Varchar（32） 用户唯一标识 是 operate Varchar（2） 操作动作：1，收藏 2，最近收看 是
	 *            channelid Varchar（32） 频道id 是
	 * 
	 * @return 返回类型：String
	 * @exception throws
	 */
	public String collect(JSONObject json) throws OTTException {
		String uid = "";
		String operate = "";
		String channelId = "";
		String operatorsCode = "";
		try {
			uid = StringUtil.getJsonStr(json, "uid");
			operate = StringUtil.getJsonStr(json, "operate");
			channelId = StringUtil.getJsonStr(json, "channelid");
			operatorsCode = StringUtil.getJsonStr(json, "operators");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OTTException(ApiCode.DECODE_JSON_ERROR);
		}
		if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(operatorsCode)) {
			return "{\"resultCode\":\"0\",\"result\":\"1\", \"msg\":\"参数缺失uid或operators\"}";
		}
		String operators = OperatorsCache.getOperatorsIdByCode(operatorsCode);

		try {
			UserFollow uf = userFollowMapper.getUserFollowByUidAndCid(uid,
					channelId, operate);
			if (uf == null) {
				UserFollow userFollow = new UserFollow();
				userFollow.setUid(uid);
				userFollow.setChannelId(channelId);
				userFollow.setType(operate);
				userFollow.setStatus("1");
				userFollow.setOperators(operators);
				userFollowMapper.addUserFollow(userFollow);
			} else {
				if ("0".equals(uf.getStatus())) {
					userFollowMapper.updateUserFollowStatus("1", uf.getId());
				}
			}
			return "{\"resultCode\":\"0\",\"result\":\"0\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"resultCode\":\"1\",\"result\":\"1\", \"msg\":\"数据异常\"}";

		}
	}

	/**
	 * 用戶是否已收藏某一頻道 创建人：pananz 创建时间：2014-8-12 - 上午10:59:13
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String isCollect(JSONObject json) throws OTTException {
		String uid = "";
		String operate = "";
		String channelId = "";
		try {
			uid = StringUtil.getJsonStr(json, "uid");
			operate = StringUtil.getJsonStr(json, "operate");
			channelId = StringUtil.getJsonStr(json, "channelid");
		} catch (Exception e) {
			throw new OTTException(ApiCode.DECODE_JSON_ERROR);
		}
		if (StringUtil.isEmpty(uid)) {
			return "{\"resultCode\":\"0\",\"result\":\"0\", \"msg\":\"参数缺失uid\"}";
		}
		try {
			UserFollow uf = userFollowMapper.getUserFollowByUidAndCid(uid,
					channelId, operate);
			if (null == uf) {
				return "{\"returnCode\":\"0\",\"result\":\"0\", \"msg\":\"未收藏\"}";
			} else {
				if ("1".equals(uf.getStatus())) {
					return "{\"resultCode\":\"0\",\"result\":\"1\", \"msg\":\"已收藏\"}";
				} else {
					return "{\"resultCode\":\"0\",\"result\":\"0\", \"msg\":\"未收藏\"}";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"resultCode\":\"1\",\"result\":\"-1\", \"msg\":\"数据异常\"}";

		}
	}

	/**
	 * 取消收藏或最近收看 创建人：pananz 创建时间：2014-6-30 - 下午10:57:41
	 * 
	 * @param request
	 *            uid Varchar（32） 用户唯一标识 是 type Varchar（2） 类型：1，收藏 2，最近观看 是
	 *            channelids Varchar（32） 频道id集合，用“#”号分隔多个 是
	 * 
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String unCollect(JSONObject object) throws OTTException {
		String uid = object.get("uid") == null ? "" : (String)object.get("uid");
		String type = object.get("type") == null ? "" : (String)object.get("type");
		String channelids = object.get("channelids") == null ? "" : (String)object.get("channelids");

		try {
			String[] arr = channelids.split(",");
			StringBuffer sb = new StringBuffer();
			for (String channelId : arr) {
				UserFollow uf = userFollowMapper.getUserFollowByUidAndCid(uid,
						channelId, type);
				if (uf == null) {
					sb.append(channelId + ",");
				} else {
					if ("1".equals(uf.getStatus())) {
						userFollowMapper
								.updateUserFollowStatus("0", uf.getId());
					}
				}
			}

			if (StringUtil.isNotEmpty(sb.toString())) {
				// 找不到此记录
				String msg = sb.toString();
				return "{\"returnCode\":\"0\",\"result\":\"1\", \"msg\":\"已存在预定频道"
						+ msg.substring(0, msg.length() - 1) + "\"}";
			}
			return "{\"returnCode\":\"0\",\"result\":\"0\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"returnCode\":\"1\",\"result\":\"1\", \"msg\":\"数据异常\"}";
		}
	}
}
