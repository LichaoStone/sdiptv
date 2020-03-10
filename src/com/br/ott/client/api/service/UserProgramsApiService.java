package com.br.ott.client.api.service;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.UserPrograms;
import com.br.ott.client.live.mapper.UserProgramsMapper;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.exception.SystemExceptionCode.ApiCode;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：UserProgramsApiService.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午11:01:45
 */
@Service
public class UserProgramsApiService {

	@Autowired
	private UserProgramsMapper userProgramsMapper;

	/**
	 * 用户预定/取消预定 节目接口 创建人：pananz 创建时间：2014-6-30 - 下午11:06:52
	 * 
	 * @param request
	 *            uid Varchar（32） 用户唯一标识 是 operate Varchar（2） 操作动作：1，预定2，取消预定 是
	 *            programIds Varchar（32） 节目id集合，用“#”分隔多个 是
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String reserveProgram(JSONObject json) throws OTTException {
		String uid = "";
		String operate = "";
		String programIds = "";
		try {
			uid = StringUtil.getJsonStr(json, "uid");
			operate = StringUtil.getJsonStr(json, "operate");
			programIds = StringUtil.getJsonStr(json, "programIds");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OTTException(ApiCode.DECODE_JSON_ERROR);
		}
		String[] arr = programIds.split(",");
		StringBuffer sb = new StringBuffer();
		if ("1".equals(operate)) {// 预定
			for (String programId : arr) {
				UserPrograms up = userProgramsMapper
						.getUserProgramsByUidAndPid(uid, programId);
				if (up != null) {
					if ("2".equals(up.getType())) {// 有此记录，且为取消预定时，进行更新操作
						userProgramsMapper.updateType(uid, programId, "1");
					} else {
						sb.append(programId + ",");
					}
				} else {
					UserPrograms userPrograms = new UserPrograms();
					userPrograms.setUid(uid);
					userPrograms.setProgramsId(programId);
					userPrograms.setType(operate);
					userProgramsMapper.addUserPrograms(userPrograms);
				}
			}
		} else {// 取消预定
			for (String programId : arr) {
				UserPrograms up = userProgramsMapper
						.getUserProgramsByUidAndPid(uid, programId);
				if (up != null) {
					if ("1".equals(up.getType())) {
						userProgramsMapper.updateType(uid, programId, "2");
					} else {
						sb.append(programId + ",");
					}
				}
			}
		}
		JSONObject returnJson = new JSONObject();
		returnJson.put("returnCode", "0");
		if (StringUtil.isNotEmpty(sb.toString())) {
			// 找不到此记录
			String msg = sb.toString();
			returnJson.put("result", "1");
			returnJson.put("msg",
					"已存在预定节目:" + msg.substring(0, msg.length() - 1));
			return returnJson.toString();
		}
		returnJson.put("result", "0");
		return returnJson.toString();
	}
}
