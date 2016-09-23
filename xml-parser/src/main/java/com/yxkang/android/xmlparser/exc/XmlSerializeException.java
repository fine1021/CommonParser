package com.yxkang.android.xmlparser.exc;

/**
 * Created by yexiaokang on 2016/9/23.
 */

public class XmlSerializeException extends Exception {

    public XmlSerializeException(String message) {
        super(message);
    }

    public XmlSerializeException() {
        super();
    }

    public XmlSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlSerializeException(Throwable cause) {
        super(cause);
    }
}
