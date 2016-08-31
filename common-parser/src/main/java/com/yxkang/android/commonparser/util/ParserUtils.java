package com.yxkang.android.commonparser.util;

import com.yxkang.android.commonparser.annotation.MsgItemField;
import com.yxkang.android.commonparser.annotation.MsgListField;

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
     * get the annotation field count of current class and super class(if this super class is not top class)
     *
     * @param clazz the current class
     * @return the annotation field count
     */
    public static int getAnnotationFieldCount(Class<?> clazz) {
        int count = 0;
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                MsgItemField msgItemField = field.getAnnotation(MsgItemField.class);
                MsgListField msgListField = field.getAnnotation(MsgListField.class);
                if (msgItemField != null || msgListField != null) {
                    count++;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.isAssignableFrom(Object.class)) {
            fields = superClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    MsgItemField msgItemField = field.getAnnotation(MsgItemField.class);
                    MsgListField msgListField = field.getAnnotation(MsgListField.class);
                    if (msgItemField != null || msgListField != null) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
