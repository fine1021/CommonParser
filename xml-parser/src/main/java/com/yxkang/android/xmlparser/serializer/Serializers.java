package com.yxkang.android.xmlparser.serializer;

import android.text.TextUtils;

import com.yxkang.android.xmlparser.Serializer;
import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;
import com.yxkang.android.xmlparser.common.Writer;
import com.yxkang.android.xmlparser.entry.XmlAttribute;
import com.yxkang.android.xmlparser.entry.XmlElement;
import com.yxkang.android.xmlparser.entry.XmlNamespace;
import com.yxkang.android.xmlparser.map.FieldDescriptor;
import com.yxkang.android.xmlparser.map.FieldDescriptorHelper;
import com.yxkang.android.xmlparser.trace.Logger;
import com.yxkang.android.xmlparser.util.ParserUtils;
import com.yxkang.android.xmlparser.util.SerializerUtils;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yexiaokang on 2016/9/22.
 */

final class Serializers {

    private static final String XMLNS = "xmlns";

    static void addTag(XmlSerializer xmlSerializer, Serializer serializer, String name, String value) {
        try {
            xmlSerializer.startTag(null, name);
            if (!TextUtils.isEmpty(value)) {
                xmlSerializer.text(value);
            } else {
                xmlSerializer.text("");
            }
            xmlSerializer.endTag(null, name);
            if (!TextUtils.isEmpty(serializer.getCRLF())) {
                xmlSerializer.text(serializer.getCRLF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addTag(XmlSerializer xmlSerializer, Serializer serializer, String name, String value, XmlElement element) {
        try {
            List<XmlAttribute> attributes = element.getAttributes();
            List<XmlNamespace> namespaces = element.getNamespaces();
            String tagName = getTagName(name, element);
            xmlSerializer.startTag(null, tagName);
            if (attributes != null && !attributes.isEmpty()) {
                for (XmlAttribute attribute : attributes) {
                    xmlSerializer.attribute(null, attribute.getName(), attribute.getValue());
                }
            }
            if (namespaces != null && !namespaces.isEmpty()) {
                for (XmlNamespace namespace : namespaces) {
                    if (!TextUtils.isEmpty(namespace.getNamespaceURI())) {
                        String prefix = namespace.getPrefix();
                        String attributeName = TextUtils.isEmpty(prefix) ? XMLNS : XMLNS + ":" + prefix;
                        xmlSerializer.attribute(null, attributeName, namespace.getNamespaceURI());
                    }
                }
            }
            if (!TextUtils.isEmpty(value)) {
                xmlSerializer.text(value);
            } else {
                xmlSerializer.text("");
            }
            xmlSerializer.endTag(null, tagName);
            if (!TextUtils.isEmpty(serializer.getCRLF())) {
                xmlSerializer.text(serializer.getCRLF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void startTag(XmlSerializer xmlSerializer, Serializer serializer, String name) {
        try {
            xmlSerializer.startTag(null, name);
            if (!TextUtils.isEmpty(serializer.getCRLF())) {
                xmlSerializer.text(serializer.getCRLF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void startTag(XmlSerializer xmlSerializer, Serializer serializer, String name, XmlElement element) {
        try {
            List<XmlAttribute> attributes = element.getAttributes();
            List<XmlNamespace> namespaces = element.getNamespaces();
            xmlSerializer.startTag(null, getTagName(name, element));
            if (attributes != null && !attributes.isEmpty()) {
                for (XmlAttribute attribute : attributes) {
                    xmlSerializer.attribute(null, attribute.getName(), attribute.getValue());
                }
            }
            if (namespaces != null && !namespaces.isEmpty()) {
                for (XmlNamespace namespace : namespaces) {
                    if (!TextUtils.isEmpty(namespace.getNamespaceURI())) {
                        String prefix = namespace.getPrefix();
                        String attributeName = TextUtils.isEmpty(prefix) ? XMLNS : XMLNS + ":" + prefix;
                        xmlSerializer.attribute(null, attributeName, namespace.getNamespaceURI());
                    }
                }
            }
            if (!TextUtils.isEmpty(serializer.getCRLF())) {
                xmlSerializer.text(serializer.getCRLF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void endTag(XmlSerializer xmlSerializer, Serializer serializer, String name, XmlElement element) {
        endTag(xmlSerializer, serializer, getTagName(name, element));
    }

    static void endTag(XmlSerializer xmlSerializer, Serializer serializer, String name) {
        try {
            xmlSerializer.endTag(null, name);
            if (!TextUtils.isEmpty(serializer.getCRLF())) {
                xmlSerializer.text(serializer.getCRLF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void write(Object bean, Writer writer, Logger logger) {

        try {

            List<FieldDescriptor> fieldDescriptors = FieldDescriptorHelper.getFieldDescriptors(bean);
            logger.info("write: fieldDescriptors size = %d", fieldDescriptors.size());

            for (FieldDescriptor fieldDescriptor : fieldDescriptors) {

                String elementName = null;
                String itemName = null;
                String elementListName = null;
                String itemNameList = null;

                Field field = fieldDescriptor.getField();

                Annotation[] annotations = field.getAnnotations();

                for (Annotation annotation : annotations) {

                    if (Element.class.isAssignableFrom(annotation.annotationType())) {

                        Element element = (Element) annotation;
                        elementName = element.name();
                        itemName = element.itemName();
                        // if the Element annotation value is empty, set the field name as the default value
                        if (TextUtils.isEmpty(elementName)) {
                            elementName = fieldDescriptor.getName();
                        }
                    } else if (ElementList.class.isAssignableFrom(annotation.annotationType())) {

                        ElementList elementList = (ElementList) annotation;
                        elementListName = elementList.name();
                        itemNameList = elementList.itemName();
                        // if the ElementList annotation value is empty, set the field name as the default value
                        if (TextUtils.isEmpty(elementListName)) {
                            elementListName = fieldDescriptor.getName();
                        }
                    }
                }

                if (TextUtils.isEmpty(elementName) && TextUtils.isEmpty(elementListName)) {
                    logger.trace("write: this field does't have Element or ElementList annotation, fieldName = %s", fieldDescriptor.getName());
                    continue;
                }

                Class<?> fieldType = field.getType();
                // set field accessible
                field.setAccessible(true);

                // if elementListName is empty, we can sure that elementName must not be empty
                if (TextUtils.isEmpty(elementListName)) {
                    if (ParserUtils.isPrimitiveType(fieldType) || fieldType.isPrimitive()) {
                        Object value = field.get(bean);
                        // if the field is annotated with Element, try to find Namespace or NamespaceList annotation
                        XmlElement xmlElement = SerializerUtils.getFieldElement(field);
                        if (value == null) {
                            writer.writePrimitiveElement(elementName, null, xmlElement);
                        } else {
                            writer.writePrimitiveElement(elementName, value.toString(), xmlElement);
                        }
                    } else {
                        logger.info("write: assume meet a custom class, fieldType = %s", fieldType.getName());
                        Object value = field.get(bean);
                        writer.writeElement(elementName, itemName, value);
                    }
                } else {
                    if (List.class.isAssignableFrom(fieldType)) {
                        Type genericType = field.getGenericType();
                        if (genericType instanceof ParameterizedType) {
                            ParameterizedType paramType = (ParameterizedType) genericType;
                            Type[] genericTypes = paramType.getActualTypeArguments();
                            if (genericTypes != null && genericTypes.length > 0) {
                                if (genericTypes[0] instanceof Class<?>) {
                                    Class<?> subType = (Class<?>) genericTypes[0];
                                    logger.info("write: subType = %s", subType.getName());
                                    List<?> list = (List<?>) field.get(bean);
                                    writer.writeElementList(elementListName, elementName, itemNameList, list, subType);
                                }
                            }
                        }
                    } else {
                        logger.warn("write: not supported type, fieldType = %s", fieldType.getName());
                    }
                }
            }
            fieldDescriptors.clear();
            logger.info("write: success, class = %s", bean.getClass().getName());
        } catch (Exception e) {
            logger.error("write: Exception", e);
        }
    }

    private static String getTagName(String name, XmlElement element) {
        String tagName = name;
        List<XmlNamespace> namespaces = element.getNamespaces();
        if (namespaces != null && namespaces.size() == 1) {
            XmlNamespace xmlNamespace = namespaces.get(0);
            if (xmlNamespace.isRequiredPrefix()) {
                String prefix = xmlNamespace.getPrefix();
                tagName = TextUtils.isEmpty(prefix) ? name : prefix + ":" + name;
            }
        }
        return tagName;
    }
}
