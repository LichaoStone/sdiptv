/*
 * @# RoleToPartner.java Aug 29, 2012 11:10:55 AM
 * 
 * Copyright (C) 2011 - 2012 博瑞立方
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.client.partner.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.br.ott.client.shiro.entity.Role;

/*
 * @author pananz
 * TODO
 * 角色对应操作合作伙伴状态
 */
public enum RoleToPartner {

	PARTNER("PARTNER", "3,6,10,11"), MANAGER("MANAGER", "1,2"), BUSINESS("BUSINESS", "4,5"), CONTRACT("CONTRACT",
			"7,8,9");

	private String name;
	private String index;

	private RoleToPartner(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public static boolean getEditable(String index, String name) {
		for (RoleToPartner rpt : RoleToPartner.values()) {
			if (name.equals(rpt.name)) {
				String[] array = rpt.index.split(",");
				List<String> list = Arrays.asList(array);
				if (list.contains(index)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean getEditable(String index, List<Role> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		List<String> names = new ArrayList<String>();
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			names.add(role.getEnname());
		}
		if (CollectionUtils.isEmpty(names)) {
			return false;
		}
		if (names.contains(RoleToPartner.PARTNER.name)) {
			String[] array = RoleToPartner.PARTNER.index.split(",");
			List<String> list = Arrays.asList(array);
			if (list.contains(index)) {
				return true;
			}
		}
		return false;
	}
}
