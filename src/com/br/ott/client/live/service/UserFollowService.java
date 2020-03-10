package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.UserFollow;
import com.br.ott.client.live.mapper.UserFollowMapper;

/* 
 *  
 *  文件名：UserFollowService.java
 *  创建人：pananz
 *  创建时间：2014-6-30 - 下午10:39:37
*/
@Service
public class UserFollowService {

	@Autowired
	private UserFollowMapper userFollowMapper;

	public void addUserFollow(UserFollow userFollow) {
		userFollowMapper.addUserFollow(userFollow);
	}

	public List<UserFollow> findUserFollowByCond(UserFollow uf) {
		return userFollowMapper.findUserFollowByCond(uf);
	}

	public List<UserFollow> findUserFollowByPage(UserFollow uf) {
		int totalResult=userFollowMapper.getCountUserFollow(uf);
		uf.setTotalResult(totalResult);
		return userFollowMapper.findUserFollowByPage(uf);
	}

	public void delFollowList(List<String> ids) {
		userFollowMapper.delFollowList(ids);
	}

	public void delWatchChannel(String id) {
		userFollowMapper.delWatchChannel(id);
	}
}
