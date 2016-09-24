package com.yxkang.android.xmlparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexiaokang on 2016/9/22.
 * <h1>XML Namespaces - The xmlns XmlAttribute</h1>
 * When using prefixes in XML, a namespace for the prefix must be defined.
 * <p>
 * The namespace can be defined by an xmlns attribute in the start tag of an element.
 * <p>
 * The namespace declaration has the following syntax.
 * <pre>xmlns:namespace-prefix="namespaceURI"</pre>
 *
 * <h1>Default Namespaces</h1>
 * Defining a default namespace for an element saves us from using prefixes in all the child elements. It has the following syntax:
 * <pre>xmlns="namespaceURI"</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Namespace {

    /**
     * This is used to specify the unique reference URI that is used
     * to define the namespace within the document. This is typically
     * a URI as this is a well know universally unique identifier.
     * It can be anything unique, but typically should be a unique
     * URI reference. If left as the empty string then this will
     * signify that the anonymous namespace will be used.
     *
     * @return this returns the reference used by this namespace
     */
    String namespaceURI() default "";

    /**
     * This is used to specify the prefix used for the namespace. If
     * no prefix is specified then the reference becomes the default
     * namespace for the enclosing element. This means that all
     * attributes and elements that do not contain a prefix belong
     * to the namespace declared by this annotation.
     *
     * @return this returns the prefix used for this namespace
     */
    String prefix() default "";

    /**
     * This is used to mark the tag should add the prefix, default is {@code false}.
     *
     * @return {@code true} means that the tag should add the prefix, otherwise {@code false}
     */
    boolean requiredPrefix() default false;
}
