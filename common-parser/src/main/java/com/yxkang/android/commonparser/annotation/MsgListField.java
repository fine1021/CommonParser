package com.yxkang.android.commonparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/6/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface MsgListField {


    /**
     * set the list map name, default is a empty string
     *
     * @return list map name
     */
    String value() default "";
}
