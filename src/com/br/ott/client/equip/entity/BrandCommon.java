package com.br.ott.client.equip.entity;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/* 
 *  常用品牌
 *  文件名：BrandCommon.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-1 - 下午4:37:19
 */
public class BrandCommon extends Pagination {
	private String id;
	// 品牌ID
	private String brandId;
	// 品牌名称
	private String brandName;
	// 硬件类型
	private String machType;
	// 排序
	private String sequence;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public BrandCommon() {
		super();
	}

	public BrandCommon(String brandId, String machType) {
		super();
		this.brandId = brandId;
		this.machType = machType;
	}

	public String getMachTypeName() {
		if (StringUtil.isEmpty(this.machType)) {
			return "";
		} else {
			Dictionary dictionary = DictCache.getDictValue(DicType.EQUIP_YJXH,
					machType);
			return null == dictionary ? "" : dictionary.getDicName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMachType() {
		return machType;
	}

	public void setMachType(String machType) {
		this.machType = machType;
	}

}
