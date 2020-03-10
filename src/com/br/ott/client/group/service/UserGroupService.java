package com.br.ott.client.group.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.common.GroupCache;
import com.br.ott.client.group.entity.GroupChannel;
import com.br.ott.client.group.entity.UserGroup;
import com.br.ott.client.group.mapper.GroupChannelMapper;
import com.br.ott.client.group.mapper.UserGroupMapper;
import com.br.ott.common.util.SSOUtil;
import com.br.ott.common.util.StringUtil;

@Service
public class UserGroupService {
	@Autowired
	private UserGroupMapper mUserGroupMapper;

	@Autowired
	private GroupChannelMapper groupChannelMapper;

	/**
	 * 分页查询
	 */
	public List<UserGroup> findUserGroupByPage(UserGroup mUserGroup) {
		mUserGroup.setTotalResult(mUserGroupMapper
				.getCountUserGroup(mUserGroup));
		return mUserGroupMapper.findUserGroupByPage(mUserGroup);
	}

	/**
	 * 根据ID查询
	 */
	public UserGroup findUserGroupById(String id) {
		return mUserGroupMapper.findUserGroupById(id);
	}

	/**
	 * 新增
	 */
	public void insertUserGroup(UserGroup mUserGroup, String groupChannels) {
		mUserGroupMapper.insertUserGroup(mUserGroup);
		sysToLauncher(mUserGroup);
		sysTo3A(mUserGroup);
		reloadUserGroup();

		if (StringUtil.isNotEmpty(groupChannels)) {
			String[] gcArray = groupChannels.split(",");
			for (String str : gcArray) {
				String[] arr = str.split("-_-");
				// CCTV1-_-1-_-12 频道名称，频道号，运营商频道ID
				GroupChannel gc = new GroupChannel();
				gc.setOperators(mUserGroup.getOperators());
				gc.setGroupCode(mUserGroup.getGroupCode());
				gc.setChannelName(arr[0]);
				gc.setLocalCid(arr[1]);
				gc.setOchannelId(arr[2]);
				gc.setSequence(arr[1]);
				gc.setStatus("1");
				groupChannelMapper.insertGroupChannel(gc);
			}
		}
	}

	/**
	 * 同步用组信息到3A服务器
	 * 
	 * 创建人：pananz 创建时间：2016-12-21
	 * 
	 * @param @param mUserGroup 返回类型：void
	 * @exception throws
	 */
	public String sysTo3A(UserGroup mUserGroup) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("groupName", mUserGroup.getGroupName());
		map.put("groupCode", mUserGroup.getGroupCode());
		map.put("operators", mUserGroup.getOperators());
		map.put("status", mUserGroup.getStatus());
		String result = SSOUtil.postRequestByUTF8(Global.SYNCGROUP_BOSS,
				JSONObject.fromObject(map).toString());
		if ("成功".equals(result)) {
			mUserGroupMapper.updateUserGroupStatus2("1", mUserGroup.getId());
		} else {
			mUserGroupMapper.updateUserGroupStatus2("0", mUserGroup.getId());
		}
		return result;
	}

	/**
	 * 同步用组信息到Launcher服务器
	 * 
	 * 创建人：pananz 创建时间：2016-12-21
	 * 
	 * @param @param mUserGroup 返回类型：void
	 * @exception throws
	 */
	public String sysToLauncher(UserGroup mUserGroup) {
		if ("hngd.yd".equals(mUserGroup.getOperators())) {// 移动暂时无launcher平台
			return "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("groupName", mUserGroup.getGroupName());
		map.put("groupCode", mUserGroup.getGroupCode());
		map.put("operators", mUserGroup.getOperators());
		map.put("status", mUserGroup.getStatus());
		String result = SSOUtil.postRequestByUTF8(Global.SYNCGROUP_LAUNCHER,
				JSONObject.fromObject(map).toString());
		if ("成功".equals(result)) {
			mUserGroupMapper.updateUserGroupStatus3("1", mUserGroup.getId());
		} else {
			mUserGroupMapper.updateUserGroupStatus3("0", mUserGroup.getId());
		}
		return result;
	}

	public void reloadUserGroup() {
		UserGroup ug = new UserGroup();
		ug.setStatus("1");
		List<UserGroup> ugList = findUserGroupByCond(ug);
		GroupCache.reload(ugList);
	}

	/**
	 * 修改
	 */
	public void updateUserGroup(UserGroup mUserGroup) {
		mUserGroupMapper.updateUserGroup(mUserGroup);
		sysToLauncher(mUserGroup);
		sysTo3A(mUserGroup);
		reloadUserGroup();
	}

	/**
	 * 查询全部
	 */
	public List<UserGroup> findUserGroupByCond(UserGroup mUserGroup) {
		return mUserGroupMapper.findUserGroupByCond(mUserGroup);
	}

	/**
	 * 根据多个ID删除数据
	 */
	public void deleteUserGroupByList(List<String> ids) {
		mUserGroupMapper.deleteUserGroupByList(ids);
		reloadUserGroup();
	}

	/**
	 * 根据ID删除
	 */
	public void deleteUserGroupById(String id) {
		mUserGroupMapper.deleteUserGroupById(id);
		reloadUserGroup();
	}

	/**
	 * 更新状态
	 * 
	 * 创建人：pananz 创建时间：2016-10-12
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	public void updateUserGroupStatus(String status, String id) {
		mUserGroupMapper.updateUserGroupStatus(status, id);
		reloadUserGroup();
	}
}