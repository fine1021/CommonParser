package com.yxkang.android.cp.dom4j.util;

import org.dom4j.Element;

import java.util.List;

/**
 * Created by yexiaokang on 2016/7/6.
 */
public class Dom4jUtils {

    /**
     * Gets the immediately child elements list from the parent element.
     *
     * @param parent  the parent element in the element tree
     * @param tagName the specified tag name
     * @return the NOT NULL immediately child elements list
     */
    public static List<Element> getChildElements(Element parent, String tagName) {
        return parent.elements(tagName);
    }

    /**
     * Gets the immediately child element from the parent element.
     *
     * @param parent  the parent element in the element tree
     * @param tagName the specified tag name
     * @return immediately child element of parent element, NULL otherwise
     */
    public static Element getChildElement(Element parent, String tagName) {
        List<Element> children = getChildElements(parent, tagName);

        if (children.isEmpty()) {
            return null;
        } else {
            return children.get(0);
        }
    }

    /**
     * Gets the value of the child element by tag name under the given parent
     * element. If there is more than one child element, return the value of the
     * first one.
     *
     * @param parent  the parent element
     * @param tagName the tag name of the child element
     * @return value of the first child element, NULL if tag not exists
     */
    public static String getElementValue(Element parent, String tagName) {
        Element element = getChildElement(parent, tagName);
        if (element != null) {
            return element.getText();
        }
        return null;
    }

    /**
     * Gets the text value of current element.
     *
     * @param element the current element
     * @return text value of the element, NULL if element not exists
     */
    public static String getElementValue(Element element) {
        if (element != null) {
            return element.getText();
        }
        return null;
    }

    /**
     * Get the count of the child element under the given parent element.
     *
     * @param parent the parent element
     * @return the child element count, 0 if not exists child element
     */
    public static int getChildElementCount(Element parent) {
        return parent.elements().size();
    }

    /**
     * Get the text value of the child element under the given parent
     * element. If there is more than one child element, return the value of the
     * first one.
     *
     * @param parent the parent element
     * @return value of the first child element, null if not exists child element
     */
    public static String getChildElementValue(Element parent) {
        if (parent != null) {
            List<Element> elements = parent.elements();
            if (elements.size() > 0) {
                return getElementValue(elements.get(0));
            }
        }
        return null;
    }
}
