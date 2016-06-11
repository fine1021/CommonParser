package com.yxkang.android.commonparser.util;

/**
 * Created by fine on 2016/6/11.
 */
public class ParserUtils {

    /**
     * 判断给定的类是否是基本的数据类型
     *
     * @param clazz 待判断类型
     * @return 判断结果，true或false
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
