package com.yxkang.android.commonparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/6/6.
 * 数据结构字段注释
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface MsgField {

    /**
     * 属性映射名称
     */
    String value() default "";
}
