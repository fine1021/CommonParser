package com.yxkang.android.xmlparser.converter;

import android.text.TextUtils;

import com.yxkang.android.xmlparser.annotation.Attribute;
import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;
import com.yxkang.android.xmlparser.common.Reader;
import com.yxkang.android.xmlparser.map.FieldDescriptor;
import com.yxkang.android.xmlparser.map.FieldDescriptorHelper;
import com.yxkang.android.xmlparser.trace.Logger;
import com.yxkang.android.xmlparser.util.ParserUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yexiaokang on 2016/6/7.
 * <p>converter utils</p>
 */
final class Converters {

    /**
     * convert the content of the reader to the clazz entry
     *
     * @param clazz  clazz
     * @param reader content reader
     * @param logger logger to trace
     * @param <T>    data model
     * @return the given clazz entry
     */
    static <T> T convert(Class<T> clazz, Reader reader, Logger logger) {
        T rsp = null;

        try {
            String className = clazz.getName();
            logger.info("convert: class = %s", className);
            int mod = clazz.getModifiers();
            logger.info("convert: class modifiers = %d", mod);
            if (clazz.getName().contains("$") && !Modifier.isStatic(mod)) {
                String outerClass = className.substring(0, className.lastIndexOf("$"));
                logger.info("convert: outerClass = %s", outerClass);
                Class<?> cls = Class.forName(outerClass);
                Object o = cls.newInstance();
                // get private or public constructor
                Constructor<T> constructor = clazz.getDeclaredConstructor(cls);
                constructor.setAccessible(true);
                rsp = constructor.newInstance(o);
            } else {
                rsp = clazz.newInstance();
            }

            List<FieldDescriptor> fieldDescriptors = FieldDescriptorHelper.getFieldDescriptors(clazz);
            logger.info("convert: fieldDescriptors size = %d", fieldDescriptors.size());

            for (FieldDescriptor fieldDescriptor : fieldDescriptors) {

                String elementName = null;
                String elementListName = null;
                String attributeName = null;

                Field field = fieldDescriptor.getField();

                Annotation[] annotations = field.getAnnotations();

                for (Annotation annotation : annotations) {

                    if (Element.class.isAssignableFrom(annotation.annotationType())) {

                        Element element = (Element) annotation;
                        elementName = element.name();
                        // if the Element annotation value is empty, set the field name as the default value
                        if (TextUtils.isEmpty(elementName)) {
                            elementName = fieldDescriptor.getName();
                        }
                    } else if (ElementList.class.isAssignableFrom(annotation.annotationType())) {

                        ElementList elementList = (ElementList) annotation;
                        elementListName = elementList.name();
                        // if the ElementList annotation value is empty, set the field name as the default value
                        if (TextUtils.isEmpty(elementListName)) {
                            elementListName = fieldDescriptor.getName();
                        }
                    } else if (Attribute.class.isAssignableFrom(annotation.annotationType())) {

                        Attribute attribute = (Attribute) annotation;
                        attributeName = attribute.name();
                        // if the XmlAttribute annotation value is empty, set the field name as the default value
                        if (TextUtils.isEmpty(attributeName)) {
                            attributeName = fieldDescriptor.getName();
                        }
                    }
                }

                if (TextUtils.isEmpty(elementName) && TextUtils.isEmpty(elementListName) && TextUtils.isEmpty(attributeName)) {
                    logger.trace("convert: this field does't have xml annotations, fieldName = %s", fieldDescriptor.getName());
                    continue;
                }

                Class<?> fieldType = field.getType();
                // set field accessible
                field.setAccessible(true);
                // the object value, maybe primitive type or custom class or list
                Object value = null;

                if (!TextUtils.isEmpty(attributeName)) {
                    if (!reader.containAttribute(attributeName)) {
                        logger.warn("convert: can't find this attributeName, name = %s", attributeName);
                        continue;
                    }
                    if (!TextUtils.isEmpty(elementName) || !TextUtils.isEmpty(elementListName)) {
                        logger.warn("convert: attribute name is not empty, ignore element or elementList, attributeName = %s", attributeName);
                    }
                    // attribute value just supports primitive type
                    value = reader.getAttribute(attributeName);
                } else {
                    // if elementListName is empty, we can sure that elementName must not be empty
                    if (TextUtils.isEmpty(elementListName)) {
                        if (!reader.containElement(elementName)) {
                            logger.warn("convert: can't find this elementName, name = %s", elementName);
                            continue;
                        }
                        if (ParserUtils.isPrimitiveType(fieldType) || fieldType.isPrimitive()) {
                            value = reader.getPrimitiveElement(elementName);
                        } else {
                            logger.info("convert: assume meet a custom class, fieldType = %s", fieldType.getName());
                        }
                    } else {
                        if (!reader.containElement(elementListName)) {
                            logger.warn("convert: can't find this elementListName, name = %s", elementListName);
                            continue;
                        }
                    }
                }

                /*
                 * there are two situations have not been handled. this element is an object or this element is a list
                 *
                 */

                if (String.class.isAssignableFrom(fieldType)) {
                    if (value != null) {
                        field.set(rsp, value.toString());
                    }
                } else if (Number.class.isAssignableFrom(fieldType)) {
                    if (value != null) {
                        if (Byte.class.isAssignableFrom(fieldType)) {
                            field.setByte(rsp, Byte.valueOf(value.toString()));
                        } else if (Short.class.isAssignableFrom(fieldType)) {
                            field.setShort(rsp, Short.valueOf(value.toString()));
                        } else if (Integer.class.isAssignableFrom(fieldType)) {
                            field.setInt(rsp, Integer.valueOf(value.toString()));
                        } else if (Long.class.isAssignableFrom(fieldType)) {
                            field.setLong(rsp, Long.valueOf(value.toString()));
                        } else if (Float.class.isAssignableFrom(fieldType)) {
                            field.setFloat(rsp, Float.valueOf(value.toString()));
                        } else if (Double.class.isAssignableFrom(fieldType)) {
                            field.setDouble(rsp, Double.valueOf(value.toString()));
                        }
                    }
                } else if (Boolean.class.isAssignableFrom(fieldType)) {
                    if (value != null) {
                        field.setBoolean(rsp, Boolean.valueOf(value.toString()));
                    }
                } else if (List.class.isAssignableFrom(fieldType)) {
                    Type genericType = field.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType paramType = (ParameterizedType) genericType;
                        Type[] genericTypes = paramType.getActualTypeArguments();
                        if (genericTypes != null && genericTypes.length > 0) {
                            if (genericTypes[0] instanceof Class<?>) {
                                Class<?> subType = (Class<?>) genericTypes[0];
                                logger.info("convert: subType = %s", subType.getName());
                                List<?> objects = reader.getElementList(elementListName, elementName, subType);
                                if (objects != null) {
                                    field.set(rsp, objects);
                                }
                            }
                        }
                    }
                } else {
                    if (fieldType.isPrimitive()) {
                        if (value != null) {
                            if (byte.class.isAssignableFrom(fieldType)) {
                                field.setByte(rsp, Byte.valueOf(value.toString()));
                            } else if (short.class.isAssignableFrom(fieldType)) {
                                field.setShort(rsp, Short.valueOf(value.toString()));
                            } else if (int.class.isAssignableFrom(fieldType)) {
                                field.setInt(rsp, Integer.valueOf(value.toString()));
                            } else if (long.class.isAssignableFrom(fieldType)) {
                                field.setLong(rsp, Long.valueOf(value.toString()));
                            } else if (float.class.isAssignableFrom(fieldType)) {
                                field.setFloat(rsp, Float.valueOf(value.toString()));
                            } else if (double.class.isAssignableFrom(fieldType)) {
                                field.setDouble(rsp, Double.valueOf(value.toString()));
                            } else if (boolean.class.isAssignableFrom(fieldType)) {
                                field.setBoolean(rsp, Boolean.valueOf(value.toString()));
                            } else if (char.class.isAssignableFrom(fieldType)) {
                                field.set(rsp, value.toString().charAt(0));
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(attributeName)) {
                            // this branch is to deal with attribute
                            logger.warn("convert: not supported attribute value type, fieldType = %s", fieldType.getName());
                        } else {
                            if (field.getGenericType() instanceof ParameterizedType) {
                                logger.warn("convert: not supported parameterized type, fieldType = %s", fieldType.getName());
                            } else {
                                // this branch is to deal with element object
                                Object obj = reader.getElement(elementName, fieldType);
                                if (obj != null) {
                                    field.set(rsp, obj);
                                }
                            }
                        }
                    }
                }
            }
            fieldDescriptors.clear();
            logger.info("convert: success, class = %s", className);
        } catch (Exception e) {
            logger.error("convert: Exception", e);
        }
        return rsp;
    }
}
