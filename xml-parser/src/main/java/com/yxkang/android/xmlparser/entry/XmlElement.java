package com.yxkang.android.xmlparser.entry;

import java.util.List;

/**
 * Created by yexiaokang on 2016/9/23.
 */

public class XmlElement {

    private List<XmlAttribute> attributes;
    private List<XmlNamespace> namespaces;

    public List<XmlAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<XmlAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<XmlNamespace> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<XmlNamespace> namespaces) {
        this.namespaces = namespaces;
    }

    public boolean isEmpty() {
        return (attributes == null || attributes.isEmpty()) && (namespaces == null || namespaces.isEmpty());
    }
}
