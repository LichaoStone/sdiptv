/*
 * 文 件 名:  ITypeService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-31
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.base;

import java.io.Serializable;
import java.util.Map;

/**
 * 复合类型必须实现接口
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-8-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ITypeService extends Serializable{

    /**
     * 获取参数
     */
    Map<String, Object> getParams();
}
