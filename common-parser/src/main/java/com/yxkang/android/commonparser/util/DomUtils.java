package com.yxkang.android.commonparser.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fine on 2016/6/11.
 */
public class DomUtils {

    /**
     * Gets the immediately child elements list from the parent element.
     *
     * @param parent  the parent element in the element tree
     * @param tagName the specified tag name
     * @return the NOT NULL immediately child elements list
     */
    public static List<Element> getChildElements(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        List<Element> elements = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element && node.getParentNode() == parent) {
                elements.add((Element) node);
            }
        }

        return elements;
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
            NodeList nodes = element.getChildNodes();
            if (nodes != null && nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node instanceof Text) {
                        return ((Text) node).getData();
                    }
                }
            }
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
            NodeList nodes = element.getChildNodes();
            if (nodes != null && nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node instanceof Text) {
                        return ((Text) node).getData();
                    }
                }
            }
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
        int count = 0;
        if (parent != null) {
            NodeList list = parent.getChildNodes();
            if (list != null && list.getLength() > 0) {
                int length = list.getLength();
                for (int i = 0; i < length; i++) {
                    Node node = list.item(i);
                    if (node instanceof Element) {
                        count++;
                    }
                }
            }
        }
        return count;
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
            NodeList list = parent.getChildNodes();
            if (list != null && list.getLength() > 0) {
                int length = list.getLength();
                for (int i = 0; i < length; i++) {
                    Node node = list.item(i);
                    if (node instanceof Element) {
                        return getElementValue((Element) node);
                    }
                }
            }
        }
        return null;
    }
}
