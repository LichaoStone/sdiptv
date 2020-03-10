/*
 * 文 件 名:  BaseParse.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-9-7
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.br.ott.common.base;

import com.br.ott.common.util.XmlParseUtil;
import com.br.ott.common.util.log.LogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析类父类
 * 
 * @author cKF46827
 * @version [版本号, 2011-9-7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseParse {

    /** xml内容 */
    private String xmlContent;

    /** xml对象class */
    private Class<?> cls;
    
    /** 日志输出 */
    private LogUtil logger = new LogUtil(BaseParse.class); 

    /**
     * <默认构造函数>
     */
    public BaseParse(Class<?> cls, String xmlContent) {
        this.xmlContent = xmlContent;
        this.cls = cls;
    }

    /**
     * 解析方法
     * 
     * @return Object [解析后的对象]
     */
    public abstract Object parse();

    /**
     * 解析类属性全部都是基本类型的
     * 
     * @return Object [解析后的对象]
     */
    protected final Object parseBaseType() {

        if (null == this.xmlContent || null == this.cls) {
            logger.error("无法解析,参数为null");
            return null;
        }

        // 获得XmlPullParser对象
        XmlPullParser parser = XmlParseUtil.getXmlPullParser(xmlContent);

        // 获得xml实例
        Object instance = null;

        try {
            instance = this.cls.newInstance();
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Field field1 = XmlParseUtil.getFieldFromInstance(this.cls, parser
                                    .getAttributeName(i).toLowerCase());
                            if (null != field1) {
                                field1.setAccessible(true);
                                XmlParseUtil.setField(instance, field1, parser.getAttributeValue(i));
                            }
                        }

                        Field field2 = XmlParseUtil.getFieldFromInstance(this.cls, parser.getName());
                        if (null != field2) {
                            field2.setAccessible(true);
                            XmlParseUtil.setField(instance, field2, parser.nextText());
                        }

                        break;
                }
                // 产生下一个事件
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            logger.error("xml格式出错！" + e.toString());
            // e.printStackTrace();
        } catch (Exception e) {
            logger.error("解析出错！" + e.toString());
            // e.printStackTrace();
        }

        return instance;
    }

    /**
     * 解析类属性有基本类型、list类型(目前不支持list内嵌list) (旧解析)
     * 
     * @param className [类名]
     * @return Object [解析后的对象]
     */
    protected final Object parseListType() {

        if (null == this.xmlContent || null == this.cls) {
            logger.error("无法解析,参数为null");
            return null;
        }

        // 记录原对象的class
        Class<?> originalClass = this.cls;

        // 记录泛型对象class
        Class<?> newClass = null;

        // 获得XmlPullParser对象
        XmlPullParser parser = XmlParseUtil.getXmlPullParser(xmlContent);

        // 对象实例
        Object instance = null;

        // 原对象实例
        Object originalInstance = null;

        // 泛型对象实例
        Object newInstance = null;

        // list属性名称
        String attributeName = "@@%%$$##ambi";

        // 保存list类型
        List<Object> list = new ArrayList<Object>();

        // 是否结束list类型
        boolean isEndList = false;

        try {
            // 原对象实例
            originalInstance = this.cls.newInstance();
            // 指定当前对象实例
            instance = originalInstance;
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

                        // System.out.println("start-->" + parser.getName());
                        //
                        if ("content".equals(parser.getName())) {
                            System.out.println("");
                        }

                        // 重新返回原对象
                        if (isEndList) {
                            if (!attributeName.equals(parser.getName())) {
                                instance = originalInstance;
                                this.cls = originalClass;
                                // 为list设置值
                                XmlParseUtil.setListField(originalInstance, originalClass,
                                        attributeName, list);
                                // 重新实例化list
                                list = new ArrayList<Object>();
                                // 设置list结束标志
                                isEndList = false;
                            } else {
                                isEndList = false;
                            }
                        }

                        // 例如<result length="7">，取length
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Field field1 = XmlParseUtil.getFieldFromInstance(this.cls, parser
                                    .getAttributeName(i).toLowerCase());
                            if (null != field1) {
                                field1.setAccessible(true);
                                XmlParseUtil.setField(instance, field1, parser.getAttributeValue(i));
                            }
                        }

                        // 例如<result length="7">，取result，区分list字段，特殊处理
                        Field field2 = XmlParseUtil.getFieldFromInstance(this.cls, parser.getName());
                        if (null != field2) {
                            // 是否是list类型
                            if (XmlParseUtil.isListType(field2)) {
                                // 取得泛型类型
                                newClass = (Class<?>)((ParameterizedType)field2.getGenericType())
                                        .getActualTypeArguments()[0];
                                // 暂时改变class类型
                                this.cls = newClass;
                                // 保存list属性名称
                                attributeName = parser.getName();
                                // 实例化泛型类
                                newInstance = newClass.newInstance();
                                instance = newInstance;
                                break;
                            }
                            field2.setAccessible(true);
                            XmlParseUtil.setField(instance, field2, parser.nextText());
                        }

                        break;
                    case XmlPullParser.END_TAG:

                        // System.out.println("end-->" + parser.getName());

                        // 是否结束泛型类
                        if (attributeName.equals(parser.getName())) {
                            list.add(instance);
                            // 重新实例化泛型类
                            newInstance = newClass.newInstance();
                            instance = newInstance;
                            isEndList = true;
                        }

                        break;
                }
                // 产生下一个事件
                event = parser.next();
            }

            // 增加最后一个list
            if (list.size() > 0) {
                XmlParseUtil.setListField(originalInstance, originalClass, attributeName, list);
            }

        } catch (XmlPullParserException e) {
            logger.error("xml格式出错！" + e.toString());
            // e.printStackTrace();
        } catch (Exception e) {
            logger.error("解析出错！" + e.toString());
            // e.printStackTrace();
        }

        return originalInstance;
    }

    /**
     * 解析类属性有基本类型、list类型(目前不支持list内嵌list)
     * 
     * @param className [类名]
     * @return Object [解析后的对象]
     */
    protected final Object newParseListType() {

        if (null == this.xmlContent || null == this.cls) {
            logger.error("无法解析,参数为null");
            return null;
        }

        // 记录原对象的class
        Class<?> originalClass = this.cls;

        // 记录泛型对象class
        Class<?> newClass = null;

        // 获得XmlPullParser对象
        XmlPullParser parser = XmlParseUtil.getXmlPullParser(xmlContent);

        // 对象实例
        Object instance = null;

        // 原对象实例
        Object originalInstance = null;

        // 泛型对象实例
        Object newInstance = null;

        // list属性名称
        String attributeName = "@@%%$$##ambi";

        // list孩子属性名称
        String attributeChildrenName = "@@%%$$##ambi";

        // 保存list类型
        List<Object> list = new ArrayList<Object>();

        // 是否结束list类型
        boolean isEndList = false;

        try {
            // 原对象实例
            originalInstance = this.cls.newInstance();
            // 指定当前对象实例
            instance = originalInstance;
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

//                        System.out.println("start-->" + parser.getName());

//                        if ("tags".equals(parser.getName())) {
//                            System.out.println("");
//                        }

                        // 重新返回原对象
                        if (isEndList) {
                            if (!attributeName.equals(parser.getName())) {
                                instance = originalInstance;
                                this.cls = originalClass;
                                // 为list设置值
                                XmlParseUtil.setListField(originalInstance, originalClass,
                                        attributeName, list);
                                // 重新实例化list
                                list = new ArrayList<Object>();
                                // 设置list结束标志
                                isEndList = false;
                            } else {
                                isEndList = false;
                            }
                        }

                        // 例如<result length="7">，取length
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            Field field1 = XmlParseUtil.getFieldFromInstance(this.cls, parser
                                    .getAttributeName(i).toLowerCase());
                            if (null != field1) {
                                field1.setAccessible(true);
                                XmlParseUtil.setField(instance, field1, parser.getAttributeValue(i));
                            }
                        }

                        // 例如<result length="7">，取result，区分list字段，特殊处理
                        Field field2 = XmlParseUtil.getFieldFromInstance(this.cls, parser.getName());
                        if (null != field2) {
                            // 是否是list类型
                            if (XmlParseUtil.isListType(field2)) {
                                // 取得泛型类型
                                newClass = (Class<?>)((ParameterizedType)field2.getGenericType())
                                        .getActualTypeArguments()[0];

                                // 保存list属性名称
                                attributeName = parser.getName();
                                // 保存list子属性的名称
                                parser.next();
                                attributeChildrenName = parser.getName();
                                
                                if (!attributeName.equals(attributeChildrenName)) {
                                    // 暂时改变class类型
                                    this.cls = newClass;
                                    // 实例化泛型类
                                    newInstance = newClass.newInstance();
                                    instance = newInstance;
                                }

                                break;
                            }
                            field2.setAccessible(true);
                            XmlParseUtil.setField(instance, field2, parser.nextText());
                        }

                        break;
                    case XmlPullParser.END_TAG:

//                        System.out.println("end-->" + parser.getName());

                        // 加入子类
                        if (attributeChildrenName.equals(parser.getName())) {
                            list.add(instance);
                            // 重新实例化泛型类
                            newInstance = newClass.newInstance();
                            instance = newInstance;

                        }

                        // 结束泛型类
                        if (attributeName.equals(parser.getName())) {
                            isEndList = true;
                        }

                        break;
                }
                // 产生下一个事件
                event = parser.next();
            }

            // 增加最后一个list
            if (list.size() > 0) {
                XmlParseUtil.setListField(originalInstance, originalClass, attributeName, list);
            }

        } catch (XmlPullParserException e) {
            logger.error("xml格式出错！" + e.toString());
            // e.printStackTrace();
        } catch (Exception e) {
            logger.error("解析出错！" + e.toString());
            // e.printStackTrace();
        }

        return originalInstance;
    }
}
