package com.yxkang.android.xmlparser.util;

import android.text.TextUtils;

import com.yxkang.android.xmlparser.annotation.Attribute;
import com.yxkang.android.xmlparser.annotation.Namespace;
import com.yxkang.android.xmlparser.annotation.NamespaceList;
import com.yxkang.android.xmlparser.entry.XmlAttribute;
import com.yxkang.android.xmlparser.entry.XmlElement;
import com.yxkang.android.xmlparser.entry.XmlNamespace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexiaokang on 2016/9/22.
 */

public class SerializerUtils {

    /**
     * get the field attached xml information, include namespace, attribute
     *
     * @param field the field
     * @return the xml element information
     */
    public static XmlElement getFieldElement(Field field) {
        XmlElement element = new XmlElement();
        List<XmlNamespace> xmlNamespaces = new ArrayList<>();
        Namespace namespace = field.getAnnotation(Namespace.class);
        if (namespace != null) {
            XmlNamespace xmlNamespace = new XmlNamespace();
            xmlNamespace.setNamespaceURI(namespace.namespaceURI());
            xmlNamespace.setPrefix(namespace.prefix());
            xmlNamespace.setRequiredPrefix(namespace.requiredPrefix());
            xmlNamespaces.add(xmlNamespace);
        }
        NamespaceList namespaceList = field.getAnnotation(NamespaceList.class);
        if (namespaceList != null) {
            Namespace[] namespaces = namespaceList.value();
            if (namespaces.length > 0) {
                for (Namespace ns : namespaces) {
                    XmlNamespace xmlNamespace = new XmlNamespace();
                    xmlNamespace.setNamespaceURI(ns.namespaceURI());
                    xmlNamespace.setPrefix(ns.prefix());
                    xmlNamespace.setRequiredPrefix(ns.requiredPrefix());
                    xmlNamespaces.add(xmlNamespace);
                }
            }
        }
        element.setNamespaces(xmlNamespaces);
        return element;
    }

    /**
     * get the bean attached xml information, include namespace, attribute
     *
     * @param bean the bean
     * @return the xml element information
     */
    public static XmlElement getBeanElement(Object bean) {
        XmlElement element = new XmlElement();
        List<XmlAttribute> xmlAttributes = new ArrayList<>();
        List<XmlNamespace> xmlNamespaces = new ArrayList<>();

        if (bean.getClass().isAnnotationPresent(Namespace.class)) {
            Namespace namespace = bean.getClass().getAnnotation(Namespace.class);
            XmlNamespace xmlNamespace = new XmlNamespace();
            xmlNamespace.setNamespaceURI(namespace.namespaceURI());
            xmlNamespace.setPrefix(namespace.prefix());
            xmlNamespace.setRequiredPrefix(namespace.requiredPrefix());
            xmlNamespaces.add(xmlNamespace);
        }
        if (bean.getClass().isAnnotationPresent(NamespaceList.class)) {
            NamespaceList namespaceList = bean.getClass().getAnnotation(NamespaceList.class);
            Namespace[] namespaces = namespaceList.value();
            if (namespaces.length > 0) {
                for (Namespace namespace : namespaces) {
                    XmlNamespace xmlNamespace = new XmlNamespace();
                    xmlNamespace.setNamespaceURI(namespace.namespaceURI());
                    xmlNamespace.setPrefix(namespace.prefix());
                    xmlNamespace.setRequiredPrefix(namespace.requiredPrefix());
                    xmlNamespaces.add(xmlNamespace);
                }
            }
        }

        // Note：namespace will not search the super class Annotation

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Attribute attr = field.getAnnotation(Attribute.class);
            if (attr != null) {
                field.setAccessible(true);
                String name = attr.name();
                // if the Attribute annotation value is empty, set the field name as the default value
                if (TextUtils.isEmpty(name)) {
                    name = field.getName();
                }
                String value = "";
                try {
                    value = field.get(bean).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                XmlAttribute xmlAttribute = new XmlAttribute();
                xmlAttribute.setName(name);
                xmlAttribute.setValue(value);
                xmlAttribute.setOrder(attr.order());
                xmlAttributes.add(xmlAttribute);
            }
        }
        // try to get super class fields, if super class is not Object.class
        Class<?> superClass = bean.getClass().getSuperclass();
        if (!superClass.isAssignableFrom(Object.class)) {
            fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                Attribute attr = field.getAnnotation(Attribute.class);
                if (attr != null) {
                    field.setAccessible(true);
                    String name = attr.name();
                    // if the Attribute annotation value is empty, set the field name as the default value
                    if (TextUtils.isEmpty(name)) {
                        name = field.getName();
                    }
                    String value = "";
                    try {
                        value = field.get(bean).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    XmlAttribute xmlAttribute = new XmlAttribute();
                    xmlAttribute.setName(name);
                    xmlAttribute.setValue(value);
                    xmlAttribute.setOrder(attr.order());
                    xmlAttributes.add(xmlAttribute);
                }
            }
        }

        element.setAttributes(xmlAttributes);
        element.setNamespaces(xmlNamespaces);
        return element;
    }
}
