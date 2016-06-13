package com.yxkang.android.commonparser.map;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yexiaokang on 2016/6/6.
 */
public class MapFieldMethod {

    /**
     * reflect field
     */
    private Field mField;

    /**
     * reflect method
     */
    private Method mMethod;

    public Field getField() {
        return mField;
    }

    public void setField(Field field) {
        mField = field;
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }
}
