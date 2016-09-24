package com.yxkang.android.xmlparser.entry;

import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;

import java.util.ArrayList;

/**
 * Created by yexiaokang on 2016/9/23.
 */

public class XmlElement implements Cloneable {

    /**
     * the element name
     */
    private String elementName;

    /**
     * the element value, this value maybe {@code null}
     */
    private String elementValue;

    /**
     * the element item name, this use in a scene:
     * <pre>
     *     &lt;element&gt;
     *         &lt;itemName&gt;primitive type value&lt;/itemName&gt;
     *     &lt;/element&gt;
     *     ...
     * </pre>
     * this value maybe {@code null}, see {@link Element#itemName()} or {@link ElementList#itemName()}
     */
    private String itemName;

    /**
     * the element attributes
     */
    private ArrayList<XmlAttribute> attributes;

    /**
     * the element namespaces
     */
    private ArrayList<XmlNamespace> namespaces;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementValue() {
        return elementValue;
    }

    public void setElementValue(String elementValue) {
        this.elementValue = elementValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ArrayList<XmlAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<XmlAttribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<XmlNamespace> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(ArrayList<XmlNamespace> namespaces) {
        this.namespaces = namespaces;
    }

    public XmlElement deepCopy() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected XmlElement clone() throws CloneNotSupportedException {
        XmlElement xmlElement = (XmlElement) super.clone();
        xmlElement.elementName = this.elementName;
        xmlElement.elementValue = this.elementValue;
        xmlElement.itemName = this.itemName;
        if (this.attributes != null) {
            //noinspection unchecked
            xmlElement.attributes = (ArrayList<XmlAttribute>) this.attributes.clone();
        }
        if (this.namespaces != null) {
            //noinspection unchecked
            xmlElement.namespaces = (ArrayList<XmlNamespace>) this.namespaces.clone();
        }
        return xmlElement;
    }
}
