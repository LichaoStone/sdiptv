package com.br.ott.client.live.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.Global;
import com.br.ott.client.SessionUtil;
import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.quartz.CCTVQuartz;
import com.br.ott.client.common.quartz.ProgramsUtils;
import com.br.ott.client.common.quartz.TvSouQuartz;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.ChannelMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：ProgramsService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方网络科技有限公司
 *  创建人：Administrator
 *  创建时间：2014-5-16 - 下午5:49:31
 */
@Service
public class ProgramsService {
	private static final Logger logger = Logger
			.getLogger(ProgramsService.class);

	@Autowired
	private ProgramsMapper programsMapper;
	@Autowired
	private OperaLogService operaLogService;
	@Autowired
	private ChannelRecoverService channelRecoverService;
	@Autowired
	private ChannelMapper channelMapper;

	public void addPrograms(Programs programs) {
		programsMapper.addPrograms(programs);
	}

	public List<Programs> findProgramsByPage(Programs programs) {
		int totalResult = programsMapper.getCountPrograms(programs);
		programs.setTotalResult(totalResult);
		return programsMapper.findProgramsByPage(programs);
	}

	public List<Programs> findProgramsByCond(Programs programs) {
		return programsMapper.findProgramsByCond(programs);
	}

	public Programs getProgramsById(String id) {
		return programsMapper.getProgramsById(id);
	}

	public void addProgramsByList(List<Programs> list) {
		programsMapper.addProgramsByList(list);
	}

	public void updatePrograms(Programs programs) {
		programsMapper.updatePrograms(programs);
	}

	public void updateProgramSource(Programs programs) {
		programsMapper.updateProgramSource(programs);
	}

	public Programs getProgramsByCidAndName(String cid, String name,
			String playTime) {
		return programsMapper.getProgramsByCidAndName(cid, name, playTime);
	}

	public void delProgramsList(List<String> ids) {
		programsMapper.delProgramsList(ids);
	}

	public Programs checkLiveProgram(String name) {
		Programs program = programsMapper.getLiveProgramByName(name, DateUtil
				.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd HH:mm"));
		return program;
	}


	/**
	 * 抓取节目单
	 * 
	 * 创建人：pananz 创建时间：2015-5-11 - 下午4:57:40
	 * 
	 * @param type
	 * @param start
	 * @param count
	 * @param createFile
	 *            返回类型：void
	 * @exception throws
	 */
	public void updatePrograms(String type, Date start, String total,
			String createFile) {
		Channel c = new Channel();
		c.setSecondNode("0");
		c.setOrderColumn("id");
		c.setStatus("1");
		List<Channel> cs = channelMapper.findChannelByCond(c);
		int sum = StringUtil.isEmpty(total) ? 1 : Integer.parseInt(total);
		Map<String, Dictionary> tsMap = DictCache
				.getDictValueList(DicType.TSJM);
		for (int i = 0; i <= sum; i++) {
			Date date = DateUtil.addDay(start, i);
			List<Programs> pList2 = new ArrayList<Programs>();
			for (Channel channel : cs) {
				List<Programs> list = null;
				try {
					if ("tvsou".equals(type)) {
						List<Programs> programs = TvSouQuartz.getProgramsByTS(
								channel, date, 1, tsMap);
						list = TvSouQuartz.setTime(programs, channel, tsMap);
					} else if ("cctv".equals(type)) {
						List<Programs> programs = CCTVQuartz.getProgramsByYS(
								channel, date, 1, tsMap);
						list = CCTVQuartz.setTime(programs, channel, tsMap);
					}
					if (CollectionUtils.isNotEmpty(list)) {
						programsMapper.delProgramsByCidAndDate(channel.getId(),
								DateUtil.toString(date, "yyyy-MM-dd"));
						addProgramsByList(list);
						pList2.addAll(list);
					}
					list = null;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("频道：" + channel.getName() + "节目单出错");
					continue;
				}
			}
			if ("1".equals(createFile)) {
				if (CollectionUtils.isNotEmpty(pList2)) {
					try {
						ProgramsUtils.createDataFile(type + "_data", date,
								pList2, Global.PROGRAM_UPLOAD_SOURCE_URL);
						pList2 = null;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("写：" + DateUtil.toString(date) + "节目单出错");
						continue;
					}
				}
			}
		}
	}

	public void changePrograms(String status, String id) {
		programsMapper.updatePorgramsStatus(status, id);
		// 异常节目单加入缓存中
		channelRecoverService.reloadAbnormalInfo();
	}

	public String addProgramsList(InputStream is, String playDate,
			HttpServletRequest request) {
		List<String> channelList = new ArrayList<String>();
		List<Programs> list = new ArrayList<Programs>();
		try {
			list = readProgramsExcel(is, playDate, channelList);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("读取节目单异常================");
		}
		if (CollectionUtils.isEmpty(list)) {
			return "文件内容不能为空";
		}
		int flag = 0;
		int success = 0;
		int except = 0;
		int timeOut = 0;
		boolean flags = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbExcept = new StringBuffer();
		StringBuffer logContent = new StringBuffer();
		logContent.append("导入的节目单数据如下：");
		List<Programs> pList = new ArrayList<Programs>();

		String operator = SessionUtil.getLoginName();
		for (Programs node : list) {
			flag++;
			try {
				if (StringUtil.isEmpty(node.getTimeLongth())) {
					continue;
				}
				String basicName = StringUtil.filterBasicWord(node.getName());
				node.setBasicName(basicName);
				node.setOperator(operator);
				pList.add(node);
				logContent.append(node.getName() + "/" + node.getChannelName()
						+ ",");
				success++;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("加入节目单集合错误======" + e.getMessage());
				except++;
				sbExcept.append(flag + 1 + ",");
				continue;
			}
		}
		list = null;
		if (CollectionUtils.isNotEmpty(pList)) {
			logger.debug("删除" + playDate + "的节目单");
			try {
				delPrograms(playDate, channelList);
				addPrograms(pList);
				flags = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导入======" + e.getMessage());
				sb.append("导入失败");
				return sb.toString();
			}
		}
		if (flags) {
			// 写入系统操作日志
			operaLogService.addOperaLog("1", request, "批量导入的节目单",
					logContent.toString());
		}

		sb.append("共" + flag + "条数据,");
		if (success > 0) {
			sb.append("成功导入" + success + "条数据,");
		}
		if (timeOut > 0) {
			sb.append("节目超过当前时间：" + timeOut + "条数据,");
		}
		if (except > 0) {
			sb.append("导入失败" + except + "条,第" + sbExcept.toString() + "行数据异常");
		}
		logger.debug("节目单导入返回信息:" + sb.toString());
		return sb.toString();
	}

	private void delPrograms(String playTime, List<String> channelList) {
		// 删除指定天历史数据,并大于当前时间的数据
		for (String cid : channelList) {
			programsMapper.delProgramsByCidAndDate(cid, playTime);
		}
		channelList = null;
	}

	private void addPrograms(List<Programs> list) {
		int count = 0;
		List<Programs> tempList = new ArrayList<Programs>();
		for (Programs p : list) {
			count++;
			tempList.add(p);
			if (count % 5000 == 0) {
				addProgramsByList(tempList);
				tempList.clear();
			}
		}
		if (CollectionUtils.isNotEmpty(tempList)) {
			addProgramsByList(tempList);
		}
		tempList = null;
		list = null;
	}

	private List<Programs> readProgramsExcel(InputStream is, String playDate,
			List<String> channelList) {
		List<Programs> list = new ArrayList<Programs>();
		try {
			Workbook wb = WorkbookFactory.create(is);
			Programs node = null;
			List<Channel> channels = ChannelCache.cList;
			Map<String, String> channelMap = new HashMap<String, String>();
			for (Channel b : channels) {
				channelMap.put(b.getName().trim(), b.getId());
				if (StringUtil.isNotEmpty(b.getOtherName())) {
					String[] arr = b.getOtherName().trim().split(",");
					for (String name : arr) {
						channelMap.put(name.trim(), b.getId());
					}
				}
			}
			channels = null;
			int index = wb.getNumberOfSheets();
			logger.debug("all channel is: " + index);
			for (int n = 0; n < index; n++) {
				Sheet sheet = wb.getSheetAt(n);// 取得第一个sheets
				String sheetName = sheet.getSheetName();
				logger.debug(n + "===========" + sheetName);
				String channelId = channelMap.get(sheetName);
				if (StringUtil.isEmpty(channelId)) {
					logger.debug("无此频道: " + sheetName);
					continue;
				}

				boolean bool = false;
				boolean flag = false;
				int maxRow = sheet.getLastRowNum();
				// 从第二行开始读取数据
				logger.debug("maxRow: " + maxRow);
				for (int i = 1; i <= maxRow; i++) {
					Row row = sheet.getRow(i); // 获取行(row)对象
					if (row == null) {
						logger.debug("row is null======" + i);
						break;
					}
					node = new Programs();
					node.setChannelId(channelId);
					node.setChannelName(sheetName);
					for (int j = 0; j < 3; j++) {
						// 转换接收的单元格
						String cellStr = ConvertCellStr(row.getCell(j));
						if (j == 0 && StringUtil.isEmpty(cellStr)) {
							bool = true;
							logger.debug(sheetName + ",第" + i
									+ "==========行的，第一列已无数据=========停止");
							break;
						}
						addNode(j, node, cellStr, playDate);
					}
					if (bool == true) {
						logger.debug(sheetName + "第" + i + "行无数据=========停止");
						if (i == 1) {
							flag = true;
						}
						break;
					}
					// 将添加数据后的对象填充至list中
					list.add(node);
					node = null;
				}
				if (flag == false) {
					channelList.add(channelId);
				}
			}
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
			logger.error("读取节目单文件异常");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private String ConvertCellStr(Cell cell) {
		if (null == cell) {
			return "";
		}
		String cellStr = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString().trim();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 先看是否是日期格式
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				cellStr = com.br.ott.common.util.DateUtil.toString(
						cell.getDateCellValue(), "HH:mm");
			} else {
				// 读取数字
				double number = cell.getNumericCellValue();
				int num = (int) cell.getNumericCellValue();
				if (number == num) {
					cellStr = String.valueOf(num);
				} else {
					cellStr = String.valueOf(number);
				}
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			try {
				double num = cell.getNumericCellValue();
				cellStr = String.valueOf((int) num);
			} catch (IllegalStateException e) {
				cellStr = String.valueOf(cell.getStringCellValue());
			}
			break;
		}
		return cellStr;
	}

	private void addNode(int j, Programs node, String cell, String playDate) {
		switch (j) {
		case 0:
			node.setPlayTime(playDate + " " + cell);
			break;
		case 1:
			node.setName(cell);
			break;
		case 2:
			node.setTimeLongth(cell);
			break;
		default:
			break;
		}
	}
}
