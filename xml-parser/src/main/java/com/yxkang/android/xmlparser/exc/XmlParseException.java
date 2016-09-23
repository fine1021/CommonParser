package com.yxkang.android.xmlparser.exc;

/**
 * Created by fine on 2016/6/11.
 */
public class XmlParseException extends Exception {

    public XmlParseException() {
    }

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }
}
