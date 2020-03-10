package com.br.ott.client.api.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/* 
 *  
 *  文件名：PrgItem.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-22 - 下午3:36:12
 */
@XStreamAlias("prgItem")
public class PrgItem {

	private String time;
	private String name;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrgItem(String time, String name) {
		super();
		this.time = time;
		this.name = name;
	}

}
