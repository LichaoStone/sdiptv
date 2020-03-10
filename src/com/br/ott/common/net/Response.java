/*
 * 文 件 名:  Response.java
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
 * 网络请求返回对象
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class Response {

    /** 状态码 */
    private int statusCode;
    
    /** 返回json字符串 */
    private String result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
