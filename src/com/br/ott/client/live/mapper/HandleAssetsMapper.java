package com.br.ott.client.live.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HandleAssetsMapper {
	
	public void addPrograms_log(Map<String, String> paramsMap);
		
	void delProgramsByIds(Map<String, Object> paramsMap);
	
	void delProgramsByCidAndDate(@Param("channelId") String channelId, @Param("day") String day);
	
	void addProgramsByMap(Map<String, Object> paramsMap);
	
	List<Map<String, Object>> queryProgramsByIdAndDate(Map<String, Object> paramsMap);
	
	void delProgramBy7Date(@Param("playTime") String playTime);
}