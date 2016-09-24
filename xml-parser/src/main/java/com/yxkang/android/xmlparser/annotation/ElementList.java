package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface ElementList {

    /**
     * set the element list name, default is a empty string
     *
     * @return element list name
     */
    String name() default "";

    /**
     * set the item tag name, this use in a scene:
     * <pre>
     *     &lt;data&gt;
     *         &lt;tagName&gt;primitive type value&lt;/tagName&gt;
     *     &lt;/data&gt;
     *     &lt;data&gt;
     *         &lt;tagName&gt;primitive type value&lt;/tagName&gt;
     *     &lt;/data&gt;
     *     or
     *     &lt;data&gt;
     *         &lt;tagName&gt;object.toString()&lt;/tagName&gt;
     *     &lt;/data&gt;
     *     &lt;data&gt;
     *         &lt;tagName&gt;object.toString()&lt;/tagName&gt;
     *     &lt;/data&gt;
     *     ...
     * </pre>
     * the element list has only one item, but the item value is a primitive type value, or the item value is <tt>object.toString()</tt>,
     * so you should tell serializer this tagName. if the item value is an object(such as a custom class),
     * you can set the tagName in you class object annotations, and no need set this.
     *
     * @return the item tag name
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
