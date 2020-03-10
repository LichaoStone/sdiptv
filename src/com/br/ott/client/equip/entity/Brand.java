package com.br.ott.client.equip.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.br.ott.common.util.Pagination;

/* 
 *  硬件品牌
 *  文件名：Brand.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2013-2-4 - 上午11:19:12
 */
public class Brand extends Pagination {
	private String id;
	// 品牌名称
	private String name;
	/** 公司名称 */
	private String factory;
	// 品牌编码
	private String code;
	// 状态：1启用，0停用
	private String status;
	// 是否为推荐品牌 1：是，0否
	private String isRec;

	private String recType;
	private String orderColumn;

	private List<BrandCommon> brandCommons;

	public String getRecType() {
		return recType;
	}

	public String getRecTypeStr() {
		if (CollectionUtils.isNotEmpty(brandCommons)) {
			return org.apache.commons.lang.StringUtils.join(getRecTypes(), "/");
		}
		return "";
	}

	public List<String> getRecTypes() {
		if (CollectionUtils.isNotEmpty(brandCommons)) {
			List<String> recTypes = new ArrayList<String>();
			for (BrandCommon bc : brandCommons) {
				recTypes.add(bc.getMachType());
			}
			return recTypes;
		}
		return null;
	}

	public String getRecTypeNameStr() {
		if (CollectionUtils.isNotEmpty(brandCommons)) {
			List<String> recTypes = new ArrayList<String>();
			for (BrandCommon bc : brandCommons) {
				recTypes.add(bc.getMachTypeName());
			}
			return org.apache.commons.lang.StringUtils.join(recTypes, "/");
		}
		return "";
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	public List<BrandCommon> getBrandCommons() {
		return brandCommons;
	}

	public void setBrandCommons(List<BrandCommon> brandCommons) {
		this.brandCommons = brandCommons;
	}

	public String getIsRec() {
		return isRec;
	}

	public void setIsRec(String isRec) {
		this.isRec = isRec;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
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

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
