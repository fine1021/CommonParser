package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface NamespaceList {

    /**
     * This is used to acquire the namespaces that are declared on
     * the class. Any number of namespaces can be declared. None of
     * the declared namespaces will be made the elements namespace,
     * instead it will simply declare the namespaces so that the
     * reference URI and prefix will be made available to children.
     *
     * @return this returns the namespaces that are declared.
     */
    Namespace[] value() default {};
}
