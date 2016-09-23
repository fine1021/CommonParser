package com.yxkang.android.xmlparser.common;

import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;
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
     * @param elementName  the element name
     * @param elementValue the element value
     * @param xmlElement   the element information
     */
    void writePrimitiveElement(String elementName, String elementValue, XmlElement xmlElement);

    /**
     * write the bean element
     *
     * @param elementName the element name
     * @param itemName    the element item name, maybe {@code null}, {@link Element#itemName()}
     * @param bean        the java bean
     */
    void writeElement(String elementName, String itemName, Object bean);

    /**
     * write the element list, each item could be a bean or primitive data
     *
     * @param elementListName the element list name
     * @param elementName     the element name, maybe {@code null}
     * @param itemName        the element item name, maybe {@code null}, {@link ElementList#itemName()}
     * @param list            the list with all beans
     * @param subType         the bean class type
     * @throws XmlSerializeException throw when item element is primitive type value, but itemName is empty
     */
    void writeElementList(String elementListName, String elementName, String itemName, List<?> list, Class<?> subType) throws XmlSerializeException;
}
