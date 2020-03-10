package com.br.ott.client.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：AreaCache.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-7-21 - 下午5:27:25
 */
public class OperatorsCache {
	private static final Logger logger = Logger.getLogger(OperatorsCache.class);
	public static List<CityOperators> opList = new ArrayList<CityOperators>();

	public static void reload(List<CityOperators> nodes) {
		logger.debug("sysCache内容的运营商更新");
		opList = nodes;
	}

	public static List<CityOperators> findOperators2() {
		List<CityOperators> nodes = opList;
		List<CityOperators> tempList = new ArrayList<CityOperators>();
		for (CityOperators op : nodes) {
			tempList.add(op);
		}
		return tempList;
	}
	public static List<String> findOperatorsId() {
		List<String> tempList = new ArrayList<String>();
		for (CityOperators op : opList) {
			tempList.add(op.getId());
		}
		return tempList;
	}

	/**
	 * 按ID取得运营商信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-10
	 * 
	 * @param @param id
	 * @param @return 返回类型：Operators
	 * @exception throws
	 */
	public static CityOperators getOperatorsById(String id) {
		if (StringUtil.isEmpty(id))
			return null;
		if (CollectionUtils.isEmpty(opList))
			return null;
		CityOperators oper = null;
		for (CityOperators node : opList) {
			if (id.equals(node.getId())) {
				oper = node;
				break;
			}
		}
		return oper;
	}

	/**
	 * 按运营商编码取得运营商信息
	 * 
	 * 创建人：pananz 创建时间：2015-9-10
	 * 
	 * @param @param code
	 * @param @return 返回类型：Operators
	 * @exception throws
	 */
	public static String getOperatorsByCode(String code) {
		if (StringUtil.isEmpty(code))
			return "";
		if (CollectionUtils.isEmpty(opList))
			return "";
		String operators = "";
		for (CityOperators node : opList) {
			if (code.equals(node.getCode())) {
				operators = node.getId();
				break;
			}
		}
		return operators;
	}

	public static String getOperatorsNameByCode(String code) {
		if (StringUtil.isEmpty(code))
			return "";
		if (CollectionUtils.isEmpty(opList))
			return "";
		String operatorsName = "";
		for (CityOperators node : opList) {
			if (code.equals(node.getCode())) {
				operatorsName = node.getName();
				break;
			}
		}
		return operatorsName;
	}

	/**
	 * 通过CODE获取ID
	 * 
	 * 创建人：pananz 创建时间：2016-10-11
	 * 
	 * @param @param code
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	public static String getOperatorsIdByCode(String code) {
		if (StringUtil.isEmpty(code))
			return "";
		if (CollectionUtils.isEmpty(opList))
			return "";
		String operatorsId = "";
		for (CityOperators node : opList) {
			if (code.equals(node.getCode())) {
				operatorsId = node.getId();
				break;
			}
		}
		return operatorsId;
	}

	public static String getOperatorsNameById(String id) {
		if (StringUtil.isEmpty(id))
			return "";
		if (CollectionUtils.isEmpty(opList))
			return "";
		String operatorsName = "";
		for (CityOperators node : opList) {
			if (id.equals(node.getId())) {
				operatorsName = node.getName();
				break;
			}
		}
		return operatorsName;
	}

	public static String getOperatorscCodeById(String id) {
		if (StringUtil.isEmpty(id))
			return "";
		String operatorsCode = "";
		for (CityOperators node : opList) {
			if (id.equals(node.getId())) {
				operatorsCode = node.getCode();
				break;
			}
		}
		return operatorsCode;
	}
}
