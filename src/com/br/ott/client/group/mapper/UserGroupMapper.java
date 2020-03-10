package com.br.ott.client.group.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.group.entity.UserGroup;

public interface UserGroupMapper {
	/**
	 * 按分页查询
	 */
	List<UserGroup> findUserGroupByPage(UserGroup mUserGroup);

	/**
	 * 按统计总记录数
	 */
	int getCountUserGroup(UserGroup mUserGroup);

	/**
	 * 按ID查询
	 */
	UserGroup findUserGroupById(String id);

	/**
	 * 增加实体
	 */
	void insertUserGroup(UserGroup mUserGroup);

	/**
	 * 修改实体
	 */
	void updateUserGroup(UserGroup mUserGroup);

	/**
	 * 按条件查询
	 */
	List<UserGroup> findUserGroupByCond(UserGroup mUserGroup);

	/**
	 * 按ID集合删除
	 */
	void deleteUserGroupByList(List<String> ids);

	/**
	 * 按ID删除
	 */
	void deleteUserGroupById(String id);

	/**
	 * 更新状态
	 * 
	 * 创建人：pananz 创建时间：2016-10-12
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void updateUserGroupStatus(@Param("status") String status,
			@Param("id") String id);
	
	void updateUserGroupStatus2(@Param("aaaStatus") String aaaStatus,
			@Param("id") String id);
	
	void updateUserGroupStatus3(@Param("lcStatus") String lcStatus,
			@Param("id") String id);
}