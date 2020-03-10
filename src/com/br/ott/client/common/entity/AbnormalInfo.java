package com.br.ott.client.common.entity;

import java.io.Serializable;


/**
 * 文件名：AbnormalInfo.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights
 * reserved 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2015-11-30
 */
public class AbnormalInfo implements Serializable {

	private static final long serialVersionUID = -1225957567675850187L;

	// 异常信息ID
	private String id;
	// 1：频道，2：节目,3：频道id+恢复时间集合
	private String type;

	public AbnormalInfo(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public AbnormalInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof AbnormalInfo) {
			AbnormalInfo ai = (AbnormalInfo) obj;
			if (this.id.equals(ai.id)
					&& this.type.equals(ai.type)) {
				return true;
			}
		}
		return false;
	}
}
