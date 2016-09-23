package com.yxkang.android.xmlparser.map;

import java.lang.reflect.Field;

/**
 * Created by yexiaokang on 2016/9/22.
 */

public class FieldDescriptor {

    /**
     * field name
     */
    private String name;

    /**
     * the field
     */
    private Field field;

    /**
     * marked this field is belong to super class
     */
    private boolean superField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isSuperField() {
        return superField;
    }

    public void setSuperField(boolean superField) {
        this.superField = superField;
    }
}
