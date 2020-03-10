package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.UserFollow;

/* 
 *  
 *  文件名：UserFollowMapper.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:34:49
 */
public interface UserFollowMapper {

	void addUserFollow(UserFollow userFollow);

	void updateUserFollowStatus(@Param("status") String status,
			@Param("id") String id);

	UserFollow getUserFollowByUidAndCid(@Param("uid") String uid,
			@Param("channelId") String channelId, @Param("type") String type);

	List<UserFollow> findUserFollowByCond(UserFollow uf);

	List<UserFollow> findUserFollowByPage(UserFollow uf);

	int getCountUserFollow(UserFollow uf);

	List<UserFollow> findUserFollowOperatorsByPage(UserFollow uf);

	int getCountUserFollowByOperators(UserFollow uf);

	/**
	 * 查询频道
	 * 
	 * 创建人：pananz 创建时间：2015-9-23
	 * 
	 * @param @param uf
	 * @param @return 返回类型：List<UserFollow>
	 * @exception throws
	 */
	List<UserFollow> findFollowOperatorsByPage(UserFollow uf);
	int getCountFollowOperators(UserFollow uf);

	/**
	 * 批量删除
		 * 
		 * 创建人：xiaojxiang 创建时间：2016-11-9
		 * 
		 * @param @param ids
		 * 返回类型：void
		 * @exception throws
	 */
	void delFollowList(List<String> ids);


	void delWatchChannel(String id);

}
