package com.yxkang.android.xmlparser.map;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexiaokang on 2016/9/22.
 */
public class FieldDescriptorHelper {

    public static List<FieldDescriptor> getFieldDescriptors(Class<?> clazz) {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                // ignore final field
                continue;
            }
            FieldDescriptor fieldDescriptor = new FieldDescriptor();
            fieldDescriptor.setName(field.getName());
            fieldDescriptor.setField(field);
            fieldDescriptor.setSuperField(false);
            fieldDescriptors.add(fieldDescriptor);
        }
        // try to get super class fields, if super class is not Object.class
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.isAssignableFrom(Object.class)) {
            fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    // ignore final field
                    continue;
                }
                FieldDescriptor fieldDescriptor = new FieldDescriptor();
                fieldDescriptor.setName(field.getName());
                fieldDescriptor.setField(field);
                fieldDescriptor.setSuperField(true);
                fieldDescriptors.add(fieldDescriptor);
            }
        }
        return fieldDescriptors;
    }

    public static List<FieldDescriptor> getFieldDescriptors(Object bean) {
        return getFieldDescriptors(bean.getClass());
    }
}
