/*
 * 文 件 名:  DataEngineInterface.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  wKF45592
 * 修改时间:  2011-6-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.br.ott.common.net;

import com.br.ott.common.exception.NotesException;



/**
 * 数据请求接口
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IDataEngine {
    
    /**
     * 开始同步请求数据
     */
    String request() throws NotesException;

    /**
     * 取消数据请求
     */
    void cancelRequest();

    /**
     * 判断是否正在加载数据
     */
    boolean isLoading();
}
