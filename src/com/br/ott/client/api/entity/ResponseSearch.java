package com.br.ott.client.api.entity;

import java.io.Serializable;

/* 
 *  
 *  文件名：ResponseSearch.java
 *  创建人：pananz
 *  创建时间：2014-7-23 - 下午10:56:31
*/
public class ResponseSearch implements Serializable {

	/** */
	private static final long serialVersionUID = -4489030867813012133L;
	private String programName;
	private int searchTimes;

	public ResponseSearch() {
		super();
	}

	public ResponseSearch(String programName, int searchTimes) {
		super();
		this.programName = programName;
		this.searchTimes = searchTimes;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getSearchTimes() {
		return searchTimes;
	}

	public void setSearchTimes(int searchTimes) {
		this.searchTimes = searchTimes;
	}

}
