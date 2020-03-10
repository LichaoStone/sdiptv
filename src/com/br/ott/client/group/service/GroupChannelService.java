package com.br.ott.client.group.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.common.OperatorsCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.group.entity.GroupChannel;
import com.br.ott.client.group.mapper.GroupChannelMapper;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;
import com.br.ott.common.util.ExcelUtil;
import com.br.ott.common.util.StringUtil;

@Service
public class GroupChannelService {
	private static final Logger logger = Logger
			.getLogger(GroupChannelService.class);
	@Autowired
	private GroupChannelMapper mGroupChannelMapper;

	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;

	/**
	 * 分页查询
	 */
	public List<GroupChannel> findGroupChannelByPage(GroupChannel mGroupChannel) {
		mGroupChannel.setTotalResult(mGroupChannelMapper
				.getCountGroupChannel(mGroupChannel));
		return mGroupChannelMapper.findGroupChannelByPage(mGroupChannel);
	}

	/**
	 * 根据ID查询
	 */
	public GroupChannel findGroupChannelById(String id) {
		return mGroupChannelMapper.findGroupChannelById(id);
	}

	/**
	 * 新增
	 */
	public void insertGroupChannel(GroupChannel mGroupChannel) {
		mGroupChannelMapper.insertGroupChannel(mGroupChannel);
	}

	/**
	 * 修改
	 */
	public void updateGroupChannel(GroupChannel mGroupChannel) {
		mGroupChannelMapper.updateGroupChannel(mGroupChannel);
	}

	/**
	 * 查询全部
	 */
	public List<GroupChannel> findGroupChannelByCond(GroupChannel mGroupChannel) {
		return mGroupChannelMapper.findGroupChannelByCond(mGroupChannel);
	}

	/**
	 * 根据多个ID删除数据
	 */
	public void deleteGroupChannelByList(List<String> ids) {
		mGroupChannelMapper.deleteGroupChannelByList(ids);
	}

	/**
	 * 根据ID删除
	 */
	public void deleteGroupChannelById(String id) {
		mGroupChannelMapper.deleteGroupChannelById(id);
	}

	public void updateChannelStatus(String status, String id) {
		mGroupChannelMapper.updateChannelStatus(status, id);
	}

	public boolean checkGroupChannel(String operators, String groupCode,
			String localCid) {
		GroupChannel gc = new GroupChannel();
		gc.setOperators(operators);
		gc.setGroupCode(groupCode);
		gc.setLocalCid(localCid);
		List<GroupChannel> list = findGroupChannelByCond(gc);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		list = null;
		return false;
	}

	/**
	 * 通过 EXCEL导入运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午3:14:36
	 * 
	 * @param is
	 * @param request
	 * @return 返回类型：String
	 * @exception throws
	 */
	public String addGroupChannels(InputStream is, HttpServletRequest request) {
		List<GroupChannel> list = readGroupChannelExcel(is);
		if (CollectionUtils.isEmpty(list)) {
			return "文件内容不能为空";
		}
		int flag = 0;
		int have = 0;
		int success = 0;
		int except = 0;
		boolean flags = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHave = new StringBuffer();
		StringBuffer sbExcept = new StringBuffer();
		StringBuffer logContent = new StringBuffer();
		logContent.append("导入的用户组运营商频道数据如下：");
		for (GroupChannel node : list) {
			flag++;
			try {
				boolean bool = checkGroupChannel(node.getOperators(),
						node.getGroupCode(), node.getLocalCid());
				if (!bool) {
					have++;
					sbHave.append(flag + 1 + ",");
				} else {
					mGroupChannelMapper.insertGroupChannel(node);
					logContent.append(node.getOperatorsName() + "/"
							+ node.getChannelName() + ",");
					success++;
					flags = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				except++;
				sbExcept.append(flag + 1 + ",");
				continue;
			}
		}
		list = null;
		if (flags) {
			// 写入系统操作日志
			operaLogService.addOperaLog("1", request, "批量导入的用户组运营商频道",
					logContent.toString());
		}
		sb.append("共" + flag + "条数据,");
		if (success > 0) {
			sb.append("成功导入" + success + "条数据,");
		}
		if (have > 0) {
			sb.append("重复导入失败" + have + "条,第" + sbHave.toString() + "行数据已存在");
		}
		if (except > 0) {
			sb.append("导入失败" + except + "条,第" + sbExcept.toString() + "行数据异常");
		}
		return sb.toString();
	}

	private GroupChannel addNode(int j, GroupChannel node, String cell,
			Row row, Map<String, String> channelMap) {
		switch (j) {
		case 0:
			node.setGroupCode(cell);
			break;
		case 1:
			String channelId = channelMap.get(cell);
			if (StringUtil.isEmpty(channelId)) {
				logger.error("================暂无此频道" + cell);
			} else {
				node.setOchannelId(channelId);
			}
			node.setChannelName(cell);
			break;
		case 2:
			node.setLocalCid(cell);
			break;
		case 3:
			node.setSequence(cell);
			break;
		default:
			break;
		}
		return node;
	}

	/**
	 * 读取EXCEL中的运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:50:20
	 * 
	 * @param inp
	 * @return 返回类型：List<InfraredCode2>
	 * @exception throws
	 */
	private List<GroupChannel> readGroupChannelExcel(InputStream inp) {
		List<GroupChannel> list = new ArrayList<GroupChannel>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			GroupChannel node = null;
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			List<CityOperators> opers = OperatorsCache.findOperators2();
			Map<String, String> operMap = new HashMap<String, String>();
			for (CityOperators b : opers) {
				operMap.put(b.getName(), b.getCode());
			}
			opers = null;
			String opName = sheet.getSheetName();
			String operatorsCode = operMap.get(opName);
			String operators = OperatorsCache
					.getOperatorsByCode(operatorsCode);

			OperatorsChannel oc = new OperatorsChannel();
			oc.setStatus("1");
			oc.setOperators(operators);
			List<OperatorsChannel> channels = operatorsChannelMapper
					.findOperatorsChannelByCond(oc);
			Map<String, String> channelMap = new HashMap<String, String>();
			for (OperatorsChannel b : channels) {
				channelMap.put(b.getChannelName(), b.getId());
			}
			channels = null;
			boolean bool = false;
			int maxRow = sheet.getLastRowNum();
			// 从第二行开始读取数据
			for (int i = 1; i <= maxRow; i++) {
				node = new GroupChannel();
				Row row = sheet.getRow(i); // 获取行(row)对象
				for (int j = 0; j < 9; j++) {
					// 转换接收的单元格
					cellStr = ExcelUtil.ConvertCellStr(row.getCell(j));
					if (j == 0 && StringUtil.isEmpty(cellStr)) {
						bool = true;
						logger.error("第" + i + "==========行,此列已无数据=========停止");
						break;
					}
					addNode(j, node, cellStr, row, channelMap);
				}
				if (bool == true) {
					logger.error("第" + i + "行无数据=========停止");
					break;
				}
				// 将添加数据后的对象填充至list中
				node.setOperators(operatorsCode);
				node.setStatus("1");
				list.add(node);
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public void updateSequence(GroupChannel groupChannel) {
		mGroupChannelMapper.updateSequence(groupChannel);
	}
}