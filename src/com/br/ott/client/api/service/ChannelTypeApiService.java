package com.br.ott.client.api.service;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.br.ott.client.common.ChannelCache;
import com.br.ott.common.exception.OTTException;

/* 
 *  
 *  文件名：ChannelTypeApiService.java
 *  创建人：pananz
 *  创建时间：2014-9-24 - 上午11:50:36
 */
@Service
public class ChannelTypeApiService {

	/**
	 * 查询频道类型 创建人：pananz 创建时间：2014-7-12 - 上午11:44:53
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws OTTException
	 *             返回类型：String
	 * @exception throws
	 */
	public String findType(String operators) throws OTTException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnCode", "0");
		jsonObject.put("result", ChannelCache.getChannelTypeByOperators(operators));
		return jsonObject.toString();
	}

}
