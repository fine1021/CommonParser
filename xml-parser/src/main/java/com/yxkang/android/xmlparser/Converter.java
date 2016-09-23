package com.yxkang.android.xmlparser;

import com.yxkang.android.xmlparser.trace.Recordable;

/**
 * Created by yexiaokang on 2016/9/22.
 * <p>
 * convert the xml string to java bean
 */

public interface Converter extends Recordable {

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
