package com.br.ott.client.equip.service;

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

import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.mapper.AreaMapper;
import com.br.ott.common.util.ExcelUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：AreaService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午3:43:49
 */
@Service
public class AreaService {
	private static final Logger logger = Logger.getLogger(AreaService.class);
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private OperaLogService operaLogService;

	/**
	 * 增加区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:10
	 * 
	 * @param area
	 *            返回类型：void
	 * @exception throws
	 */
	public void addArea(Area area) {
		areaMapper.addArea(area);
	}

	/**
	 * 修改区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:18
	 * 
	 * @param area
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateArea(Area area) {
		areaMapper.updateArea(area);
	}

	/**
	 * 修改区域状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:24:25
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateAreaStatus(String status, String id) {
		areaMapper.updateAreaStatus(status, id);
	}

	/**
	 * 按ID查询区域
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:25:07
	 * 
	 * @param id
	 * @return 返回类型：Area
	 * @exception throws
	 */
	public Area getAreaById(String id) {
		return areaMapper.getAreaById(id);
	}

	/**
	 * 按分页查询区域信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:26:32
	 * 
	 * @param area
	 * @return 返回类型：List<Area>
	 * @exception throws
	 */
	public List<Area> findAreaByPage(Area area) {
		int totalResult=areaMapper.getCountArea(area);
		area.setTotalResult(totalResult);
		return areaMapper.findAreaByPage(area);
	}

	/**
	 * 按条件查询区域信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午3:26:43
	 * 
	 * @param area
	 * @return 返回类型：List<Area>
	 * @exception throws
	 */
	public List<Area> findAreaByCond(Area area) {
		return areaMapper.findAreaByCond(area);
	}

	/**
	 * 通过区域查询省会城市
	 * 
	 * 创建人：pananz 创建时间：2015-5-26 - 下午5:15:28
	 * 
	 * @param name
	 * @return 返回类型：Area
	 * @exception throws
	 */
	public Area getAreaCapitalByName(String name) {
		return areaMapper.getAreaCapitalByName(name);
	}

	public boolean checkAreaByName(String name) {
		Area area = new Area();
		area.setName(name);
		List<Area> list = findAreaByCond(area);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		return false;
	}

	public String addAreaByExcel(InputStream is, HttpServletRequest request) {
		List<Area> list = readAreaExcel(is);
		if (CollectionUtils.isEmpty(list)) {
			return "文件内容不能为空";
		}
		int flag = 1;
		int have = 0;
		int success = 0;
		int except = 0;
		boolean flags = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHave = new StringBuffer();
		StringBuffer sbExcept = new StringBuffer();
		StringBuffer logContent = new StringBuffer();
		for (Area node : list) {
			if (StringUtil.isEmpty(node.getName())) {
				logger.error("数据不正确========");
				break;
			}
			flag++;
			try {
				logContent.append("导入的区域数据如下：");
				boolean bool = checkAreaByName(node.getName());
				if (!bool) {
					have++;
					sbHave.append(flag + ",");
				} else {
					if (!"0".equals(node.getParentId())) {
						areaMapper.addArea(node);
					}
					logContent.append(node.getName() + "/"
							+ node.getParentName() + "/" + ",");
					success++;
					flags = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				except++;
				sbExcept.append(flag + ",");
			}
		}

		if (flags) {
			// 写入系统操作日志
			operaLogService.addOperaLog("1", request, "批量导入的区域",
					logContent.toString());
		}

		sb.append("共" + flag + "条数据,");
		if (success > 0) {
			sb.append("成功导入" + success + "条数据,");
		}
		if (have > 0) {
			sb.append("重复导入失败" + have + "条,第" + sbHave.toString() + "条数据已存在");
		}
		if (except > 0) {
			sb.append("导入失败" + except + "条,第" + sbExcept.toString() + "条数据异常");
		}
		return sb.toString();
	}

	private Area addNode(int j, Area node, String cell, String areaCell,
			Map<String, String> areaMap) {
		switch (j) {
		case 0:
			node.setName(cell);
			break;
		case 1:
			String areaId = areaMap.get(cell);
			if (StringUtil.isEmpty(areaId)) {
				Area area = new Area();
				area.setName(areaCell);
				area.setParentId("0");
				area.setIsCapital("0");
				areaMapper.addArea(area);
				areaMap.put(areaCell, area.getId());
				node.setParentId("0");
			} else {
				node.setParentId(areaId);
				node.setParentName(cell);
			}
			break;
		case 2:
			node.setIsCapital(cell);
			break;
		default:
			logger.error("文件内容格式不正确");
			break;
		}
		return node;
	}

	/**
	 * 读取EXCEL中Area
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:50:20
	 * 
	 * @param inp
	 * @return 返回类型：List<InfraredCode2>
	 * @exception throws
	 */
	private List<Area> readAreaExcel(InputStream inp) {
		List<Area> list = new ArrayList<Area>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			Area node = null;
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets
			Area area = new Area();
			area.setStatus("1");
			area.setParentId("0");
			List<Area> areas = areaMapper.findAreaByCond(area);
			Map<String, String> areaMap = new HashMap<String, String>();
			for (Area b : areas) {
				areaMap.put(b.getName(), b.getId());
			}
			boolean bool = false;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				node = new Area();
				Row row = sheet.getRow(i); // 获取行(row)对象
				if (row == null) {// row为空的话,不处理
					continue;
				}
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// 转换接收的单元格
					cellStr = ExcelUtil.ConvertCellStr(row.getCell(j), cellStr);
					if (j == 0 && StringUtil.isEmpty(cellStr)) {
						bool = true;
						logger.error("第" + i + "==========行,此列已无数据=========停止");
						break;
					}
					// 将单元格的数据添加至一个对象
					String areaCell = "";
					if (j == 1) {
						areaCell = ExcelUtil.ConvertCellStr(row.getCell(j - 1),
								cellStr);
					}
					addNode(j, node, cellStr, areaCell, areaMap);
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
