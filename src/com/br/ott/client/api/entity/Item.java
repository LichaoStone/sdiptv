package com.br.ott.client.api.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/* 
 *  
 *  文件名：Item.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-22 - 下午3:34:40
 */
@XStreamAlias("item")
public class Item {

	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String date;

	@XStreamImplicit
	private List<PrgItem> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<PrgItem> getPrgItem() {
		return list;
	}

	public void setPrgItem(List<PrgItem> list) {
		this.list = list;
	}

}
