package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 * An XML document
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Document {

    /**
     * set the document name, default is a empty string
     *
     * @return document name
     */
    String name() default "";
}
