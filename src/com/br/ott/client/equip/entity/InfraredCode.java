package com.br.ott.client.equip.entity;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/* 
 * 品牌型号红外码信息
 * 
 *  文件名：InfraredCode.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:06:29
 */
public class InfraredCode extends Pagination {

	/**
	 * * 品牌 设备类型 设备型号 编码格式 客户码 键值名 键值码 全码 品牌指厂商品牌：如海信，海尔，长虹，等
	 * 设备类型：电视，机顶盒，空调等（加入数据字典即可） 设备型号：从设备信息表中获取，根据品牌和类型联动 编码格式：NEC，RC5，RC6，SONY
	 * SIRC 等固定的值 全码：按不同的编码格式由客户端提供规则生成对应的全码值
	 */
	private String id;
	// 品牌id
	private String brandId;
	// 品牌编码
	private String brandCode;
	// 品牌名称
	private String brandName;
	// 设备类型
	private String type;
	// 设备型号
	private String modelName;
	// 编码格式
	private String codeFormat;
	// 客户码
	private String clientCode;
	// 键值名
	private String keyName;
	// 键值码
	private String keyCode;
	// 全码
	private String allCode;
	// 排序
	private String sequence;

	private String status;

	private int modelSequence;
	private String orderColumn;

	public int getModelSequence() {
		return modelSequence;
	}

	public void setModelSequence(int modelSequence) {
		this.modelSequence = modelSequence;
	}

	public String getTypeOtherName() {
		String typeName = getTypeName();
		if ("有线机顶盒_卫星_IPTV".equals(typeName)) {
			typeName = "机顶盒-";
		} else if ("智能机顶盒_Android".equals(typeName)) {
			typeName = "智能机顶盒";
		} else if ("电视机".equals(typeName)) {
			typeName = "电视";
		}
		return typeName;
	}

	public String getTypeName() {
		if (StringUtil.isEmpty(this.type)) {
			return "";
		} else {
			Dictionary dictionary = DictCache.getDictValue(DicType.EQUIP_YJXH,
					type);
			return null == dictionary ? "" : dictionary.getDicName();
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCodeFormat() {
		return codeFormat;
	}

	public void setCodeFormat(String codeFormat) {
		this.codeFormat = codeFormat;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getAllCode() {
		return allCode;
	}

	public void setAllCode(String allCode) {
		this.allCode = allCode;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

}
