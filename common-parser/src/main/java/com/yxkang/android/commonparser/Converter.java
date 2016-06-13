package com.yxkang.android.commonparser;

/**
 * Created by yexiaokang on 2016/6/1.
 */
public interface Converter {

    /**
     * convert the text to the class entity
     *
     * @param clazz the entity class
     * @param text  text
     * @param <T>   class type
     * @return the result entity
     */
    <T> T convert(Class<T> clazz, String text);
}
