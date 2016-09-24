package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 * An XML document contains XML Elements.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Element {

    /**
     * set the element name, default is a empty string
     *
     * @return element name
     */
    String name() default "";

    /**
     * set the element object tag name, this use in a scene:
     * <pre>
     *     &lt;data&gt;
     *         &lt;tagName&gt;object.toString()&lt;/tagName&gt;
     *     &lt;/data&gt;
     *     ...
     * </pre>
     * the element is an object and has only one item, but the item value is <tt>object.toString()</tt>,
     * so you should tell serializer this tagName. if the item value is an object(such as a custom class),
     * you can set the tagName in you class object annotations, and no need set this.
     * <br>
     * if the element is as followed:
     * <pre>
     *     &lt;data&gt;primitive type value&lt;/data&gt;
     *     ...
     * </pre>
     * in this case, this field will not be used
     *
     * @return the element object tag name
     */
    String itemName() default "";

    /**
     * This is used to acquire the namespaces that are declared on
     * the class. Any number of namespaces can be declared. None of
     * the declared namespaces will be made the elements namespace,
     * instead it will simply declare the namespaces so that the
     * reference URI and prefix will be made available to children.
     *
     * @return this returns the namespaces that are declared.
     */
    Namespace[] namespaces() default {};
}
