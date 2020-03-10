package com.br.ott.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/***
 * 接口xml处理公用方法类 通过相应条件解析或生成相应xml
 * 
 * @author pKF46373
 * @version [版本号, May 9, 2011]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class XmlUtil {
	private XmlUtil() {

	}

	/**xml节点开始的起始符号**/
	public static final String XML_TAG_BEGIN_BEGIN = "<";
	/**xml节点结束的起始符号**/
	public static final String XML_TAG_END_BEGIN = "</";
	/**xml节点标签的结尾符号**/
	public static final String XML_TAG_END = ">";
	/**xml头部节点名称**/
	public static final String XML_HEADER = "header";
	/**
	 * 实例化一个记录日志
	 */
	private static Logger logger = Logger.getLogger(XmlUtil.class);

	/**
	 * 生成xml请求应答错误头信息 
	 * 根据对应传入的参数生成符合xml规则的字符串
	 * 
	 * @param rspcode 应答的状态码
	 * @param rspdesc 应答的状态描述
	 * @param amount 应答内容总记录数 
	 * @param factcount 应答内容实际记录数 
	 * @return 组装完成的xml头文件
	 * @return String 符合xml规则的字符串
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String createErrorXml(String rspcode, String rspdesc) {
		StringBuffer xmlheaderstring = new StringBuffer();
		//        xmlheaderstring.append(Constants.XML_TOP_LINE);
		//        xmlheaderstring.append(Constants.XML_ROOT_BEGIN);
		//        xmlheaderstring.append(createResponseXmlHeader(rspcode,rspdesc,
		//        		Constants.CMD_AMOUNT, Constants.CMD_FACTCOUNT));
		xmlheaderstring.append(Constants.XML_ROOT_END);
		return xmlheaderstring.toString();
	}

	/**
	 * xml 响应请求生成最后一级并列节点集合，包含父节点 <功能详细描述>
	 * 
	 * @param fnodename 父级节点名称
	 * @param paraMap 子节点集合
	 * @return xml字符串
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String createXmlsnodes(String fnodename, HashMap<String, Object> paraMap) {
		StringBuffer xmlbodystring = new StringBuffer();
		xmlbodystring.append(XML_TAG_BEGIN_BEGIN + fnodename + XML_TAG_END);
		for (Iterator<Entry<String, Object>> it = paraMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
			xmlbodystring.append(XML_TAG_BEGIN_BEGIN + e.getKey() + XML_TAG_END + e.getValue() + XML_TAG_END_BEGIN
					+ e.getKey() + XML_TAG_END);
		}
		xmlbodystring.append(XML_TAG_END_BEGIN + fnodename + XML_TAG_END);
		return xmlbodystring.toString();
	}

	/**
	 * 向客户端输出xml字节流
	 * 
	 * @param request 
	 * @param request
	 * @param xmlstr 返回给请求接口的xml字符串
	 * @return void 不需要返回
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void outXmlToClient(HttpServletRequest request, HttpServletResponse response, String outputXml) {
		//定义返回目标需要的字符串
		StringBuffer xmlString = new StringBuffer();
		OutputStream outs = null;
		try {
			outs = response.getOutputStream();
			response.setContentType(Constants.XML_CONTENTTYPE);
			xmlString.append(outputXml);
			String xmlstr1 = xmlString.toString();
			outs.write((xmlstr1.getBytes(Constants.CHARSET)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (outs != null)
					outs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
