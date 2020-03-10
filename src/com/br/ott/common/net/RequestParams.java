/*
 * 文 件 名:  RequestParam.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.br.ott.common.net;

import com.br.ott.common.base.IParamService;
import com.br.ott.common.base.ITypeService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 封装请求参数
 * 
 * @author cKF46827
 * @version [版本号, 2011-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class RequestParams {

   // private static LogUtil logUtil = new LogUtil(RequestParams.class);
    private Logger log = Logger.getLogger(RequestParams.class);

//    /** json包装类 */
//    private JSONObject mJsonObject = new JSONObject();
//
//    /** 序号 */
//    private int mSequence;
//
//    /** 记录错误 */
//    private List<String> mErrorList = new ArrayList<String>();

    private StringBuffer result = new StringBuffer();
    
    /** 类型 */
    private Class<?> originalClass = null;

    /**
     * <默认构造函数>
     */
    public RequestParams() {
    }

    /**
     * <默认构造函数>
     */
    public RequestParams(IParamService iParamService) {

        Map<String, Object> map = iParamService.getParams();
        Object[] keys1 = map.keySet().toArray();

        if (0 < keys1.length) {
            this.result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }

        this.result.append(this.parseMapToXmlForArray(map));
    }
    
    /**
     * <默认构造函数>
     */
    public RequestParams(IParamService iParamService, Class<?> cls) {
        
        this.originalClass = cls;
        
        Map<String, Object> map = iParamService.getParams();
        Object[] keys1 = map.keySet().toArray();

        if (0 < keys1.length) {
            this.result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }

        this.result.append(this.parseMapToXmlForList(map));
        
    }

    @SuppressWarnings("unchecked")
    private String parseMapToXmlForArray(Map<String, Object> xmlMap) {
        
        StringBuffer tempContent = new StringBuffer();

        Object[] keys1 = xmlMap.keySet().toArray();

        try {
            for (int i = 0; i < keys1.length; i++) {

                String key1 = keys1[i].toString();

                String tag = "";

                Object object = xmlMap.get(key1);

                // 如果对象为空，跳过
                if (null == object) {
                    continue;
                }

                // 解决基本数组对象
                if (key1.split("\\|").length > 1) {
                    
                    tag = key1.split("\\|")[1];
                    key1 = key1.split("\\|")[0];
                    tempContent.append("<" + key1 + " length=\"" + ((Object[])object).length + "\">");// root
                    
                } else {
                    
                    tempContent.append("<" + key1 + ">");// root
                    
                }

                // 判断是否是map类型
                if (this.isMapType(object)) {
                    tempContent.append(this.parseMapToXmlForArray((Map<String, Object>)object));
                }
                // 判断是否是数组类型
                else if (this.isArray(object)) {

                    // 判断是否是基本类型数组
                    if (this.isBaseTypeArray(object)) {

                        Object[] obj = (Object[])object;
                        for (int arrayIndex = 0; arrayIndex < obj.length; arrayIndex++) {
                            tempContent.append("<" + tag + ">" + obj[arrayIndex] + "</"
                                    + tag + ">");
                        }

                    } else {
                            
                        ITypeService[] obj = (ITypeService[])object;
                        for (int arrayIndex = 0; arrayIndex < obj.length; arrayIndex++) {
                            if (null == obj[arrayIndex]) {
                                continue;
                            }
                            tempContent.append(this.parseMapToXmlForArray(obj[arrayIndex].getParams()));
                        }

                    }

                } else {
                    String value1 = object.toString();
                    tempContent.append(value1);
                    tempContent.append("</" + key1 + ">");
                    continue;
                }

                tempContent.append("</" + key1 + ">");// root

            }
        } catch (Exception e) {
            log.error("",e);
        }
        return tempContent.toString();
    }
    
    @SuppressWarnings("unchecked")
    private String parseMapToXmlForList(Map<String, Object> xmlMap) {
        
        StringBuffer tempContent = new StringBuffer();

        Object[] keys1 = xmlMap.keySet().toArray();

        try {
            for (int i = 0; i < keys1.length; i++) {

                String key1 = keys1[i].toString();

                String tag = "";

                Object object = xmlMap.get(key1);

                // 如果对象为空，跳过
                if (null == object) {
                    continue;
                }

                // 解决基本数组对象
                if (key1.split("\\|").length > 1) {
                    
                    tag = key1.split("\\|")[1];
                    key1 = key1.split("\\|")[0];
                    tempContent.append("<" + key1 + " length=\"" + ((Object[])object).length + "\">");// root
                    
                } else {
                    
                    tempContent.append("<" + key1 + ">");// root
                    
                }

                // 判断是否是map类型
                if (this.isMapType(object)) {
                    tempContent.append(this.parseMapToXmlForList((Map<String, Object>)object));
                }
                // 判断是否是List类型
                else if (this.isListType(object)) {

                    // 判断是否是基本类型list
                    if (this.isBaseTypeList(key1)) {

                        Object[] obj = (Object[])object;
                        for (int arrayIndex = 0; arrayIndex < obj.length; arrayIndex++) {
                            tempContent.append("<" + tag + ">" + obj[arrayIndex] + "</"
                                    + tag + ">");
                        }

                    } else {
                            
                        List<ITypeService> obj = (List<ITypeService>)object;
                        for (ITypeService iTypeService : obj) {
                            if (null == iTypeService) {
                                continue;
                            }
                            tempContent.append(this.parseMapToXmlForList(iTypeService.getParams()));
                        }

                    }

                } else {
                    String value1 = object.toString();
                    tempContent.append(value1);
                    tempContent.append("</" + key1 + ">");
                    continue;
                }

                tempContent.append("</" + key1 + ">");// root

            }
        } catch (Exception e) {
            log.error("",e);
        }
        return tempContent.toString();
    }

    /**
     * 判断是否是map类型
     */
    private boolean isMapType(Object obj) {
        return obj instanceof Map;
    }

    /**
     * 判断是否是一个数组
     * 
     * @return boolean [是否是一个数组]
     */
    public boolean isArray(Object obj) {
        return obj instanceof Object[];
    }
    
    /**
     * 判断是否是一个List
     * 
     * @return boolean [是否是一个数组]
     */
    public boolean isListType(Object obj) {
        return obj instanceof List<?>;
    }

    /**
     * 是否是基本数组类型
     * 
     * @param obj [类型]
     * @return [参数说明]
     */
    public boolean isBaseTypeArray(Object obj) {
        return obj instanceof byte[] || obj instanceof short[] || obj instanceof int[]
                || obj instanceof Integer[] || obj instanceof float[] || obj instanceof Float[]
                || obj instanceof long[] || obj instanceof Long[] || obj instanceof double[]
                || obj instanceof Double[] || obj instanceof String[] || obj instanceof boolean[]
                || obj instanceof Boolean[] || obj instanceof char[] || obj instanceof Character[];
    }
    
    /**
     * 是否是基本类型List
     * 
     * @param obj [类型]
     * @return [参数说明]
     */
    public boolean isBaseTypeList(String key) {
        try {
            Field field = this.originalClass.getDeclaredField(key);
            Type type = field.getGenericType();
            ParameterizedType pt = (ParameterizedType)type;
            Type[] types = pt.getActualTypeArguments();
            Type t = types[0];
            Class<?> cls = (Class<?>)t;
            return cls == byte.class || cls == short.class || cls == int.class
                    || cls == Integer.class || cls == float.class || cls == Float.class
                    || cls == long.class || cls == Long.class || cls == double.class
                    || cls == Double.class || cls == String.class || cls == boolean.class
                    || cls == Boolean.class || cls == char.class || cls == Character.class;
        } catch (Exception e) {
            log.error("",e);
            return false;
        }
        
    }

//    /**
//     * 增加一个整型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void add(String key, int value) {
//
//        this.mSequence++;
//        try {
//            this.mJsonObject.put(key, value);
//        } catch (Exception e) {
//            this.mErrorList.add("第" + this.mSequence + "个");
//        }
//    }

//    /**
//     * 增加一个对象参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void add(String key, Object value) {
//
//        this.mSequence++;
//        try {
//            this.mJsonObject.put(key, value);
//        } catch (Exception e) {
//            this.mErrorList.add("第" + this.mSequence + "个");
//        }
//    }

//    /**
//     * 增加一个浮点型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void add(String key, double value) {
//
//        this.mSequence++;
//        try {
//            this.mJsonObject.put(key, value);
//        } catch (Exception e) {
//            this.mErrorList.add("第" + this.mSequence + "个");
//        }
//    }

//    /**
//     * 增加一个布尔型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void add(String key, boolean value) {
//
//        this.mSequence++;
//        try {
//            this.mJsonObject.put(key, value);
//        } catch (Exception e) {
//            this.mErrorList.add("第" + this.mSequence + "个");
//        }
//    }

//    /**
//     * 增加一个长整型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void add(String key, long value) {
//
//        this.mSequence++;
//        try {
//            this.mJsonObject.put(key, value);
//        } catch (Exception e) {
//            this.mErrorList.add("第" + this.mSequence + "个");
//        }
//    }

//    /**
//     * 增加一个数组型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void addArray(String key, Object... value) {
//
//        if (0 == value.length) {
//            return;
//        } else {
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < value.length; i++) {
//                jsonArray.put(value[i]);
//            }
//            this.add(key, jsonArray);
//        }
//    }
//
//    /**
//     * 增加一个数组型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void addArray(String key, String... value) {
//
//        if (0 == value.length) {
//            return;
//        } else {
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < value.length; i++) {
//                jsonArray.put(value[i]);
//            }
//            this.add(key, jsonArray);
//        }
//    }

//    /**
//     * 增加一个数组型参数
//     * 
//     * @param key [key]
//     * @param value [value]
//     */
//    public void addArray(String key, ITypeService... value) {
//
//        if (0 == value.length) {
//            return;
//        } else {
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < value.length; i++) {
//                jsonArray.put(value[i].getJsonObject());
//            }
//            this.add(key, jsonArray);
//        }
//    }

//    /**
//     * 返回一个json对象
//     */
//    public JSONObject getJsonObject() {
//
//        if (0 != this.mErrorList.size()) {
//            LogUtil.e(TAG, "封装参数出错的个数：" + this.mErrorList.size() + "个\n分别为："
//                    + this.mErrorList.toString());
//        }
//        return this.mJsonObject;
//    }
//
    public String getXmlContent() {
        return this.result.toString();
    }
}
