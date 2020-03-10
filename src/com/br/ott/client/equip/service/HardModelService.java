package com.br.ott.client.equip.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.mapper.HardModelMapper;
import com.br.ott.client.equip.mapper.InfraredCodeMapper;

/* 
 *  
 *  文件名：ModelService.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-2 - 上午11:46:58
 */
@Service
public class HardModelService {

	@Autowired
	private HardModelMapper hardModelMapper;

	@Autowired
	private InfraredCodeMapper infraredCodeMapper;

	/**
	 * 增加型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:18
	 * 
	 * @param model
	 *            返回类型：void
	 * @exception throws
	 */
	public void addHardModel(HardModel model) {
		hardModelMapper.addHardModel(model);
	}

	/**
	 * 修改型号信息
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:28
	 * 
	 * @param model
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateHardModel(HardModel model) {
		hardModelMapper.updateHardModel(model);
	}

	/**
	 * 按ID查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:36
	 * 
	 * @param id
	 * @return 返回类型：Model
	 * @exception throws
	 */
	public HardModel getHardModelById(String id) {
		return hardModelMapper.getHardModelById(id);
	}

	/**
	 * 按分页查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:46
	 * 
	 * @param model
	 * @return 返回类型：List<Model>
	 * @exception throws
	 */
	public List<HardModel> findHardModelByPage(HardModel model) {
		int totalResult=hardModelMapper.getCountHardModel(model);
		model.setTotalResult(totalResult);
		return hardModelMapper.findHardModelByPage(model);
	}

	/**
	 * 按条件查询型号
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:47:59
	 * 
	 * @param model
	 * @return 返回类型：List<Model>
	 * @exception throws
	 */
	public List<HardModel> findHardModelByCond(HardModel model) {
		return hardModelMapper.findHardModelByCond(model);
	}

	public List<HardModel> findHardModelByType(String macType) {
		return hardModelMapper.findHardModelByType(macType);
	}

	public List<HardModel> findRecHardModelByType(String macType) {
		return hardModelMapper.findRecHardModelByType(macType);
	}

	public boolean checkModelByNumber(String number, String brandId, String machType) {
		HardModel model = new HardModel();
		model.setBrandId(brandId);
		model.setNumber(number);
		model.setMachType(machType);
		List<HardModel> list = findHardModelByCond(model);
		if (CollectionUtils.isNotEmpty(list)) {
			list = null;
			return false;
		}
		return true;
	}

	/**
	 * 修改型号状态
	 * 
	 * 创建人：pananz 创建时间：2015-4-2 - 上午11:48:08
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	public void updateModelStatus(String status, String id) {
		hardModelMapper.updateModelStatus(status, id);
	}

	@Transactional
	public void delHardModelById(String id) {
		HardModel hm = getHardModelById(id);
		hardModelMapper.delHardModelById(id);
		if (hm != null) {
			infraredCodeMapper.delInfraredCodeByCond(hm.getBrandId(),
					hm.getMachType(), hm.getNumber());
		}
	}

	@Transactional
	public void delHardModelByList(List<String> ids) {
		for (String id : ids) {
			HardModel hm = getHardModelById(id);
			hardModelMapper.delHardModelById(id);
			if (hm != null) {
				infraredCodeMapper.delInfraredCodeByCond(hm.getBrandId(),
						hm.getMachType(), hm.getNumber());
			}
		}
	}
}
