package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.UserPrograms;
import com.br.ott.client.live.mapper.UserProgramsMapper;

/* 
 *  
 *  文件名：UserProgramsService.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:42:10
*/
@Service
public class UserProgramsService {

	@Autowired
	private UserProgramsMapper userProgramsMapper;

	public void addUserPrograms(UserPrograms userPrograms) {
		userProgramsMapper.addUserPrograms(userPrograms);
	}

	public void updateType(String uid, String programsId, String type) {
		userProgramsMapper.updateType(uid, programsId, type);
	}

	public UserPrograms getUserProgramsByUidAndPid(String uid, String programsId) {
		return userProgramsMapper.getUserProgramsByUidAndPid(uid, programsId);
	}

	/**
	 * 按条件查询
	 * 创建人：pananz
	 * 创建时间：2014-6-30 - 下午10:33:51
	 * @param up
	 * @return
	 * 返回类型：List<UserPrograms>
	 * @exception throws
	 */
	public List<UserPrograms> findUserProgramsByCond(UserPrograms up) {
		return userProgramsMapper.findUserProgramsByCond(up);
	}

	/**
	 * 分页查询
	 * 创建人：pananz
	 * 创建时间：2014-6-30 - 下午10:34:16
	 * @param up
	 * @return
	 * 返回类型：List<UserPrograms>
	 * @exception throws
	 */
	public List<UserPrograms> findUserProgramsByPage(UserPrograms up) {
		int totalResult=userProgramsMapper.getCountUserPrograms(up);
		up.setTotalResult(totalResult);
		return userProgramsMapper.findUserProgramsByPage(up);
	}
}
