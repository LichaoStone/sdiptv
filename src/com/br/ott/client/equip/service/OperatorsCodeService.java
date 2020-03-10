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

import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.entity.Operators;
import com.br.ott.client.equip.entity.OperatorsCode;
import com.br.ott.client.equip.mapper.AreaMapper;
import com.br.ott.client.equip.mapper.OperatorsCodeMapper;
import com.br.ott.client.equip.mapper.OperatorsMapper;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.mapper.CityOperatorsMapper;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.ExcelUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.id.CreateIdentityId;

/* 
 *  
 *  文件名：OperatorsCodeService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-15 - 下午4:21:46
 */
@Service
public class OperatorsCodeService {
	private static final Logger logger = Logger
			.getLogger(OperatorsCodeService.class);

	@Autowired
	private OperatorsCodeMapper OperatorsCodeMapper;

	@Autowired
	private OperatorsMapper operatorsMapper;
	@Autowired
	private CityOperatorsMapper cityOperatorsMapper;

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private OperaLogService operaLogService;

	/**
	 * 增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:01
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	public void addOperatorsCode(OperatorsCode infraredCode) {
		OperatorsCodeMapper.addOperatorsCode(infraredCode);
	}

	/**
	 * 修改红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:14
	 * 
	 * @param infraredCode
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateOperatorsCode(OperatorsCode infraredCode) {
		OperatorsCodeMapper.updateOperatorsCode(infraredCode);
	}

	/**
	 * 修改红外码状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:27
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateOperatorsCodeStatus(String status, String id) {
		OperatorsCodeMapper.updateOperatorsCodeStatus(status, id);
	}

	/**
	 * 按分页查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:40
	 * 
	 * @param infraredCode
	 * @return 返回类型：List<InfraredCode>
	 * @exception throws
	 */
	public List<OperatorsCode> findOperatorsCodeByPage(
			OperatorsCode operatorsCode) {
		int totalResult=OperatorsCodeMapper.getCountOperatorsCode(operatorsCode);
		operatorsCode.setTotalResult(totalResult);
		return OperatorsCodeMapper.findOperatorsCodeByPage(operatorsCode);
	}

	/**
	 * 按ID查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:50:52
	 * 
	 * @param id
	 * @return 返回类型：InfraredCode
	 * @exception throws
	 */
	public OperatorsCode getOperatorsCodeById(String id) {
		return OperatorsCodeMapper.getOperatorsCodeById(id);
	}

	/**
	 * 按条件查询红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:04
	 * 
	 * @param infraredCode
	 * @return 返回类型：List<InfraredCode>
	 * @exception throws
	 */
	public List<OperatorsCode> findOperatorsCodeByCond(
			OperatorsCode infraredCode) {
		return OperatorsCodeMapper.findOperatorsCodeByCond(infraredCode);
	}

	public List<OperatorsCode> findOperatorsCodeByOPList(String opList,
			String keyList) {
		return OperatorsCodeMapper.findOperatorsCodeByOPList(opList, keyList);
	}

	/**
	 * 按ID删除红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:17
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void delOperatorsCode(String id) {
		OperatorsCodeMapper.delOperatorsCode(id);
	}

	public void delOperatorsCodeList(List<String> ids) {
		OperatorsCodeMapper.delOperatorsCodeList(ids);
	}

	/**
	 * 按集合方式增加红外码
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:51:28
	 * 
	 * @param list
	 *            返回类型：void
	 * @exception throws
	 */
	public void addOperatorsCodeList(List<OperatorsCode> list) {
		OperatorsCodeMapper.addOperatorsCodeList(list);
	}

	/**
	 * 校验运营商红外码是否存在
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:49:05
	 * 
	 * @param areaId
	 * @param operators
	 * @param modelName
	 * @param clientCode
	 * @return 返回类型：boolean
	 * @exception throws
	 */
	public boolean checkCode(String areaId, String operators, String type,
			String keyName) {
		OperatorsCode infraredCode = new OperatorsCode();
		infraredCode.setAreaId(areaId);
		infraredCode.setOperators(operators);
		infraredCode.setType(type);
		infraredCode.setKeyName(keyName);
		List<OperatorsCode> list = OperatorsCodeMapper
				.findOperatorsCodeByCond(infraredCode);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		list = null;
		return false;
	}

	/**
	 * 通过EXCEL增加运营商红外码
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:49:36
	 * 
	 * @param is
	 * @param request
	 * @return 返回类型：String
	 * @exception throws
	 */
	public String addInfraredCode(InputStream is, HttpServletRequest request) {
		List<OperatorsCode> list = readCodeExcel(is);
		if (CollectionUtils.isEmpty(list)) {
			return "文件内容不能为空";
		}
		int flag = 0;
		int have = 0;
		int success = 0;
		int except = 0;
		int noArea = 0;
		boolean flags = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHave = new StringBuffer();
		StringBuffer sbExcept = new StringBuffer();
		StringBuffer logContent = new StringBuffer();
		StringBuffer logArea = new StringBuffer();
		for (OperatorsCode node : list) {
			flag++;
			if (StringUtil.isEmpty(node.getAreaId())) {
				logger.error("数据不正确========" + flag + 1);
				noArea++;
				logArea.append(flag + 1 + ",");
				continue;
			}
			try {
				logContent.append("导入的运营商红外码数据如下：");
				boolean bool = checkCode(node.getAreaId(),
						node.getOperators(), node.getType(),
						node.getKeyName());
				if (!bool) {
					have++;
					sbHave.append(flag + ",");
				} else {
					OperatorsCodeMapper.addOperatorsCode(node);
					logContent.append(node.getAreaName() + "/"
							+ node.getOperators() + "/" + node.getKeyName()
							+ ",");
					success++;
					flags = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				except++;
				sbExcept.append(flag + ",");
				continue;
			}
		}

		if (flags) {
			// 写入系统操作日志
			operaLogService.addOperaLog("1", request, "批量导入的运营商红包码",
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
		if (noArea > 0) {
			sb.append("导入不存在地市" + noArea + "条,第" + logArea.toString()
					+ "行地市不存在");
		}
		return sb.toString();
	}

	private OperatorsCode addNode(int j, Row row, OperatorsCode node,
			String cell, Map<String, String> areaMap,
			Map<String, String> operMap, Map<String, String> typeMap, Map<String, String> cpMap) {
		switch (j) {
		case 0:
			break;
		case 1:
			String areaId = areaMap.get(cell);
			node.setAreaId(areaId);
			node.setAreaName(cell);
			break;
		case 2:
			String areaCell = "";
			String spName = "";
			String otherName="";
			if (j == 2) {
				areaCell = ExcelUtil.ConvertCellStr(row.getCell(1));
				spName = ExcelUtil.ConvertCellStr(row.getCell(9));
				otherName= ExcelUtil.ConvertCellStr(row.getCell(10));
			}
			String area=areaMap.get(areaCell);
			if (StringUtil.isEmpty(area)) {
				break;
			}
			String operators = operMap.get(areaCell + "-" + spName + "-" + cell);
			// 如果没有此运营商方案，则增加此运营商
			if (StringUtil.isEmpty(operators)) {
				Operators op = new Operators();
				op.setAreaId(area);
				op.setName(cell);
				op.setSpName(spName);
				op.setFullName(cell);
				op.setCode(CreateIdentityId.getInstance().createId() + "-"
						+ areaMap.get(areaCell));
				op.setStatus("1");
				op.setSequence(10);
				String cityOperators= cpMap.get(spName);
				if(StringUtil.isNotEmpty(cityOperators)){
					op.setCityOperators(cityOperators);
					op.setOtherName(spName);
				}else{
					op.setOtherName(otherName);
				}
				operatorsMapper.addOperators(op);
				operMap.put(areaCell + "-" + spName + "-" + cell, op.getId());
				node.setOperators(op.getId());
			} else {
				node.setOperators(operators);
			}
			node.setOperatorsName(cell);
			break;
		case 3:
			node.setType(typeMap.get(cell));
			break;
		case 4:
			node.setCodeFormat(cell);
			break;
		case 5:
			node.setClientCode(cell);
			break;
		case 6:
			node.setKeyName(cell);
			break;
		case 7:
			node.setKeyCode(cell);
			break;
		case 8:
			node.setAllCode(cell);
			break;
		case 9:
			// 运营商名称占位
			break;
		case 10:
			// 运营商别名占位
			break;
		default:
			logger.error("文件内容格式不正确");
			break;
		}
		return node;
	}

	/**
	 * 读取EXCEL中的运营商红外码
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:50:20
	 * 
	 * @param inp
	 * @return 返回类型：List<OperatorsCode>
	 * @exception throws
	 */
	private List<OperatorsCode> readCodeExcel(InputStream inp) {
		List<OperatorsCode> list = new ArrayList<OperatorsCode>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			OperatorsCode node = null;
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets
			Operators operators = new Operators();
			List<Operators> opers = operatorsMapper
					.findOperatorsByCond(operators);
			Map<String, String> operMap = new HashMap<String, String>();
			for (Operators b : opers) {
				operMap.put(
						b.getAreaName() + "-" + b.getSpName() + "-" + b.getName(),
						b.getId());
				if (StringUtil.isNotEmpty(b.getOtherName())) {
					String[] arr = b.getOtherName().trim().split(",");
					for (String name : arr) {
						operMap.put(b.getAreaName() + "-" + b.getSpName() + "-"
								+ name, b.getId());
					}
				}
			}
			Area area = new Area();
			area.setSecond("0");
			List<Area> areas = areaMapper.findAreaByCond(area);
			if (CollectionUtils.isEmpty(areas)) {
				return null;
			}
			Map<String, String> areaMap = new HashMap<String, String>();
			for (Area b : areas) {
				areaMap.put(b.getName(), b.getId());
			}

			List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
			if (CollectionUtils.isEmpty(dicts)) {
				return null;
			}
			Map<String, String> typeMap = new HashMap<String, String>();
			for (Dictionary b : dicts) {
				typeMap.put(b.getDicName(), b.getDicValue());
			}

			CityOperators cp = new CityOperators();
			cp.setStatus("1");
			List<CityOperators> cityOpers = cityOperatorsMapper
					.findCityOperatorsByCond(cp);
			Map<String, String> cpMap = new HashMap<String, String>();
			for (CityOperators b : cityOpers) {
				cpMap.put(b.getName(), b.getId());
			}
			// 从第二行开始读取数据
			boolean bool = false;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				node = new OperatorsCode();
				Row row = sheet.getRow(i); // 获取行(row)对象
				for (int j = 0; j < 11; j++) {
					// 转换接收的单元格
					cellStr = ExcelUtil.ConvertCellStr(row.getCell(j));
					if (j == 0 && StringUtil.isEmpty(cellStr)) {
						bool = true;
						logger.error("第" + i + "==========行,此列已无数据=========停止");
						break;
					}
					// 将单元格的数据添加至一个对象
					addNode(j, row, node, cellStr, areaMap, operMap, typeMap, cpMap);
				}
				// 将添加数据后的对象填充至list中
				if (bool == true) {
					logger.error("第" + i + "行无数据=========停止");
					break;
				}
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
