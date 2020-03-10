package com.br.ott.client.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.br.ott.client.group.entity.GroupChannel;
import com.br.ott.client.group.entity.UserGroup;
import com.br.ott.common.util.StringUtil;

/**
 * 文件名：GroupCache.java 版权：BoRuiCubeNet. Copyright 2014-2015,All rights reserved
 * 公司名称：青岛博瑞立方科技有限公司 创建人：pananz 创建时间：2016-10-12
 */
public class GroupCache {

	private static final Logger logger = Logger.getLogger(GroupCache.class);
	public static List<UserGroup> ugList = new ArrayList<UserGroup>();

	public static List<GroupChannel> gcList = new ArrayList<GroupChannel>();

	public static void reload(List<UserGroup> nodes) {
		logger.debug("sysCache内容的用户组更新");
		ugList = nodes;
	}

	public static List<UserGroup> findUserGroup() {
		return ugList;
	}

	public static UserGroup getUserGroupById(String id) {
		if (StringUtil.isEmpty(id))
			return null;
		UserGroup ug = null;
		for (UserGroup node : ugList) {
			if (id.equals(node.getId())) {
				ug = node;
				break;
			}
		}
		return ug;
	}

	public static List<UserGroup> findUserGroupByOperators(String operators) {
		if (StringUtil.isEmpty(operators))
			return null;
		List<UserGroup> list = new ArrayList<UserGroup>();
		for (UserGroup node : ugList) {
			if (operators.equals(node.getOperators())) {
				list.add(node);
			}
		}
		return list;
	}

	public static String getUserGroupNameByCode(String operators,
			String groupCode) {
		if (StringUtil.isEmpty(operators) || StringUtil.isEmpty(groupCode))
			return "";
		String name = "";
		for (UserGroup node : ugList) {
			if (operators.equals(node.getOperators())
					&& groupCode.equals(node.getGroupCode())) {
				name = node.getGroupName();
				break;
			}
		}
		return name;
	}

}
