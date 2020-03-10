/*
 * 文 件 名:  LogUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-11-8
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.util.log;

import org.apache.log4j.Logger;

/**
 * 日志输出
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-11-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class LogUtil {
    
    private Logger logger;
    
    public LogUtil(Class<?> clazz) {
        logger = Logger.getLogger(clazz);
    }
    
    public void info(String str) {
        logger.info(str);
    }
    
    public void warn(String str) {
        logger.warn(str);
    }
    
    public void error(String str) {
        logger.error(str);
    }
    
    public void debug(String str) {
        logger.debug(str);
    }
}
