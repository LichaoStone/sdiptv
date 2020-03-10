package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.UserPrograms;

/* 
 *  
 *  文件名：UserProgramsMapper.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:27:37
*/
public interface UserProgramsMapper {

	void addUserPrograms(UserPrograms userPrograms);

	void updateType(@Param("uid")
	String uid, @Param("programsId")
	String programsId, @Param("type")
	String type);

	UserPrograms getUserProgramsByUidAndPid(@Param("uid")
	String uid, @Param("programsId")
	String programsId);

	/**
	 * 按条件查询
	 * 创建人：pananz
	 * 创建时间：2014-6-30 - 下午10:33:51
	 * @param up
	 * @return
	 * 返回类型：List<UserPrograms>
	 * @exception throws
	 */
	List<UserPrograms> findUserProgramsByCond(UserPrograms up);

	/**
	 * 分页查询
	 * 创建人：pananz
	 * 创建时间：2014-6-30 - 下午10:34:16
	 * @param up
	 * @return
	 * 返回类型：List<UserPrograms>
	 * @exception throws
	 */
	List<UserPrograms> findUserProgramsByPage(UserPrograms up);
	int getCountUserPrograms(UserPrograms up);
	
}
