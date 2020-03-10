package com.br.ott.client.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseApiController;
import com.br.ott.client.JsonFormat;
import com.br.ott.client.api.entity.ResponseArea;
import com.br.ott.client.api.entity.ResponseBrand;
import com.br.ott.client.api.entity.ResponseCode;
import com.br.ott.client.api.entity.ResponseCode2;
import com.br.ott.client.api.entity.ResponseCustomer;
import com.br.ott.client.api.entity.ResponseModel;
import com.br.ott.client.api.entity.ResponseOperators;
import com.br.ott.client.api.entity.ResponseOperatorsBrand;
import com.br.ott.client.api.entity.ResponseType;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.equip.entity.Area;
import com.br.ott.client.equip.entity.BrandSupport;
import com.br.ott.client.equip.entity.FeedbackBrand;
import com.br.ott.client.equip.entity.FeedbackOperators;
import com.br.ott.client.equip.entity.HardModel;
import com.br.ott.client.equip.entity.InfraredCode;
import com.br.ott.client.equip.entity.Operators;
import com.br.ott.client.equip.entity.OperatorsCode;
import com.br.ott.client.equip.service.AreaService;
import com.br.ott.client.equip.service.BrandSupportService;
import com.br.ott.client.equip.service.FeedbackBrandService;
import com.br.ott.client.equip.service.FeedbackOperatorsService;
import com.br.ott.client.equip.service.HardModelService;
import com.br.ott.client.equip.service.InfraredCodeService;
import com.br.ott.client.equip.service.OperatorsCodeService;
import com.br.ott.client.equip.service.OperatorsService;
import com.br.ott.client.live.entity.CityOperators;
import com.br.ott.client.live.service.CityOperatorsService;
import com.br.ott.client.market.entity.Customer;
import com.br.ott.client.market.service.CustomerService;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.JsonUtils;
import com.br.ott.common.util.StringUtil;

/* 
 *  红外遥控数据接口类
 *  文件名：InfraredRcController
 *  创建人：pengwei
 *  创建时间：2015-4-10 - 上午11:21:30
 */
@Controller
@RequestMapping(value = "/rcapi")
public class InfraredRcController extends BaseApiController {
	private static Logger log = Logger.getLogger(InfraredRcController.class);

	@Autowired
	private HardModelService hardModelService;
	@Autowired
	private InfraredCodeService infraredCodeService;
	@Autowired
	private OperatorsService operatorsService;
	@Autowired
	private OperatorsCodeService operatorsCodeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private BrandSupportService brandSupportService;
	@Autowired
	private FeedbackBrandService feedbackBrandService;
	@Autowired
	private FeedbackOperatorsService feedbackOperatorsService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CityOperatorsService cityOperatorsService;

	/**
	 * 设备分类获取接口(电视，空调，机顶盒，....等)，设备分类通过数据字典维护即可
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "findType")
	public @ResponseBody
	String findType(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Dictionary> dicts = DictCache.getDictList(DicType.EQUIP_YJXH);
			BeanComparator menuBC = new BeanComparator("orderId");// 排序
			Collections.sort(dicts, menuBC);
			List<ResponseType> list = new ArrayList<ResponseType>();
			for (Dictionary dict : dicts) {
				String name = dict.getDicName();
				if ("有线机顶盒_卫星_IPTV".equals(dict.getDicName())) {
					name = "机顶盒（有线）/卫星/IPTV";
				} else if ("智能机顶盒_Android".equals(dict.getDicName())) {
					name = "智能机顶盒（Android）";
				}
				ResponseType type = new ResponseType(name, dict.getDicValue());
				list.add(type);
			}
			JSON json = JSONSerializer.toJSON(list);
			list = null;
			return success(response, json.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9001", e.getMessage());
		}
	}

	/**
	 * 通过前端提供的终端所在地市区县，查询相关地市区县拥有的运营商列表（IPTV，有线机顶盒类型的独立接口）
	 * 后台需要增加地市区县维护管理和地市区县拥有的运营商管理 步骤： 1.服务端获取到参数以后通过规则提取到区或者县地址
	 * 2.将第1步取到的值，匹配地市区县运营商表，提取到当前区县所有的运营商类型信息返回给终端
	 * 
	 * @param request
	 *            地市区县全称，如：（广东省广州市海珠区广州大道南228号）
	 * @param response
	 * 
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperators")
	public @ResponseBody
	String findOperators(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "area", false);
			String address = StringUtil.getJsonStr(jsonStr, "area");
			String city = StringUtil.filterAddress(address);
			log.debug("获取红外运营商请求发起地址：" + address + ",过虑分析后的区域为：" + city);
			List<Operators> opList = operatorsService.findOperatorsByArea(city);
			List<ResponseArea> spTypes = new ArrayList<ResponseArea>();
			if (CollectionUtils.isEmpty(opList)) {// 当过虑后的区域无运营商时，
//				String spType = StringUtil.getCityName(city) + "有线";
//				ResponseArea ra = new ResponseArea(spType, city, "-1");
//				spTypes.add(ra);
				return success(response, "{}");
			} else {
				for (Operators op : opList) {
					ResponseArea ra = new ResponseArea(op.getSpName(), city,
							"0");
					spTypes.add(ra);
				}
			}
			// 暂时不添加默认IPTV类型
			// String area_iptv = StringUtil.getCityName(city) + "IPTV";
			// ResponseArea ra2 = new ResponseArea(area_iptv, city, "1");
			// spTypes.add(ra2);
			List<CityOperators> cpList = cityOperatorsService
					.findCityOperatorsByArea(city);
			if (CollectionUtils.isNotEmpty(opList)) {
				for (CityOperators op : cpList) {
					String spName = op.getName();
					if (spName.indexOf("电信") >= 0 || spName.indexOf("联通") >= 0) {
						ResponseArea ra = new ResponseArea(spName, "IPTV",
								op.getId());
						if (spTypes.contains(ra)) {
							spTypes.remove(ra);
						}
						spTypes.add(ra);
					}
				}
			}
			JSON json = JSONSerializer.toJSON(spTypes);
			opList = null;
			spTypes = null;
			return success(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return error(response, "tv_code_9002", e.getMessage());
		}
	}

	/**
	 * 查询运营商(红外方案)品牌
	 * 
	 * 创建人：pananz 创建时间：2015-9-11
	 * 
	 * @param @param request
	 * @param @param response
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperatorsBrand")
	public @ResponseBody
	String findOperatorsBrand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			String spName = StringUtil.getJsonStr(jsonStr, "spName");
			String isSpecial = StringUtil.getJsonStr(jsonStr, "isSpecial");
			String city = StringUtil.getJsonStr(jsonStr, "city");
			if (spName.indexOf("电信") >= 0 || spName.indexOf("联通") >= 0) {
				List<Operators> opList = operatorsService
						.findOperatorsBySpName("IPTV");
				List<ResponseOperatorsBrand> obs = new ArrayList<ResponseOperatorsBrand>();
				for (Operators oper : opList) {
					ResponseOperatorsBrand ob = new ResponseOperatorsBrand(
							oper.getOtherName(), city, isSpecial);
					obs.add(ob);
				}
				JSON json = JSONSerializer.toJSON(obs);
				opList = null;
				obs = null;
				return success(response, json.toString());
			} else {
				return success(response, "{}");
			}
		} catch (OTTException e) {
			e.printStackTrace();
			return error(response, "tv_code_9002s", e.getMessage());
		}
	}

	/**
	 * 按运营商类型查询二级运营商级红外信息
	 * 
	 * 创建人：pananz 创建时间：2015-7-13 - 下午5:19:37
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findOperators2")
	public @ResponseBody
	String findOperators2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "spName", false);
			String spName = StringUtil.getJsonStr(jsonStr, "spName");
			String isSpecial = StringUtil.getJsonStr(jsonStr, "isSpecial");
			String city = StringUtil.getJsonStr(jsonStr, "city");
			List<Operators> opList = new ArrayList<Operators>();
			if ("-1".equals(isSpecial)) {
				Area area = areaService.getAreaCapitalByName(city);
				if (area == null) {
					return success(response, JsonFormat.JSON_RESULT_EMPTY);
				}
				Operators operators = new Operators();
				operators.setParentId(area.getParentId());
				operators.setStatus("1");
				operators.setOrderColumn("o.sequence,o.id");
				opList = operatorsService.findOperatorsByCond(operators);
			} else {
				// 运营商地区有IPTV的，默认其为IPTV类型运营商，返回所有IPTV类型的给客户端
				if ("IPTV".equals(city)) {
					Operators operators = new Operators();
					operators.setOtherName(spName);
					operators.setAreaName("IPTV");
					operators.setSpName("IPTV");
					operators.setStatus("1");
					operators.setOrderColumn("o.sequence,o.id");
					opList = operatorsService.findOperatorsByCond(operators);
				} else {
					Operators operators = new Operators();
					operators.setAreaName(city);
					operators.setSpName(spName);
					operators.setStatus("1");
					operators.setOrderColumn("o.sequence,o.id");
					opList = operatorsService.findOperatorsByCond(operators);
				}
			}
			List<ResponseOperators> operators = getOperators(opList, city,
					isSpecial);
			JSON json = JSONSerializer.toJSON(operators);
			opList = null;
			operators = null;
			return success(response, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return error(response, "tv_code_9002n", e.getMessage());
		}
	}

	/**
	 * 构建运营商红外信息
	 * 
	 * 创建人：pananz 创建时间：2015-7-13 - 下午5:20:17
	 * 
	 * @param opList
	 * @param city
	 * @return 返回类型：List<ResponseOperators>
	 * @exception throws
	 */
	private List<ResponseOperators> getOperators(List<Operators> opList,
			String city, String isSpecial) {
		List<String> opStr = new ArrayList<String>();
		Map<String, ResponseOperators> map = new HashMap<String, ResponseOperators>();
		for (Operators op : opList) {
			ResponseOperators ro = new ResponseOperators();
			ro.setCode(op.getId());
			ro.setName(op.getName());
			if ("-1".equals(isSpecial) || "0".equals(isSpecial)) {
				ro.setOperators(op.getCityOperators());
			} else {
				ro.setOperators(isSpecial);
			}
			ro.setOtherName(op.getOtherName());
			ro.setArea(city);
			ro.setSpName(op.getSpName());
			map.put(op.getId(), ro);
			opStr.add(op.getId());
		}
		List<String> keyList = new ArrayList<String>();
		keyList.add("volume_add");
		keyList.add("shutdown");
		keyList.add("ok");
		List<OperatorsCode> icList = operatorsCodeService
				.findOperatorsCodeByOPList(StringUtils.join(opStr, ","),
						StringUtils.join(keyList, ","));
		for (OperatorsCode ic : icList) {
			ResponseOperators ra = map.get(ic.getOperators());
			if (ra == null) {
				continue;
			}
			ResponseCode2 rc = new ResponseCode2();
			rc.setAllCode(ic.getAllCode());
			rc.setClientCode(ic.getClientCode());
			rc.setCodeFormat(ic.getCodeFormat());
			rc.setKeyCode(ic.getKeyCode());
			rc.setKeyName(ic.getKeyName());
			rc.setType(ic.getType());
			List<ResponseCode2> codes = ra.getCodes();
			if (CollectionUtils.isEmpty(codes)) {
				codes = new ArrayList<ResponseCode2>();
			}
			codes.add(rc);
			ra.setCodes(codes);
			map.put(ic.getOperators(), ra);
		}
		List<ResponseOperators> operators = new ArrayList<ResponseOperators>();
		for (String key : opStr) {
			ResponseOperators ra = map.get(key);
			if (ra == null) {
				continue;
			}
			operators.add(ra);
		}
		opStr = null;
		return operators;
	}

	/**
	 * 通过分类类型（电视，空调等）获取品牌列表
	 * 
	 * @param request
	 *            分类类型值
	 * @param response
	 *            品牌列表（海信，创维...等）
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findBrands")
	public @ResponseBody
	String findBrands(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "typeCode", false);
			String typeCode = StringUtil.getJsonStr(jsonStr, "typeCode");
			log.debug("分类获取品牌请求的参数为：typeCode=" + typeCode);
			List<HardModel> recBrands = hardModelService
					.findRecHardModelByType(typeCode);
			List<ResponseBrand> list = new ArrayList<ResponseBrand>();
			for (HardModel hm : recBrands) {
				list.add(new ResponseBrand(hm.getBrandName(), hm.getBrandCode()));
			}

			List<HardModel> models = hardModelService
					.findHardModelByType(typeCode);
			List<ResponseBrand> list2 = new ArrayList<ResponseBrand>();
			for (HardModel hm : models) {
				list2.add(new ResponseBrand(hm.getBrandName(), hm
						.getBrandCode()));
			}
			JSONObject jsonObject = new JSONObject();
			JSONArray recArray = (JSONArray) JSONSerializer.toJSON(list);
			jsonObject.put("rec", recArray.toString());
			JSONArray allArray = (JSONArray) JSONSerializer.toJSON(list2);
			jsonObject.put("all", allArray.toString());
			recBrands = null;
			models = null;
			list2 = null;
			list = null;
			return success(response, jsonObject.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9003", e.getMessage());
		}
	}

	/**
	 * 通过品牌获取型号列表 品牌型号信息中需要带上一个（音量+ 按键全码信息与电源按键全码信息）作为一个属性
	 * 
	 * @param request
	 *            品牌值
	 * @param response
	 *            型号列表
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findModels")
	public @ResponseBody
	String findModels(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date date = new Date();
			log.debug("品牌获取型号请求进入时间：" + DateUtil.toString(date));
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "brandCode", false);
			String brandCode = StringUtil.getJsonStr(jsonStr, "brandCode");
			String typeCode = StringUtil.getJsonStr(jsonStr, "typeCode");
			log.debug("品牌获取型号请求的参数为：brandCode=" + brandCode + ",typeCode="
					+ typeCode);
			log.debug("品牌获取型号解析参数时间：" + DateUtil.dateDiff(date, new Date()));
			String keyNameList = "";
			if ("TV".equals(typeCode)) {// 电视时，返回三个键值信息volume_add,shutdown,single
				List<String> keyList = new ArrayList<String>();
				keyList.add("volume_add");
				keyList.add("shutdown");
				keyList.add("single");
				keyNameList = StringUtils.join(keyList, ",");
			} else if ("AC".equals(typeCode)) {// 空调时，返回所有
				List<String> keyList = new ArrayList<String>();
				keyList.add("AC");
				keyNameList = StringUtils.join(keyList, ",");
			} else if ("DCF".equals(typeCode)) {// 风扇
				List<String> keyList = new ArrayList<String>();
				keyList.add("shutdown");
				keyList.add("snaking");
				keyNameList = StringUtils.join(keyList, ",");
			} else {// 其他返回三个键值信息volume_add,shutdown,ok
				List<String> keyList = new ArrayList<String>();
				keyList.add("volume_add");
				keyList.add("shutdown");
				keyList.add("ok");
				keyNameList = StringUtils.join(keyList, ",");
			}
			List<ResponseModel> list = getModelInfo(brandCode, typeCode,
					keyNameList);
			log.debug("获取品牌型号键值信息后时间：" + DateUtil.dateDiff(date, new Date()));
			JSON json = JSONSerializer.toJSON(list);
			list = null;
			return success(response, json.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9004", e.getMessage());
		}
	}

	/**
	 * 取得品牌型号与红外码信息
	 * 
	 * 创建人：pananz 创建时间：2015-7-27 - 下午2:51:28
	 * 
	 * @param brandCode
	 * @param typeCode
	 * @param keyNameList
	 * @return 返回类型：List<ResponseModel>
	 * @exception throws
	 */
	private List<ResponseModel> getModelInfo(String brandCode, String typeCode,
			String keyNameList) {
		List<InfraredCode> ics = infraredCodeService.findInfraredCodeByCond2(
				brandCode, typeCode, keyNameList);
		if (CollectionUtils.isEmpty(ics)) {
			log.debug("无品牌型号红外码信息");
			return null;
		}
		Map<String, ResponseModel> map = new HashMap<String, ResponseModel>();
		List<String> icStr = new ArrayList<String>();
		for (InfraredCode ic : ics) {
			String key = typeCode + "@_@" + brandCode + "@_@"
					+ ic.getModelName();
			log.debug("key=" + key);
			if (!icStr.contains(key)) {
				icStr.add(key);
			}
			ResponseModel rm = map.get(key);
			if (null == rm) {
				rm = new ResponseModel();
				rm.setBrandCode(brandCode);
				rm.setTypeName(ic.getTypeOtherName());
				rm.setModel(ic.getModelName());
			}
			ResponseCode code = new ResponseCode();
			code.setAllCode(ic.getAllCode());
			code.setBrand(ic.getBrandName());
			code.setClientCode(ic.getClientCode());
			code.setCodeFormat(ic.getCodeFormat());
			code.setKeyCode(ic.getKeyCode());
			code.setKeyName(ic.getKeyName());
			code.setNumber(ic.getModelName());
			code.setType(ic.getType());
			List<ResponseCode> codes = rm.getCodes();
			if (CollectionUtils.isEmpty(codes)) {
				codes = new ArrayList<ResponseCode>();
			}
			codes.add(code);
			rm.setCodes(codes);
			map.put(key, rm);
		}
		List<ResponseModel> list = new ArrayList<ResponseModel>();
		for (String key : icStr) {
			ResponseModel rm = map.get(key);
			if (null == rm) {
				log.debug("找不到品牌型号信息");
				continue;
			}
			list.add(rm);
		}
		icStr = null;
		ics = null;
		return list;
	}

	/**
	 * 通过品牌型号类型获取当前型号设备的所有键值名，键和全码列表
	 * 
	 * @param request
	 *            品牌型号值
	 * @param response
	 *            型号列表
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCodeValues")
	public @ResponseBody
	String findCodeValues(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "brandCode", false);
			getStringFromJsonObject(jsonStr, "model", false);
			String brandCode = StringUtil.getJsonStr(jsonStr, "brandCode");
			String model = StringUtil.getJsonStr(jsonStr, "model");
			String typeCode = StringUtil.getJsonStr(jsonStr, "typeCode");
			log.debug("品牌型号获取红外码请求的参数为：brandCode=" + brandCode + " ,model="
					+ model + " ,typeCode=" + typeCode);

			InfraredCode infraredCode = new InfraredCode();
			infraredCode.setBrandCode(brandCode);
			infraredCode.setModelName(model);
			infraredCode.setType(typeCode);
			infraredCode.setStatus("1");
			infraredCode.setOrderColumn("sequence desc");
			List<InfraredCode> codes = infraredCodeService
					.findInfraredCodeByCond(infraredCode);
			List<ResponseCode> list = new ArrayList<ResponseCode>();
			for (InfraredCode ic : codes) {
				ResponseCode code = new ResponseCode();
				code.setAllCode(ic.getAllCode());
				code.setBrand(ic.getBrandName());
				code.setClientCode(ic.getClientCode());
				code.setCodeFormat(ic.getCodeFormat());
				code.setKeyCode(ic.getKeyCode());
				code.setKeyName(ic.getKeyName());
				code.setNumber(ic.getModelName());
				code.setType(ic.getType());
				list.add(code);
			}
			JSON json = JSONSerializer.toJSON(list);
			codes = null;
			list = null;
			return success(response, json.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9005", e.getMessage());
		}
	}

	/**
	 * 通过运营商获取红外码信息
	 * 
	 * 返回信息 number codeFormat clientCode keyName keyCode allCode
	 * 
	 * 
	 * 创建人：pananz 创建时间：2015-4-15 - 下午2:52:16
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCodeValues2")
	public @ResponseBody
	String findCodeValues2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "code", false);
			String code = StringUtil.getJsonStr(jsonStr, "code");
			log.debug("运营商获取红外码请求的参数为：code=" + code);
			OperatorsCode infraredCode = new OperatorsCode();
			infraredCode.setOperators(code);
			infraredCode.setStatus("1");
			List<OperatorsCode> icList = operatorsCodeService
					.findOperatorsCodeByCond(infraredCode);
			List<ResponseCode2> list = new ArrayList<ResponseCode2>();
			for (OperatorsCode ic : icList) {
				ResponseCode2 rc = new ResponseCode2();
				rc.setAllCode(ic.getAllCode());
				rc.setClientCode(ic.getClientCode());
				rc.setCodeFormat(ic.getCodeFormat());
				rc.setKeyCode(ic.getKeyCode());
				rc.setKeyName(ic.getKeyName());
				rc.setType(ic.getType());
				list.add(rc);
			}
			JSON json = JSONSerializer.toJSON(list);
			icList = null;
			list = null;
			return success(response, json.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9006", e.getMessage());
		}
	}

	/**
	 * 查询支持手机红外功能的手机品牌
	 * 
	 * 创建人：pananz 创建时间：2015-6-8 - 下午12:08:36
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findBrandSupport")
	public @ResponseBody
	String findBrandSupport(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BrandSupport brandSupport = new BrandSupport();
			brandSupport.setStatus("1");
			brandSupport.setOrderColumn("sequence");
			List<BrandSupport> list = brandSupportService
					.findBrandSupportByCond(brandSupport);
			JsonConfig jsonConfig = JsonUtils.excludeConfig(new String[] {
					"status", "showCount", "totalPage", "totalResult",
					"orderColumn", "currentPage", "currentResult",
					"entityOrField", "pageStr", "id", "sequence", "logo",
					"showRowSelect" });
			JSON json = JSONSerializer.toJSON(list, jsonConfig);
			list = null;
			return success(response, json.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9007", e.getMessage());
		}
	}

	/**
	 * 品牌型号反馈
	 * 
	 * 创建人：pananz 创建时间：2015-6-11 - 下午3:19:09
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "feedbackBrand")
	public @ResponseBody
	String feedbackBrand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "hardType", false);
			getStringFromJsonObject(jsonStr, "brandName", false);
			getStringFromJsonObject(jsonStr, "modelName", false);

			String hardType = StringUtil.getJsonStr(jsonStr, "hardType");
			String brandName = StringUtil.getJsonStr(jsonStr, "brandName");
			String modelName = StringUtil.getJsonStr(jsonStr, "modelName");
			String address = StringUtil.getJsonStr(jsonStr, "address");
			String phoneModel = StringUtil.getJsonStr(jsonStr, "phoneModel");
			String feedMan = StringUtil.getJsonStr(jsonStr, "feedMan");

			String feedType = StringUtil.getJsonStr(jsonStr, "feedType");
			String feedContent = StringUtil.getJsonStr(jsonStr, "feedContent");

			log.debug("品牌型号反馈的参数为：hardType=" + hardType + ",brandName="
					+ brandName + ",modelName=" + modelName + " ,address="
					+ address + " ,phoneModel=" + phoneModel);
			FeedbackBrand feedback = new FeedbackBrand();
			feedback.setHardType(hardType);
			feedback.setBrandName(brandName);
			feedback.setModelName(modelName);
			feedback.setAddress(address);
			feedback.setPhoneModel(phoneModel);
			feedback.setFeedMan(feedMan);
			feedback.setFeedType(feedType);
			feedback.setFeedContent(feedContent);

			feedbackBrandService.addFeedbackBrand(feedback);
			return success(response, "{\"result\":\"1\",\"msg\":\"品牌型号反馈成功\"}");
		} catch (Exception e) {
			return error(response, "tv_code_9008", "品牌型号反馈异常");
		}
	}

	/**
	 * 
	 * 运营商品牌型号反馈 创建人：pananz 创建时间：2015-6-26 - 下午3:31:10
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "feedbackOperators")
	public @ResponseBody
	String feedbackOperators(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "hardType", false);
			getStringFromJsonObject(jsonStr, "brandName", false);
			getStringFromJsonObject(jsonStr, "modelName", false);
			getStringFromJsonObject(jsonStr, "phoneModel", false);
			getStringFromJsonObject(jsonStr, "operators", false);
			String hardType = StringUtil.getJsonStr(jsonStr, "hardType");
			String brandName = StringUtil.getJsonStr(jsonStr, "brandName");
			String modelName = StringUtil.getJsonStr(jsonStr, "modelName");
			String feedMan = StringUtil.getJsonStr(jsonStr, "feedMan");
			String phoneModel = StringUtil.getJsonStr(jsonStr, "phoneModel");
			String operators = StringUtil.getJsonStr(jsonStr, "operators");
			String province = StringUtil.getJsonStr(jsonStr, "province");
			String city = StringUtil.getJsonStr(jsonStr, "city");
			String address = StringUtil.getJsonStr(jsonStr, "address");
			String feedType = StringUtil.getJsonStr(jsonStr, "feedType");
			String feedContent = StringUtil.getJsonStr(jsonStr, "feedContent");

			log.debug("运营商品牌型号反馈的参数为：hardType=" + hardType + ",brandName="
					+ brandName + ",modelName=" + modelName + " phoneModel="
					+ phoneModel + " operators=" + operators + ", province="
					+ province + ", city=" + city);
			FeedbackOperators feedback = new FeedbackOperators();
			feedback.setHardType(hardType);
			feedback.setBrandName(brandName);
			feedback.setModelName(modelName);
			feedback.setOperators(operators);
			feedback.setProvince(province);
			feedback.setCity(city);
			feedback.setAddress(address);
			feedback.setPhoneModel(phoneModel);
			feedback.setFeedMan(feedMan);
			feedback.setFeedType(feedType);
			feedback.setFeedContent(feedContent);
			feedbackOperatorsService.addFeedbackOperators(feedback);
			return success(response,
					"{\"result\":\"1\",\"msg\":\"运营商品牌型号反馈成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			return error(response, "tv_code_9009", "运营商品牌型号反馈异常");
		}
	}

	/**
	 * 查询客服账号信息
	 * 
	 * 创建人：pananz 创建时间：2015-8-31
	 * 
	 * @param @param request
	 * @param @param response
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCustomer")
	public @ResponseBody
	String findCustomer(HttpServletRequest request, HttpServletResponse response) {
		try {
			Customer customer = new Customer();
			customer.setStatus("1");
			List<Customer> list = customerService.findCustomerByCond(customer);
			JSONObject jsonObject = new JSONObject();
			if (CollectionUtils.isNotEmpty(list)) {
				List<ResponseCustomer> qqList = new ArrayList<ResponseCustomer>();
				List<ResponseCustomer> wxList = new ArrayList<ResponseCustomer>();
				List<ResponseCustomer> wbList = new ArrayList<ResponseCustomer>();
				for (Customer c : list) {
					ResponseCustomer rc = new ResponseCustomer(
							c.getCustomerName(), c.getCustomerNo(),
							c.getHeadUrl(), c.getType());
					if ("1".equals(c.getType())) {
						if (!qqList.contains(rc)) {
							qqList.add(rc);
						}
					} else if ("2".equals(c.getType())) {
						if (!wxList.contains(rc)) {
							wxList.add(rc);
						}
					} else if ("3".equals(c.getType())) {
						if (!wbList.contains(rc)) {
							wbList.add(rc);
						}
					}
				}
				JSONArray qqArray = (JSONArray) JSONSerializer.toJSON(qqList);
				jsonObject.put("qq", qqArray.toString());
				JSONArray wxArray = (JSONArray) JSONSerializer.toJSON(wxList);
				jsonObject.put("wx", wxArray.toString());
				JSONArray wbArray = (JSONArray) JSONSerializer.toJSON(wbList);
				jsonObject.put("wb", wbArray.toString());
				qqList = null;
				wxList = null;
				wbList = null;
			}
			list = null;
			return success(response, jsonObject.toString());
		} catch (Exception e) {
			return error(response, "tv_code_9011", e.getMessage());
		}
	}

	/**
	 * 按地区取得EPG运营商信息
	 * 
	 * 创建人：pananz 创建时间：2015-6-15 - 上午10:09:19
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * @exception throws
	 */
	@RequestMapping(value = "findCityOperators")
	public @ResponseBody
	String findCityOperators(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonStr = getJSONObj(request);
			getStringFromJsonObject(jsonStr, "area", false);
			String address = jsonStr.get("area") == null ? ""
					: (String) jsonStr.get("area");
			String city = StringUtil.filterAddress(address);
			log.debug("获取EPG运营商请求发起地址：" + address + ",过虑分析后的区域为：" + city);
			List<CityOperators> opList = cityOperatorsService
					.findCityOperatorsByArea(city);
			if (CollectionUtils.isEmpty(opList)) {// 当过虑后的区域无运营商时，取得上一级的省会城市
				Area area = areaService.getAreaCapitalByName(city);
				if (area == null) {
					return success(response, JSONSerializer.toJSON(null)
							.toString());
				} else {
					opList = cityOperatorsService.findCityOperatorsByArea(area
							.getName());
				}
			}
			List<ResponseOperators> list = new ArrayList<ResponseOperators>();
			for (CityOperators co : opList) {
				ResponseOperators ra = new ResponseOperators();
				ra.setArea(co.getAreaName());
				ra.setCode(co.getId());
				ra.setName(co.getName());
				list.add(ra);
			}
			return success(response, JSONSerializer.toJSON(list).toString());
		} catch (Exception e) {
			return error(response, "tv_code_9010", e.getMessage());
		}
	}
}
