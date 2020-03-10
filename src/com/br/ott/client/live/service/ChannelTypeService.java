package com.br.ott.client.live.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.live.entity.CType;
import com.br.ott.client.live.entity.ChannelType;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.mapper.CTypeMapper;
import com.br.ott.client.live.mapper.ChannelTypeMapper;

/* 
 *  
 *  文件名：ChannelTypeService.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午7:41:36
 */
@Service
public class ChannelTypeService {
	private static final Logger logger = Logger
			.getLogger(ChannelTypeService.class);
	@Autowired
	private ChannelTypeMapper channelTypeMapper;

	@Autowired
	private CTypeMapper cTypeMapper;

	public void addChannelType(ChannelType channelType) {
		channelTypeMapper.addChannelType(channelType);
	}

	public void updateChannelType(ChannelType channelType) {
		channelTypeMapper.updateChannelType(channelType);
	}

	public List<ChannelType> findChannelTypeByPage(ChannelType channelType) {
		int totalResult = channelTypeMapper.getCountChannelType(channelType);
		channelType.setTotalResult(totalResult);
		return channelTypeMapper.findChannelTypeByPage(channelType);
	}

	public List<ChannelType> findChannelTypeByCond(ChannelType channelType) {
		return channelTypeMapper.findChannelTypeByCond(channelType);
	}

	public ChannelType getChannelTypeById(String id) {
		return channelTypeMapper.getChannelTypeById(id);
	}

	public void reloadChannelType() {
		ChannelType channelType = new ChannelType();
		channelType.setStatus("1");
		List<ChannelType> list = channelTypeMapper
				.findChannelTypeByCond(channelType);
		ChannelCache.reloadChannelType(list);
		list = null;

		List<CityOperators> opList = OperatorsCache.opList;
		Map<String, String> ctMap = new HashMap<String, String>();
		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		for (CityOperators co : opList) {
			ChannelType ct = new ChannelType();
			ct.setStatus("1");
			ct.setOrderColumn("sequence");
			ct.setOperators(co.getId());
			List<ChannelType> ctList = channelTypeMapper
					.findChannelTypeByCond(ct);
			for (ChannelType type : ctList) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("id", type.getId());
				jsonMap.put("name", type.getName());
				jsonMap.put("code", type.getCode());
				tempList.add(jsonMap);
			}
			logger.debug(co.getCode() + "运营商频道类型:"
					+ JSONSerializer.toJSON(tempList).toString());
			ctMap.put(co.getCode(), JSONSerializer.toJSON(tempList).toString());
			tempList.clear();
		}
		tempList=null;
		ChannelCache.reloadChannelType(ctMap);
	}

	public void updateChannelTypeStatus(String status, String id) {
		channelTypeMapper.updateChannelTypeStatus(status, id);
	}

	public List<ChannelType> findChannelTypeByCid(String channelId,
			String operators) {
		ChannelType channelType = new ChannelType();
		channelType.setStatus("1");
		channelType.setOperators(operators);
		channelType.setIsSpecial("2");
		channelType.setOrderColumn("sequence");
		List<ChannelType> list = channelTypeMapper
				.findChannelTypeByCond(channelType);
		List<CType> cTypes = cTypeMapper.findCTypeByChannelId(channelId);
		List<ChannelType> types = new ArrayList<ChannelType>();
		List<String> list2 = new ArrayList<String>();
		for (CType cType : cTypes) {
			list2.add(cType.getTypeId());
		}
		for (ChannelType ct : list) {
			if (list2.contains(ct.getId())) {
				ct.setCheck(true);
			} else {
				ct.setCheck(false);
			}
			types.add(ct);
		}
		return types;
	}

	public boolean findChannelTypeByTypeAndName(String type, String name) {
		List<ChannelType> list = channelTypeMapper
				.findChannelTypeByTypeAndName(type, name);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}

	public List<CType> findCTypeByTypeId(String typeId) {
		return cTypeMapper.findCTypeByTypeId(typeId);
	}

	public void addCTypeList(CType cType, String oldType) {
		List<String> newList = Arrays.asList(cType.getcList().split(","));
		List<String> oldList = Arrays.asList(oldType.split(","));
		if (oldList.containsAll(newList) && newList.containsAll(oldList)) {  //判断两个List集合对象是否一样，必须两个List同时对对方调用containsAll方法
		} else {
			cTypeMapper.delCTypeByTypeId(cType.getTypeId());
			if (CollectionUtils.isNotEmpty(newList)) {
				for (String channelId : newList) {
					CType ct = new CType(channelId, cType.getTypeId());
					cTypeMapper.addCType(ct);
				}
			}
		}
	}
}
