package com.yxkang.android.xmlparser.util;

import com.yxkang.android.xmlparser.annotation.Attribute;
import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;

import java.lang.reflect.Field;

/**
 * Created by fine on 2016/6/11.
 */
public class ParserUtils {

    /**
     * check the given class is a primitive type
     *
     * @param clazz the class
     * @return {@code true} if this class is a primitive type, otherwise {@code false}
     */
    public static boolean isPrimitiveType(Class<?> clazz) {
        boolean result = false;
        if (String.class.isAssignableFrom(clazz)) {
            result = true;
        } else if (Number.class.isAssignableFrom(clazz)) {
            result = true;
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * get the element annotation field count of current class and super class(if this super class is not top class)
     *
     * @param clazz the current class
     * @return the element annotation field count
     */
    public static int getElementFieldCount(Class<?> clazz) {
        int count = 0;
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {

                Element element = field.getAnnotation(Element.class);
                ElementList elementList = field.getAnnotation(ElementList.class);
                if (element != null || elementList != null) {
                    count++;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.isAssignableFrom(Object.class)) {
            fields = superClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {

                    Element element = field.getAnnotation(Element.class);
                    ElementList elementList = field.getAnnotation(ElementList.class);
                    if (element != null || elementList != null) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * get the attribute annotation field count of current class and super class(if this super class is not top class)
     *
     * @param clazz the current class
     * @return the attribute annotation field count
     */
    public static int getAttributeFieldCount(Class<?> clazz) {
        int count = 0;
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {

                Attribute attribute = field.getAnnotation(Attribute.class);
                if (attribute != null) {
                    count++;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.isAssignableFrom(Object.class)) {
            fields = superClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {

                    Attribute attribute = field.getAnnotation(Attribute.class);
                    if (attribute != null) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
