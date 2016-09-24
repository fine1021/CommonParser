package com.yxkang.android.xmlparser.common;

import com.yxkang.android.xmlparser.entry.XmlElement;
import com.yxkang.android.xmlparser.exc.XmlSerializeException;

import java.util.List;

/**
 * Created by yexiaokang on 2016/9/22.
 */

public interface Writer {

    /**
     * write the primitive type value element
     *
     * @param xmlElement   the element information, elementName, elementValue, itemName, attributes and namespaces
     * @param elementValue the element value
     */
    void writePrimitiveElement(XmlElement xmlElement, String elementValue);

    /**
     * write the bean element
     *
     * @param xmlElement the element information, elementName, elementValue, itemName, attributes and namespaces
     * @param bean       the java bean
     */
    void writeElement(XmlElement xmlElement, Object bean);

    /**
     * write the element list, each item could be a bean or primitive data
     *
     * @param xmlElementList the element list information, elementName, elementValue, itemName, attributes and namespaces
     * @param xmlElement     the element information, elementName, elementValue, itemName, attributes and namespaces
     * @param list           the list with all beans
     * @param subType        the bean class type
     * @throws XmlSerializeException throw when item element is primitive type value, but itemName is empty
     */
    void writeElementList(XmlElement xmlElementList, XmlElement xmlElement, List<?> list, Class<?> subType) throws XmlSerializeException;
}
