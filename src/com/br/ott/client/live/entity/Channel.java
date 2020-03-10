package com.br.ott.client.live.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.br.ott.client.common.ChannelCache;
import com.br.ott.common.util.Pagination;

/* 
 *  
 *  文件名：Channel.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  创建人：pananz
 *  创建时间：2014-5-16 - 上午10:34:25
 */
public class Channel extends Pagination implements Serializable {

	/** */
	private static final long serialVersionUID = 7789260377188881572L;
	private String id;
	// 频道名称
	private String name;
	// 上一级频道
	private String parentId;
	// 看电视频道地址
	private String cctvRel;
	// TV搜频道地址
	private String tvsouRel;
	// 频道简介
	private String remark;
	// 状态：1启用，0停用,2 待完善
	private String status;
	// 浩软关联的一个频道id,唯一
	private String cid;
	// 频道类型
	private String type;

	private String typeList;
	// 频道logo图标地址
	private String logoUrl;
	// 频道简介图地址
	private String logoUrl2;
	// 别名(如cctv1,cctv-1,cctv-1综合)
	private String otherName;
	private String orderColumn;
	// 只显示二级及以后的
	private String secondNode;
	private String areaId;
	private boolean isCheck;
	
	private List<CType> cTypes;

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTypes() {
		if (CollectionUtils.isEmpty(cTypes)) {
			return "";
		}
		List<String> list = new ArrayList<String>();
		for (CType t : cTypes) {
			list.add(t.getTypeId());
		}
		return org.apache.commons.lang.StringUtils.join(list, "/");
	}

	public String getTypeNames() {
		if (CollectionUtils.isEmpty(cTypes)) {
			return "";
		}
		List<String> mobilelist = new ArrayList<String>();
		for (CType t : cTypes) {
			mobilelist.add(t.getTypeName());
		}
		return org.apache.commons.lang.StringUtils.join(mobilelist, "/");
	}

	public String getLogoUrl2() {
		return logoUrl2;
	}

	public void setLogoUrl2(String logoUrl2) {
		this.logoUrl2 = logoUrl2;
	}

	public Channel() {
		super();
	}

	public List<CType> getCTypes() {
		return cTypes;
	}

	public void setCTypes(List<CType> cTypes) {
		this.cTypes = cTypes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return ChannelCache.getChannelNameById(parentId);
	}

	public String getCctvRel() {
		return cctvRel;
	}

	public void setCctvRel(String cctvRel) {
		this.cctvRel = cctvRel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getSecondNode() {
		return secondNode;
	}

	public void setSecondNode(String secondNode) {
		this.secondNode = secondNode;
	}

	public String getTvsouRel() {
		return tvsouRel;
	}

	public void setTvsouRel(String tvsouRel) {
		this.tvsouRel = tvsouRel;
	}

	public List<CType> getcTypes() {
		return cTypes;
	}

	public void setcTypes(List<CType> cTypes) {
		this.cTypes = cTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Channel) {
			Channel channel = (Channel) obj;
			if (this.name.equals(channel.name)) {
				return true;
			}
		}
		return false;
	}

}
