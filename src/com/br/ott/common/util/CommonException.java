/*
 * 文 件 名:  CommonExceptionAction.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pKF46373
 * 修改时间:  2011-10-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.util;
import org.apache.log4j.Logger;

/**
 * 公共异常处理类
 * <功能详细描述>
 * 
 * @author  pKF46373
 * @version  [版本号, 2011-10-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommonException extends Exception{
	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = -6595626121984443240L;
	private Logger  logger=Logger.getLogger(CommonException.class);	
	/**
	 * <默认构造函数>
	 */
	public CommonException(String type, String exceptionmessage){
		if("error".equals(type)){
			logger.error(exceptionmessage);
			return;
		}
		if("warn".equals(type)){
			logger.warn(exceptionmessage);
			return;
		}
		if("debug".equals(type)){
			logger.debug(exceptionmessage);
			return;
		}
		logger.info(exceptionmessage);
	}

}
