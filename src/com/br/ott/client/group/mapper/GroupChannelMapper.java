package com.br.ott.client.group.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.group.entity.GroupChannel;

public interface GroupChannelMapper {
	/**
	*按分页查询
	*/
	List<GroupChannel> findGroupChannelByPage(GroupChannel mGroupChannel);
	/**
	*按统计总记录数
	*/
	int getCountGroupChannel(GroupChannel mGroupChannel);
	
	/**
	*按ID查询
	*/
	GroupChannel findGroupChannelById(String id);
	
	/**
	*增加实体
	*/
	void insertGroupChannel(GroupChannel mGroupChannel);
	
	/**
	*修改实体
	*/
	void updateGroupChannel(GroupChannel mGroupChannel);
	
	/**
	*按条件查询
	*/
	List<GroupChannel> findGroupChannelByCond(GroupChannel mGroupChannel);
	
	/**
	*按ID集合删除
	*/
	void deleteGroupChannelByList(List<String> ids);
	
	/**
	*按ID删除
	*/
	void deleteGroupChannelById(String id);
	
	/**
	 * 更新状态
	 * 
	 * 创建人：pananz 创建时间：2016-10-12
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void updateChannelStatus(@Param("status") String status,
			@Param("id") String id);
	/**
	 * 修改排序值
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-12-22
		 * 
		 * @param @param groupChannel
		 * 返回类型：void
		 * @exception throws
	 */
	void updateSequence(GroupChannel groupChannel);
}