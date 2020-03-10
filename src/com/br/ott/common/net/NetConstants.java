/*
 * 文 件 名:  NetConstants.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.net;

/**
 * 网络常量
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class NetConstants {

    private NetConstants() {
    }
    
    /** 请求超时时间 */
    public static final int REQUEST_TIMEOUT = 20 * 1000;
    
    /** 返回超时时间 */
    public static final int RESPONSE_TIMEOUT = 20 * 1000;
    
    /** URL错误 */
    public static final int URL_ERROR = 404;
    
    /** HTTP请求超时 */
    public static final int HTTP_TIMEOUT = 999;
    
    /** 网络连接失败 */
    public static final int CONNECT_ERROR = 888;
    
    /** HTTP请求成功 */
    public static final int HTTP_SUCCESS = 200;
}
