package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.SearchLog;
import com.br.ott.client.live.mapper.SearchLogMapper;

/* 
 *  
 *  文件名：SearchLogService.java
 *  创建人：pananz
 *  创建时间：2014-7-23 - 下午10:42:03
 */
@Service
public class SearchLogService {

	@Autowired
	private SearchLogMapper searchLogMapper;

	public void updateVirtualNumber(String rNumber, String id) {
		searchLogMapper.updateVirtualNumber(rNumber, id);
	}

	public List<SearchLog> findSearchLogByPage(SearchLog searchLog) {
		int totalResult=searchLogMapper.getCountSearchLog(searchLog);
		searchLog.setTotalResult(totalResult);
		return searchLogMapper.findSearchLogByPage(searchLog);
	}

	public void updateSearchLogStatus(String status, String id) {
		searchLogMapper.updateSearchLogStatus(status, id);
	}

	public void delSearchLog(String id) {
		searchLogMapper.delSearchLog(id);
	}

	public void delLogList(List<String> ids) {
		searchLogMapper.delLogList(ids);
	}
	/**
	 * 定期删除热门搜索
	 * @author lizhenghg
	 */
	public void delSearchLogForTerminal(){
		this.searchLogMapper.delSearchLogForTerminal();
	}
}
