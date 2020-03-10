package com.br.ott.client.equip.entity;

import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.common.annotation.FieldName;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Pagination;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：HardTypeumber.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：Dec 17, 2012 - 4:07:44 PM
 */
public class HardModel extends Pagination {

	private String id;
	@FieldName("型号")
	private String number;
	// 型号是否停用(1:正常使用，0:停用)
	@FieldName("型号状态")
	private String status;
	// 型号类型 如:电视，机顶盒，空调
	@FieldName("机器类型")
	private String machType;
	@FieldName("操作系统")
	private String systemName;
	@FieldName("所属品牌")
	private String brandId;
	@FieldName("排序")
	private int sequence;
	// 品牌名称
	private String brandName;
	// 品牌编码
	private String brandCode;
	// 推荐品牌
	private String recBrand;

	private boolean isCheck;

	private String orderColumn;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getRecBrand() {
		return recBrand;
	}

	public void setRecBrand(String recBrand) {
		this.recBrand = recBrand;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
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

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMachType() {
		return machType;
	}

	public void setMachType(String machType) {
		this.machType = machType;
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

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
