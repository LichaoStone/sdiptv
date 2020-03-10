/*
 * 文 件 名:  StringUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46828
 * 修改时间:  2011-7-5
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.br.ott.common.util;

import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.br.ott.Global;

/**
 * <字符串操作>
 * 
 * @author cKF46828
 * @version [版本号, 2011-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class StringUtil {

	// private static LogUtil logUtil = new LogUtil(StringUtil.class);
	private static Logger log = Logger.getLogger(StringUtil.class);

	private StringUtil() {
	}

	/**
	 * 是否为null或空字符串
	 * 
	 * @param str
	 * @return [参数说明]
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为null或空字符串 创建人：pananz 创建时间：2013-1-5 - 上午11:21:15
	 * 
	 * @param str
	 * @return 返回类型：boolean
	 * @exception throws
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * <判断是否为手机号码>
	 * 
	 * @param str
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		String reg = "1[3,5,8]{1}\\d{9}";
		return phoneNumber.matches(reg);
	}

	/**
	 * <判断是否是数字>
	 * 
	 * @param str
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isNumber(String str) {
		String reg = "[0-9]+";
		return str.matches(reg);
	}

	/**
	 * 字符串转为整数(如果转换失败,则返回 -1)
	 * 
	 * @param str
	 * @return [参数说明]
	 * @return int [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int stringToInt(String str) {
		if (isEmpty(str)) {
			return -1;
		}
		try {
			return Integer.parseInt(str.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * 字体串转为boolean (如果转换失败,则返回false)
	 * 
	 * @param str
	 * @return [参数说明]
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean stringToBoolean(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			return Boolean.parseBoolean(str.trim());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * boolean转为字体串
	 * 
	 * @param str
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String BooleanToString(Boolean bool) {
		String booleanString = "false";
		if (bool) {
			booleanString = "true";
		}
		return booleanString;
	}

	/**
	 * <从异常中获取调用栈>
	 * 
	 * @param ex
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getExceptionStackTrace(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		return result;
	}

	/**
	 * <Unicode转化为中文>
	 * 
	 * @param dataStr
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String decodeUnicode(String dataStr) {
		final StringBuffer buffer = new StringBuffer();
		String tempStr = "";
		String operStr = dataStr;
		if (operStr != null && operStr.indexOf("\\u") == -1) {
			return buffer.append(operStr).toString();
		}
		if (operStr != null && !operStr.equals("")
				&& !operStr.startsWith("\\u")) {
			// tempStr = operStr.substring(0, operStr.indexOf("\\u"));
			tempStr = StringUtil.substring(operStr, 0, operStr.indexOf("\\u"));
			// operStr字符一定是以unicode编码字符打头的字符串
			// operStr = operStr.substring(operStr.indexOf("\\u"),
			// operStr.length());
			operStr = StringUtil.substring(operStr, operStr.indexOf("\\u"),
					operStr.length());
		}
		buffer.append(tempStr);
		// 循环处理,处理对象一定是以unicode编码字符打头的字符串
		while (operStr != null && !operStr.equals("")
				&& operStr.startsWith("\\u")) {
			// tempStr = operStr.substring(0, 6);
			tempStr = StringUtil.substring(operStr, 0, 6);
			// operStr = operStr.substring(6, operStr.length());
			operStr = StringUtil.substring(operStr, 6, operStr.length());
			String charStr = "";
			// charStr = tempStr.substring(2, tempStr.length());
			charStr = StringUtil.substring(tempStr, 2, tempStr.length());
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串
			buffer.append(String.valueOf(letter));
			if (operStr.indexOf("\\u") == -1) {
				buffer.append(operStr);
			} else { // 处理operStr使其打头字符为unicode字符
				// tempStr = operStr.substring(0, operStr.indexOf("\\u"));
				tempStr = StringUtil.substring(operStr, 0,
						operStr.indexOf("\\u"));
				// operStr = operStr.substring(operStr.indexOf("\\u"),
				// operStr.length());
				operStr = StringUtil.substring(operStr, operStr.indexOf("\\u"),
						operStr.length());
				buffer.append(tempStr);
			}
		}
		return buffer.toString();
	}

	/**
	 * 字条串截取
	 * 
	 * @param str
	 *            源字符串
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String substring(String str, int start, int end) {
		if (isEmpty(str)) {
			return "";
		}
		int len = str.length();
		if (start > end) {
			return "";
		}
		if (start > len) {
			return "";
		}
		if (end > len) {
			return str.substring(start, len);
		}
		return str.substring(start, end);
	}

	/**
	 * 字条串截取
	 * 
	 * @param str
	 *            源字符串
	 * @param start
	 *            开始位置
	 * @return [参数说明]
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String substring(String str, int start) {
		if (isEmpty(str)) {
			return "";
		}
		int len = str.length();
		if (start > len) {
			return "";
		}
		return str.substring(start);
	}

	/**
	 * 流转换成字符串
	 * 
	 * @param input
	 *            [输入流]
	 * @return [参数说明]
	 * @return String [字符串]
	 */
	public static String streamToString(InputStream input) {

		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(input,
					"UTF-8"));
			String temp = br.readLine();
			while (null != temp && !"".equals(temp)) {
				sb.append(temp);
				temp = br.readLine();
			}
		} catch (IOException e) {
			log.error("流转换string错误！", e);
			return "";
		}
		return sb.toString();
	}

	/**
	 * Request转换字符串
	 * 
	 * @param request
	 *            [Request]
	 * @return String [字符串]
	 */
	public static String requestGetStreamToString(HttpServletRequest request) {
		InputStream input = null;
		try {
			input = request.getInputStream();
			return streamToString(input);
		} catch (IOException e) {
			log.error("获得输入流失败！", e);
			return "";
		}
	}

	public static long parseLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int parseInt(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static float parseFloat(String str) {
		try {
			return Float.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double parseDouble(String str) {
		try {
			return Double.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String transformBase64(String message) {
		if (isEmpty(message)) {
			return "";
		}
		return Base64.encode(message, "UTF-8");
	}

	public static String decodeformBase64(String message) {
		if (isEmpty(message)) {
			return "";
		}
		return Base64.decode(message, "UTF-8");
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}

		return false;

	}

	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
					System.out.println(c);
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 特殊字符去除 创建人：pananz 创建时间：2014-10-9 - 下午4:09:29
	 * 
	 * @param name
	 * @return 返回类型：String
	 * @exception throws
	 */
	public static String filterWord(String name) {
		// 取得关键词前面的内容
		String[] before = Global.KEY_BEFORE_FILTER.split(",");
		for (String key : before) {
			if (name.indexOf(key) > 0) {
				name = name.substring(0, name.indexOf(key));
			}
		}
		// 取得关键词后面的内容
		String[] after = Global.KEY_AFTER_FILTER.split(",");
		for (String key : after) {
			if (name.indexOf(key) > 0) {// 取得英文冒号后面的内容
				name = name.substring(name.indexOf(key) + 1, name.length());
			}
		}
//		name = checkHaveNumber(name);
		return name.trim();
	}

	public static String filterBasicWord(String name) {
		// 取得关键词前面的内容
		String[] before = Global.KEY_BEFORE_FILTER.split(",");
		for (String key : before) {
			if (name.indexOf(key) > 0) {
				name = name.substring(0, name.indexOf(key));
			}
		}
		// 取得关键词后面的内容
		String[] after = Global.KEY_AFTER_FILTER.split(",");
		for (String key : after) {
			if (name.indexOf(key) > 0) {// 取得英文冒号后面的内容
				name = name.substring(name.indexOf(key) + 1, name.length());
			}
		}
		name = filterHaveNumber(name);
		return name.trim();
	}
	
	/**
	 * 末尾数字去除 创建人：pananz 创建时间：2014-10-9 - 下午4:09:09
	 * 
	 * @param name
	 * @return 返回类型：String
	 * @exception throws
	 */
	public static String filterHaveNumber(String name) {
		String[] arr = name.split("");
		String n = arr[arr.length - 1];
		if (isNumber(n)) {// 尾数是数字时，去除后再对字符进行迭代过滤
			name = name.substring(0, arr.length - 2);
			return filterHaveNumber(name);
		} else {
			return name;
		}
	}

	/**
	 * 取得数字
	 * 
	 * 创建人：pananz 创建时间：2015-5-21 - 上午11:01:47
	 * 
	 * @param name
	 * @return 返回类型：String
	 * @exception throws
	 */
	private static String getHaveNumber(String name) {
		String[] arr = name.split("");
		String n = arr[arr.length - 1];
		StringBuffer sb = new StringBuffer();
		if (isNumber(n)) {// 尾数是数字时，去除后再对字符进行迭代过滤
			sb.append(n);
			String m = arr[arr.length - 2];
			if (isNumber(m)) {// 位数是数字时
				sb.append(m);
				String k = arr[arr.length - 3];
				if (isNumber(k)) {// 位数是数字时
					sb.append(k);
				}
			}
		}
		return sb.reverse().toString();
	}

	/**
	 * 取得数字
	 * 
	 * 创建人：pananz 创建时间：2015-4-7 - 下午12:01:16
	 * 
	 * @param word
	 * @return 返回类型：String
	 * @exception throws
	 */
	public static String getHaveLastNum(String word) {
		// 取得关键词后面的内容
		String[] after = Global.WORD_AFTER_FILTER.split(",");
		for (String key : after) {
			if (word.indexOf(key) > 0) {
				word = word.substring(word.indexOf(key) + 1, word.length());
			}
		}
		// 取得关键词前面的内容
		String[] before = Global.WORD_BEFORE_FILTER.split(",");
		for (String key : before) {
			if (word.indexOf(key) > 0) {
				word = word.substring(0, word.indexOf(key));
			}
		}
		if (isNumber(word)) {
			return word;
		} else {
			String num = getHaveNumber(word);
			if (isNotEmpty(num)) {
				return num;
			}
		}
		return "0";
	}

	public static String getJsonStr(JSONObject json, String str) {
		return json.get(str) == null ? "" : json.getString(str);
	}

	// 一级特殊(北京市,上海市,天津市,重庆市) 二级 市,盟,地区,自治州
	public static String filterAddress(String address) {
		if (StringUtil.isEmpty(address)) {
			return address;
		}
		if (address.indexOf("香港") >= 0) {
			return "香港";
		} else if (address.indexOf("澳门") >= 0) {
			return "澳门";
		} else if (address.indexOf("台湾") >= 0) {
			return "台湾";
		}
		if (address.indexOf("市") > 0) {// 有“市”标示时
			if (address.indexOf("省") > 0) {
				return address.substring(address.indexOf("省") + 1,
						address.indexOf("市") + 1);
			} else if (address.indexOf("自治区") > 0) {
				return address.substring(address.indexOf("自治区") + 3,
						address.indexOf("市") + 1);
			} else {// 直辖市
				return address.substring(0, address.indexOf("市") + 1);
			}
		} else if (address.indexOf("盟") > 0) {
			if (address.indexOf("省") > 0) {
				return address.substring(address.indexOf("省") + 1,
						address.indexOf("盟") + 1);
			} else if (address.indexOf("自治区") > 0) {
				return address.substring(address.indexOf("自治区") + 3,
						address.indexOf("盟") + 1);
			}
		} else if (address.indexOf("自治州") > 0) {
			if (address.indexOf("省") > 0) {
				return address.substring(address.indexOf("省") + 1,
						address.indexOf("自治州") + 3);
			} else if (address.indexOf("自治区") > 0) {
				return address.substring(address.indexOf("自治区") + 3,
						address.indexOf("自治州") + 3);
			}
		} else if (address.indexOf("地区") > 0) {
			if (address.indexOf("省") > 0) {
				return address.substring(address.indexOf("省") + 1,
						address.indexOf("地区") + 2);
			} else if (address.indexOf("自治区") > 0) {
				return address.substring(address.indexOf("自治区") + 3,
						address.indexOf("地区") + 2);
			}
		} else {// 只要一级区域
			if (address.indexOf("县") > 0) {
				if (address.indexOf("省") > 0) {
					return address.substring(address.indexOf("省") + 1,
							address.indexOf("县") + 1);
				} else if (address.indexOf("自治区") > 0) {
					return address.substring(address.indexOf("自治区") + 3,
							address.indexOf("县") + 1);
				}
			}
		}
		return address;
	}

	/**
	 * 取前市/盟/自治州前的名称
	 * 
	 * 创建人：pananz 创建时间：2015-7-10 - 上午11:11:14
	 * 
	 * @param address
	 * @return 返回类型：String
	 * @exception throws
	 */
	public static String getCityName(String address) {
		if (address.indexOf("市") > 0) {// 有“市”标示时
			return address.substring(0, address.indexOf("市"));
		} else if (address.indexOf("盟") > 0) {
			return address.substring(0, address.indexOf("盟"));
		} else if (address.indexOf("自治州") > 0) {
			return address.substring(0, address.indexOf("自治州") + 3);
		} else {
			return address;
		}
	}

	public static String getFilterArea(String address) {
		// 广东省广州市海珠区广州大道南228号
		if (StringUtil.isEmpty(address)) {
			return "";
		}
		/**
		 * 区/县 优先排除
		 */
		if (address.lastIndexOf("区") > 0) {
			if (address.lastIndexOf("市") > 0) {
				return address.substring(address.indexOf("市") + 1,
						address.lastIndexOf("区") + 1);
			} else if (address.lastIndexOf("州") > 0) {// 当区县时，其前面出现州
				return address.substring(address.lastIndexOf("州") + 1,
						address.indexOf("区") + 1);
			}
		} else if (address.indexOf("县") > 0) {
			if (address.lastIndexOf("市") > 0) {// 当有县时，其前面出现市
				return address.substring(address.lastIndexOf("市") + 1,
						address.indexOf("县") + 1);
			} else if (address.lastIndexOf("州") > 0) {// 当有县时，其前面出现州
				return address.substring(address.lastIndexOf("州") + 1,
						address.indexOf("县") + 1);
			} else {
				if (address.indexOf("省") > 0) {
					return address.substring(address.indexOf("省") + 1,
							address.indexOf("县") + 1);
				}
			}
		} else {
			if (address.lastIndexOf("市") > 0) {
				if (address.indexOf("州") > 0) {
					return address.substring(address.indexOf("州") + 1,
							address.lastIndexOf("市") + 1);
				}
				if (address.indexOf("省") > 0) {
					String addr = address.substring(address.indexOf("省") + 1,
							address.lastIndexOf("市") + 1);
					if (addr.indexOf("市") != addr.lastIndexOf("市")) {
						return address.substring(address.indexOf("市") + 1,
								address.lastIndexOf("市") + 1);
					} else {
						return addr;
					}
				}
			}
		}
		// 区 县/市
		return address;
	}

	public static void main(String[] args) {
		// System.out.println(filterWord("海豚星光剧场：好心作怪2 18"));
		// System.out.println(getHaveLastNum("2014/2015赛季NBA季后赛:奇才-老鹰"));
//		System.out.println(filterAddress("香港海珠县广州大道南228号"));
		System.out.println(filterBasicWord("电视剧：决杀22/40"));
	}
}
