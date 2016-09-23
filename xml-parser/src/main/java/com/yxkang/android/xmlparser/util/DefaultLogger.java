package com.yxkang.android.xmlparser.util;

import android.util.Log;

import com.yxkang.android.xmlparser.trace.Logger;

/**
 * Created by yexiaokang on 2016/6/22.
 */
public class DefaultLogger implements Logger {

    private static final String TAG = "DefaultLogger";

    @Override
    public String getName() {
        return ROOT_LOGGER_NAME;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {

    }

    @Override
    public void trace(String format, Object arg) {

    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(String format, Object... arguments) {

    }

    @Override
    public void trace(String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(String format, Object arg) {

    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(String format, Object... arguments) {

    }

    @Override
    public void debug(String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void info(String format, Object arg) {

    }

    @Override
    public void info(String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(String format, Object... arguments) {

    }

    @Override
    public void info(String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
        if (isWarnEnabled()) {
            Log.w(TAG, msg);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        if (isWarnEnabled()) {
            Log.w(TAG, String.format(format, arg));
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (isWarnEnabled()) {
            Log.w(TAG, String.format(format, arguments));
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled()) {
            Log.w(TAG, String.format(format, arg1, arg2));
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (isWarnEnabled()) {
            Log.w(TAG, msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        if (isErrorEnabled()) {
            Log.e(TAG, msg);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled()) {
            Log.e(TAG, String.format(format, arg));
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled()) {
            Log.e(TAG, String.format(format, arg1, arg2));
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled()) {
            Log.e(TAG, String.format(format, arguments));
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        if (isErrorEnabled()) {
            Log.e(TAG, msg, t);
        }
    }
}
