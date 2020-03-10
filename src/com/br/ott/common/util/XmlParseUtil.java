/*
 * 文 件 名:  XmlParseUtil2.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-11-9
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.br.ott.common.util;

import com.br.ott.common.util.log.LogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;

/**
 * xml解析工具类
 * 
 * @author  cKF46827
 * @version  [版本号, 2011-11-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class XmlParseUtil {

    private static LogUtil logger = new LogUtil(XmlParseUtil.class);

    /**
     * <默认构造函数>
     */
    private XmlParseUtil() {
    }

    /**
     * <从一个对象中取出一个字段>
     * 
     * @param currentInstance 对象
     * @param name 字段名
     * @return Field 返回字段
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Field getFieldFromInstance(Class<?> cls, String name) {
        Field field = null;
        try {
            field = cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            field = getFieldFromInstanceForSuper(cls.getSuperclass(), name);
        } catch (NullPointerException e) {
            logger.warn(" Can't instance ->" + name);
        }
        return field;
    }

    /**
     * <从一个对象的父类中取出一个字段>
     * 
     * @param currentInstance 对象
     * @param name 字段名
     * @return Field 返回字段
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Field getFieldFromInstanceForSuper(Class<?> cls, String name) {
        Field field = null;
        try {
            field = cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            logger.warn(" No such field(Start tag) ->" + name);
        } catch (NullPointerException e) {
            logger.error("Super can't instance ->" + name);
        }
        return field;
    }

    /**
     * <给一个对象的字段设值>
     * 
     * @param instance
     * @param field
     * @param nextText [参数说明]
     * @return void [返回类型说明]
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void setField(Object instance, Field field, String text)
            throws IllegalAccessException {
        if (field.getType() == Integer.class || field.getType() == int.class) {
            field.set(instance, Integer.parseInt(text));
        } else if (field.getType() == Float.class || field.getType() == float.class) {
            field.set(instance, Float.parseFloat(text));
        } else if (field.getType() == Double.class || field.getType() == double.class) {
            field.set(instance, Double.parseDouble(text));
        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            field.set(instance, Boolean.parseBoolean(text));
        } else if (field.getType() == Byte.class || field.getType() == byte.class) {
            field.set(instance, Byte.parseByte(text));
        } else if (field.getType() == Long.class || field.getType() == long.class) {
            field.set(instance, Long.parseLong(text));
        } else if (field.getType() == String.class) {
            field.set(instance, text);
        } else {
            logger.error("Error type: " + field.getName());
        }
    }

    /**
     * <给一个对象的LIST字段设值>
     * 
     * @param instance
     * @param field
     * @param nextText [参数说明]
     * @return void [返回类型说明]
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void setListField(Object instance, Class<?> cls, String name, List<Object> list) {
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, list);
        } catch (Exception e) {
            logger.error("设置list字段出错！");
            e.printStackTrace();
        }
    }

    /**
     * <返回一个XmlPullParser>
     * 
     * @param intput
     * @return XmlPullParser [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static XmlPullParser getXmlPullParser(String intput) {
        XmlPullParserFactory pullParserFactory = null;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            // 获取XmlPullParser的实例
            XmlPullParser xmlPullParser = pullParserFactory.newPullParser();
            // 把String变成输入流
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(
                    intput.getBytes()));
            Reader reader = new BufferedReader(isr);
            // 设置输入流 xml文件
            xmlPullParser.setInput(reader);
            return xmlPullParser;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 判断属性是否是list
     * 
     * @param field [属性]
     */
    public static boolean isListType(Field field) {
        return field.getType() == List.class;
    }
}
