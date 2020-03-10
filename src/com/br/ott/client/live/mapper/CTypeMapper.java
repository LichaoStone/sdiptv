package com.br.ott.client.live.mapper;

import java.util.List;

import com.br.ott.client.live.entity.CType;

/* 
 *  
 *  文件名：CTypeMapper.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午6:58:02
 */
public interface CTypeMapper {

	void addCType(CType cType);

	void delCTypeByChannelId(String channelId);

	void delCTypeByTypeId(String typeId);

	List<CType> findCTypeByChannelId(String channelId);

	List<CType> findCTypeByTypeId(String typeId);

	List<CType> findCTypeByTypeList(String types);

}
