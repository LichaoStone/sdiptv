/*
 * 文 件 名:  BaseXml.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zKF45495
 * 修改时间:  2011-9-6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.base;

import java.io.Serializable;

/**
 * xml实体父类
 * 
 * @author  zKF45495
 * @version  [版本号, 2011-9-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseXml implements Serializable{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 3247996904465135471L;

    /** 返回码 */
    private String resultcode = "";
    
    /** 返回描述 */
    private String desc = "";

    public final String getResultcode() {
        return resultcode;
    }

    public final void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public final String getDesc() {
        return desc;
    }

    public final void setDesc(String desc) {
        this.desc = desc;
    }

}
