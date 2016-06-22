package com.yxkang.android.commonparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/6/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface MsgItemField {

    /**
     * set the field map name, default is a empty string
     *
     * @return field map name
     */
    String value() default "";
}
