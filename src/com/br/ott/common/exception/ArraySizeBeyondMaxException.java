/*
 * 文 件 名:  ArraySizeBeyondMaxException.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-31
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.exception;

/**
 * 数组大小越界
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-8-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class ArraySizeBeyondMaxException extends RuntimeException{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * <默认构造函数>
     */
    public ArraySizeBeyondMaxException() {
        super("数组大小越界");
    }
}
