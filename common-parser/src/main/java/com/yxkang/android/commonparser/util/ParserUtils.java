package com.yxkang.android.commonparser.util;

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
}
