package com.yxkang.android.commonparser;

/**
 * Created by yexiaokang on 2016/6/1.
 */
public interface Converter {

    /**
     * 把字符串的值解析到对应的类实体中
     *
     * @param clazz 指定的实体类的类型
     * @param text  字符串
     * @param <T>   泛型类
     * @return 解析的实体类
     */
    <T> T convert(Class<T> clazz, String text);
}
