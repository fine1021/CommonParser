package com.yxkang.android.commonparser.converter;

import android.text.TextUtils;

import com.yxkang.android.commonparser.Reader;
import com.yxkang.android.commonparser.annotation.MsgItemField;
import com.yxkang.android.commonparser.annotation.MsgListField;
import com.yxkang.android.commonparser.map.MapFieldMethod;
import com.yxkang.android.commonparser.map.PropertyDescriptor;
import com.yxkang.android.commonparser.map.PropertyDescriptorHelper;
import com.yxkang.android.commonparser.trace.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexiaokang on 2016/6/7.
 * <p>converter utils</p>
 */
public final class Converters {

    /**
     * convert the content of the reader to the clazz entry
     *
     * @param clazz  clazz
     * @param reader content reader
     * @param logger logger to trace
     * @param <T>    data model
     * @return the given clazz entry
     */
    public static <T> T convert(Class<T> clazz, Reader reader, Logger logger) {
        T rsp = null;

        try {
            if (clazz.getName().contains("$")) {
                String className = clazz.getName();
                String outerClass = className.substring(0, className.lastIndexOf("$"));
                logger.info("convert: outerClass = %s", outerClass);
                Class<?> cls = Class.forName(outerClass);
                Object o = cls.newInstance();
                Constructor<T> constructor = clazz.getDeclaredConstructor(cls);
                constructor.setAccessible(true);
                rsp = constructor.newInstance(o);
            } else {
                rsp = clazz.newInstance();
            }

            List<PropertyDescriptor> descriptors = PropertyDescriptorHelper.getPropertyDescriptors(clazz);
            List<MapFieldMethod> fieldMethods = new ArrayList<>();

            logger.info("convert: descriptors size = %d", descriptors.size());

            for (PropertyDescriptor descriptor : descriptors) {
                Method writeMethod = descriptor.getWriteMethod();
                // ignore read-only method
                if (writeMethod == null) {
                    continue;
                }

                String itemName = descriptor.getName();
                Field field = clazz.getDeclaredField(itemName);      // get the declared field, include private field

                MapFieldMethod fieldMethod = new MapFieldMethod();
                fieldMethod.setField(field);
                fieldMethod.setMethod(writeMethod);

                fieldMethods.add(fieldMethod);
            }

            logger.info("convert: fieldMethods size = %d", fieldMethods.size());

            for (MapFieldMethod fieldMethod : fieldMethods) {

                String itemName = null;
                String listName = null;

                Field field = fieldMethod.getField();
                Method method = fieldMethod.getMethod();

                MsgItemField msgItemField = field.getAnnotation(MsgItemField.class);
                if (msgItemField != null) {
                    itemName = msgItemField.value();

                    // if the MsgItemField annotation value is empty, set the field name as the default value
                    if (TextUtils.isEmpty(itemName)) {
                        itemName = field.getName();
                    }
                }

                MsgListField msgListField = field.getAnnotation(MsgListField.class);
                if (msgListField != null) {
                    listName = msgListField.value();

                    // if the MsgListField annotation value is empty, set the field name as the default value
                    if (TextUtils.isEmpty(listName)) {
                        listName = field.getName();
                    }
                }

                // filter the unannotated field and excluded field
                if (TextUtils.isEmpty(itemName) || !reader.contain(itemName)) {
                    if (TextUtils.isEmpty(listName) || !reader.contain(listName)) {
                        continue;
                    }
                }

                Class<?> typeClass = field.getType();

                if (String.class.isAssignableFrom(typeClass)) {
                    Object value = reader.getPrimitiveObject(itemName);
                    if (value instanceof String) {
                        method.invoke(rsp, value.toString());
                    } else {
                        if (value != null) {
                            method.invoke(rsp, value.toString());
                        } else {
                            method.invoke(rsp, "");
                        }
                    }
                } else if (Number.class.isAssignableFrom(typeClass)) {
                    Object value = reader.getPrimitiveObject(itemName);
                    if (value != null) {
                        if (Byte.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Byte.valueOf(value.toString()));
                        } else if (Short.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Short.valueOf(value.toString()));
                        } else if (Integer.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Integer.valueOf(value.toString()));
                        } else if (Long.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Long.valueOf(value.toString()));
                        } else if (Float.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Float.valueOf(value.toString()));
                        } else if (Double.class.isAssignableFrom(typeClass)) {
                            method.invoke(rsp, Double.valueOf(value.toString()));
                        }
                    }
                } else if (Boolean.class.isAssignableFrom(typeClass)) {
                    Object value = reader.getPrimitiveObject(itemName);
                    if (value instanceof Boolean) {
                        method.invoke(rsp, Boolean.valueOf(value.toString()));
                    } else {
                        if (value != null) {
                            method.invoke(rsp, Boolean.valueOf(value.toString()));
                        }
                    }
                } else if (List.class.isAssignableFrom(typeClass)) {
                    Type fieldType = field.getGenericType();
                    if (fieldType instanceof ParameterizedType) {
                        ParameterizedType paramType = (ParameterizedType) fieldType;
                        Type[] genericTypes = paramType.getActualTypeArguments();
                        if (genericTypes != null && genericTypes.length > 0) {
                            if (genericTypes[0] instanceof Class<?>) {
                                Class<?> subType = (Class<?>) genericTypes[0];
                                logger.info("convert: subType = %s", subType.getName());
                                List<?> objects = reader.getListObjects(listName, itemName, subType);
                                if (objects != null) {
                                    method.invoke(rsp, objects);
                                }
                            }
                        }
                    }
                } else {
                    if (typeClass.isPrimitive()) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value != null) {
                            if (byte.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Byte.valueOf(value.toString()));
                            } else if (short.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Short.valueOf(value.toString()));
                            } else if (int.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Integer.valueOf(value.toString()));
                            } else if (long.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Long.valueOf(value.toString()));
                            } else if (float.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Float.valueOf(value.toString()));
                            } else if (double.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Double.valueOf(value.toString()));
                            } else if (boolean.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, Boolean.valueOf(value.toString()));
                            } else if (char.class.isAssignableFrom(typeClass)) {
                                method.invoke(rsp, value.toString().charAt(0));
                            } else {
                                method.invoke(rsp, value);
                            }
                        }
                    } else {
                        Object obj = reader.getObject(itemName, typeClass);
                        if (obj != null) {
                            method.invoke(rsp, obj);
                        }
                    }
                }

            }
            logger.info("convert: success");
        } catch (Exception e) {
            logger.error("convert: Exception", e);
        }
        return rsp;
    }
}
