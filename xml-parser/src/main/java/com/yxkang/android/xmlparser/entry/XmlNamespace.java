package com.yxkang.android.xmlparser.entry;

/**
 * Created by yexiaokang on 2016/9/23.
 * <p>
 * same as the {@link com.yxkang.android.xmlparser.annotation.Namespace}
 */

public class XmlNamespace {

    private String namespaceURI;
    private String prefix;
    private boolean requiredPrefix;

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public void setNamespaceURI(String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isRequiredPrefix() {
        return requiredPrefix;
    }

    public void setRequiredPrefix(boolean requiredPrefix) {
        this.requiredPrefix = requiredPrefix;
    }
}
