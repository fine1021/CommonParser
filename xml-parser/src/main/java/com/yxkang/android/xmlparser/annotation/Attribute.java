package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 * XML elements can have attributes, just like HTML.
 * <p>
 * Attributes are designed to contain data related to a specific element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Attribute {

    /**
     * set the attribute name, default is a empty string
     *
     * @return attribute name
     */
    String name() default "";

    /**
     * set the attribute order
     *
     * @return the attribute order
     */
    int order() default -1;
}
