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
import com.br.ott.client.equip.entity.Brand;
import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.entity.InfraredCode;
import com.br.ott.client.equip.mapper.BrandMapper;
import com.br.ott.client.equip.mapper.HardModelMapper;
import com.br.ott.client.equip.mapper.InfraredCodeMapper;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.ExcelUtil;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：InfraredCodeService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:52:40
 */
@Service
public class InfraredCodeService {
	private static final Logger logger = Logger
			.getLogger(InfraredCodeService.class);
	@Autowired
	private InfraredCodeMapper infraredCodeMapper;

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private HardModelMapper hardModelMapper;

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
	public void addInfraredCode(InfraredCode infraredCode) {
		infraredCodeMapper.addInfraredCode(infraredCode);
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
	public void updateInfraredCode(InfraredCode infraredCode) {
		infraredCodeMapper.updateInfraredCode(infraredCode);
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
	public void updateInfraredCodeStatus(String status, String id) {
		infraredCodeMapper.updateInfraredCodeStatus(status, id);
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
	public List<InfraredCode> findInfraredCodeByPage(InfraredCode infraredCode) {
		int totalResult=infraredCodeMapper.getCountInfraredCode(infraredCode);
		infraredCode.setTotalResult(totalResult);
		return infraredCodeMapper.findInfraredCodeByPage(infraredCode);
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
	public InfraredCode getInfraredCodeById(String id) {
		return infraredCodeMapper.getInfraredCodeById(id);
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
	public List<InfraredCode> findInfraredCodeByCond(InfraredCode infraredCode) {
		return infraredCodeMapper.findInfraredCodeByCond(infraredCode);
	}

	public List<InfraredCode> findInfraredCodeByCond2(String brandCode,
			String type, String keyNameList) {
		return infraredCodeMapper.findInfraredCodeByCond2(brandCode, type,
				keyNameList);
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
	public void delInfraredCode(String id) {
		infraredCodeMapper.delInfraredCode(id);
	}

	public void delInfraredCodeList(List<String> ids) {
		infraredCodeMapper.delInfraredCodeList(ids);
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
	public void addInfraredCodeList(List<InfraredCode> list) {
		infraredCodeMapper.addInfraredCodeList(list);
	}

	/**
	 * 校验客户码是否存在
	 * 
	 * 创建人：pananz 创建时间：2015-5-18 - 上午10:23:11
	 * 
	 * @param brandId
	 * @param type
	 * @param modelName
	 * @param clientCode
	 * @return 返回类型：boolean
	 * @exception throws
	 */
	public boolean checkCode(String brandId, String type, String modelName,
			String keyName) {
		InfraredCode infraredCode = new InfraredCode();
		infraredCode.setBrandId(brandId);
		infraredCode.setType(type);
		infraredCode.setModelName(modelName);
		infraredCode.setKeyName(keyName);
		List<InfraredCode> list = infraredCodeMapper
				.findInfraredCodeByCond(infraredCode);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		list = null;
		return false;
	}

	/**
	 * 通过excel导入数据 创建人：pananz 创建时间：2012-12-18 - 下午1:47:42
	 * 
	 * @param is
	 * @return 返回类型：String
	 * @exception throws
	 */
	public String addInfraredCode(InputStream is, HttpServletRequest request) {
		List<InfraredCode> list = readCodeExcel(is);
		if (CollectionUtils.isEmpty(list)) {
			return "文件内容不能为空";
		}
		int flag = 0;
		int have = 0;
		int success = 0;
		int except = 0;
		int noType = 0;
		boolean flags = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHave = new StringBuffer();
		StringBuffer sbExcept = new StringBuffer();
		StringBuffer logContent = new StringBuffer();
		StringBuffer logType = new StringBuffer();
		for (InfraredCode node : list) {
			flag++;
			if (StringUtil.isEmpty(node.getType())) {
				logger.error("数据不正确========" + flag + 1);
				noType++;
				logType.append(flag + 1 + ",");
				continue;
			}
			try {
				logContent.append("导入的品牌红包码数据如下：");
				boolean bool = checkCode(node.getBrandId(), node.getType(),
						node.getModelName(), node.getKeyName());
				if (!bool) {
					have++;
					sbHave.append(flag + 1 + "=" + node.getKeyName() + ",");
				} else {
					infraredCodeMapper.addInfraredCode(node);
					logContent.append(node.getBrandName() + "/"
							+ node.getType() + "/" + node.getModelName() + "/"
							+ node.getKeyCode() + ",");
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
			operaLogService.addOperaLog("1", request, "批量导入的品牌红包码",
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
		if (noType > 0) {
			sb.append("不存在类型" + noType + "条,第" + logType.toString() + "行类型不存在");
		}
		return sb.toString();
	}

	private InfraredCode addNode(int j, InfraredCode node, String cell,
			Row row, Map<String, String> typeMap, Map<String, String> brandMap,
			Map<String, String> modelMap) {
		switch (j) {
		case 0:
			node.setType(typeMap.get(cell));
			break;
		case 1:
			node.setBrandId(brandMap.get(cell));
			break;
		case 2:
			String typeCell = ExcelUtil.ConvertCellStr(row.getCell(0));
			String brandCell = ExcelUtil.ConvertCellStr(row.getCell(1));
			String machType = typeMap.get(typeCell);
			String brandId = brandMap.get(brandCell);
			String number = modelMap.get(brandId + "-" +machType+"-" + cell);
			if (StringUtil.isEmpty(number)) {// 如型号没有，则增加，且要审核
				logger.error("======number型号没有============" + number);
				HardModel model = new HardModel();
				model.setNumber(cell);
				model.setMachType(machType);
				model.setBrandId(brandId);
				model.setStatus("1");
				hardModelMapper.addHardModel(model);
				modelMap.put(brandId +  "-" +machType+"-" + cell, model.getId());
			}
			node.setModelName(cell);
			break;
		case 3:
			node.setCodeFormat(cell);
			break;
		case 4:
			node.setClientCode(cell);
			break;
		case 5:
			node.setKeyName(cell);
			break;
		case 6:
			node.setKeyCode(cell);
			break;
		case 7:
			node.setAllCode(cell);
			break;
		default:
			logger.error("文件内容格式不正确");
			break;
		}
		return node;
	}

	private List<InfraredCode> readCodeExcel(InputStream inp) {
		List<InfraredCode> list = new ArrayList<InfraredCode>();
		try {
			String cellStr = null;
			Workbook wb = WorkbookFactory.create(inp);
			InfraredCode node = null;
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets
			// 从第二行开始读取数据
			Brand brand = new Brand();
			List<Brand> brands = brandMapper.findBrandByCond(brand);
			if (CollectionUtils.isEmpty(brands)) {
				return null;
			}
			Map<String, String> brandMap = new HashMap<String, String>();
			for (Brand b : brands) {
				brandMap.put(b.getName(), b.getId());
			}

			List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
			if (CollectionUtils.isEmpty(dicts)) {
				return null;
			}
			Map<String, String> typeMap = new HashMap<String, String>();
			for (Dictionary b : dicts) {
				typeMap.put(b.getDicName(), b.getDicValue());
			}
			HardModel model = new HardModel();
			List<HardModel> hardModels = hardModelMapper
					.findHardModelByCond(model);
			Map<String, String> modelMap = new HashMap<String, String>();
			for (HardModel b : hardModels) {
				modelMap.put(b.getBrandId()+ "-" +b.getMachType()+ "-" + b.getNumber(), b.getId());
			}
			boolean bool = false;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				node = new InfraredCode();
				Row row = sheet.getRow(i); // 获取行(row)对象
				for (int j = 0; j < 8; j++) {
					// 转换接收的单元格
					cellStr = ExcelUtil.ConvertCellStr(row.getCell(j));
					if (j == 0 && StringUtil.isEmpty(cellStr)) {
						bool = true;
						logger.error("第" + i + "==========行,此列已无数据=========停止");
						break;
					}
					// 将单元格的数据添加至一个对象
					addNode(j, node, cellStr, row, typeMap, brandMap, modelMap);
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
