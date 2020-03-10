package com.br.ott.client.live.service;

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
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.entity.OperatorsChannel;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.CityOperatorsMapper;
import com.br.ott.client.live.mapper.OperatorsChannelMapper;
import com.br.ott.common.util.ExcelUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：OperatorsChannelService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-5-28 - 下午1:57:10
 */
@Service
public class OperatorsChannelService {
	private static final Logger logger = Logger
			.getLogger(OperatorsChannelService.class);

	@Autowired
	private OperatorsChannelMapper operatorsChannelMapper;
	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private CityOperatorsMapper cityOperatorsMapper;

	@Autowired
	private ChannelMapper channelMapper;

	/**
	 * 增加运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:42:27
	 * 
	 * @param operatorsChannel
	 *            返回类型：void
	 * @exception throws
	 */
	public void addOperatorsChannel(OperatorsChannel operatorsChannel) {
		operatorsChannelMapper.addOperatorsChannel(operatorsChannel);
	}

	public void updateOperatorsChannel(OperatorsChannel operatosChannel) {
		operatorsChannelMapper.updateOperatorsChannel(operatosChannel);
	}

	/**
	 * 修改运营商频道状态
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:42:51
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateOperatorsChannelStatus(String status, String id) {
		operatorsChannelMapper.updateOperatorsChannelStatus(status, id);
	}

	/**
	 * 按ID查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:05
	 * 
	 * @param id
	 * @return 返回类型：OperatorsChannel
	 * @exception throws
	 */
	public OperatorsChannel getOperatorsChannelById(String id) {
		return operatorsChannelMapper.getOperatorsChannelById(id);
	}

	/**
	 * 按分页查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:14
	 * 
	 * @param operatorsChannel
	 * @return 返回类型：List<OperatorsChannel>
	 * @exception throws
	 */
	public List<OperatorsChannel> findOperatorsChannelByPage(
			OperatorsChannel operatorsChannel) {
		int totalResult = operatorsChannelMapper
				.getCountOperatorsChannel(operatorsChannel);
		operatorsChannel.setTotalResult(totalResult);
		return operatorsChannelMapper
				.findOperatorsChannelByPage(operatorsChannel);
	}

	/**
	 * 按条件查询运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:39
	 * 
	 * @param operatorsChannel
	 * @return 返回类型：List<OperatorsChannel>
	 * @exception throws
	 */
	public List<OperatorsChannel> findOperatorsChannelByCond(
			OperatorsChannel operatorsChannel) {
		return operatorsChannelMapper
				.findOperatorsChannelByCond(operatorsChannel);
	}

	/**
	 * 按ID删除运营商频道
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午1:43:47
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void delOperatorsChannelById(String id) {
		operatorsChannelMapper.delOperatorsChannelById(id);
	}

	public void delOperatorsChannelList(List<String> ids) {
		operatorsChannelMapper.delOperatorsChannelList(ids);
	}

	/**
	 * 校验是否存在
	 * 
	 * 创建人：pananz 创建时间：2015-5-28 - 下午2:02:52
	 * 
	 * @param areaId
	 * @param operators
	 * @param channelId
	 * @return 返回类型：boolean
	 * @exception throws
	 */
	public boolean checkOperatorsChannel(String operators,
			String channelId, String localCid) {
		OperatorsChannel operatorsChannel = new OperatorsChannel();
		operatorsChannel.setOperators(operators);
		operatorsChannel.setChannelId(channelId);
		operatorsChannel.setPlayChannelId(localCid);
		List<OperatorsChannel> list = operatorsChannelMapper
				.findOperatorsChannelByCond(operatorsChannel);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
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
	public String addOperatorsChannels(InputStream is,
			HttpServletRequest request) {
		List<OperatorsChannel> list = readCodeExcel(is);
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
		for (OperatorsChannel node : list) {
			flag++;
			try {
				logContent.append("导入的运营商频道数据如下：");
				boolean bool = checkOperatorsChannel(node.getOperators(), node.getChannelId(),
						node.getPlayChannelId());
				if (!bool) {
					have++;
					sbHave.append(flag + 1 + ",");
				} else {
					operatorsChannelMapper.addOperatorsChannel(node);
					logContent.append(node.getAreaName() + "/"
							+ node.getOperatorsName() + "/"
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

		if (flags) {
			// 写入系统操作日志
			operaLogService.addOperaLog("1", request, "批量导入的运营商频道",
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

	private OperatorsChannel addNode(int j, OperatorsChannel node, String cell,
			Row row, Map<String, String> operMap, Map<String, String> channelMap) {
		switch (j) {
		case 0:
			String code = ExcelUtil.ConvertCellStr(row.getCell(1));
			String operators = operMap.get(cell + "-" + code);
			if (StringUtil.isNotEmpty(operators)) {
				node.setOperators(operators);
			} else {
				logger.error("================暂无此地区频道运营商" + cell);
				CityOperators op = new CityOperators();
				op.setName(cell);
				op.setFullName(cell);
				op.setCode(code);
				op.setStatus("1");
				cityOperatorsMapper.addCityOperators(op);
				operMap.put(cell + "-" + code, op.getId());
				node.setOperators(op.getId());
			}
			node.setOperatorsName(cell);
			break;
		case 1:
			break;
		case 2:
			String channelId = channelMap.get(cell);
			if (StringUtil.isEmpty(channelId)) {
				logger.error("================暂无此频道" + cell);
				Channel channel = new Channel();
				channel.setName(cell);
				channel.setStatus("0");
				channel.setRemark(ExcelUtil.ConvertCellStr(row.getCell(1))
						+ "---->" + ExcelUtil.ConvertCellStr(row.getCell(3))
						+ "--->" + ExcelUtil.ConvertCellStr(row.getCell(2)));
				channelMapper.addChannel(channel);
				node.setChannelId(channel.getId());
				channelMap.put(cell, channel.getId());
			} else {
				node.setChannelId(channelId);
			}
			node.setChannelName(cell);
			break;
		case 3:
			node.setPlayChannelId(cell);
			break;
		case 4:
			node.setIsLocal(cell);
			break;
		case 5:
			node.setSingleLiveServer(cell);
			break;
		case 6:
			node.setSingleLiveServer2(cell);
			break;
		case 7:
			node.setGroupLiveServer(cell);
			break;
		case 8:
			node.setGroupLiveServer2(cell);
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
	private List<OperatorsChannel> readCodeExcel(InputStream inp) {
		List<OperatorsChannel> list = new ArrayList<OperatorsChannel>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			OperatorsChannel node = null;
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			List<CityOperators> opers = OperatorsCache.opList;
			Map<String, String> operMap = new HashMap<String, String>();
			for (CityOperators b : opers) {
				operMap.put(b.getName() + "-" + b.getCode(), b.getId());
			}
			opers = null;

			Channel c = new Channel();
			c.setOrderColumn("id");
			List<Channel> channels = channelMapper.findChannelByCond(c);
			Map<String, String> channelMap = new HashMap<String, String>();
			for (Channel b : channels) {
				channelMap.put(b.getName(), b.getId());
				if (StringUtil.isNotEmpty(b.getOtherName())) {
					String[] arr = b.getOtherName().trim().split(",");
					for (String name : arr) {
						channelMap.put(name, b.getId());
					}
				}
			}
			channels = null;

			boolean bool = false;
			int maxRow = sheet.getLastRowNum();
			// 从第二行开始读取数据
			for (int i = 1; i <= maxRow; i++) {
				node = new OperatorsChannel();
				Row row = sheet.getRow(i); // 获取行(row)对象
				for (int j = 0; j < 9; j++) {
					// 转换接收的单元格
					cellStr = ExcelUtil.ConvertCellStr(row.getCell(j));
					if (j == 0 && StringUtil.isEmpty(cellStr)) {
						bool = true;
						logger.error("第" + i + "==========行,此列已无数据=========停止");
						break;
					}
					addNode(j, node, cellStr, row, operMap, channelMap);
				}
				if (bool == true) {
					logger.error("第" + i + "行无数据=========停止");
					break;
				}
				// 将添加数据后的对象填充至list中
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
}
