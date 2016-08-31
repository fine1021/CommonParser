package com.yxkang.android.commonparser.sample;

import android.util.Log;

import com.yxkang.android.commonparser.trace.Logger;

/**
 * Created by yexiaokang on 2016/6/22.
 */
public class XmlParserLogger implements Logger {

    private static final String TAG = "XmlParserLogger";

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        Log.v(TAG, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        Log.v(TAG, String.format(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        Log.v(TAG, String.format(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        Log.v(TAG, String.format(format, arguments));
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
        Log.d(TAG, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        Log.d(TAG, String.format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        Log.d(TAG, String.format(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        Log.d(TAG, String.format(format, arguments));
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
        Log.i(TAG, msg);
    }

    @Override
    public void info(String format, Object arg) {
        Log.i(TAG, String.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        Log.i(TAG, String.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        Log.i(TAG, String.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        Log.w(TAG, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        Log.w(TAG, String.format(format, arg));
    }

    @Override
    public void warn(String format, Object... arguments) {
        Log.w(TAG, String.format(format, arguments));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        Log.w(TAG, String.format(format, arg1, arg2));
    }

    @Override
    public void warn(String msg, Throwable t) {
        Log.w(TAG, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void error(String format, Object arg) {
        Log.e(TAG, String.format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        Log.e(TAG, String.format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        Log.e(TAG, String.format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        Log.e(TAG, msg, t);
    }
}
