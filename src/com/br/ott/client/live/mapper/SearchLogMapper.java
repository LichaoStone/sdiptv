package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.SearchLog;

/* 
 *  
 *  文件名：SearchLogMapper.java
 *  创建人：pananz
 *  创建时间：2014-7-23 - 下午10:29:39
 */
public interface SearchLogMapper {

	void addSearchLog(SearchLog searchLog);

	void updateRealNumber(String id);

	void updateVirtualNumber(@Param("virtualNumber") String virtualNumber,
			@Param("id") String id);

	List<SearchLog> findSearchLogByPage(SearchLog searchLog);
	int getCountSearchLog(SearchLog searchLog);
	
	List<SearchLog> findSearchLogByName(String name);

	void updateSearchLogStatus(@Param("status") String status,
			@Param("id") String id);

	void delSearchLog(String id);

	void delLogList(List<String> ids);
	
	void delSearchLogForTerminal();
}
