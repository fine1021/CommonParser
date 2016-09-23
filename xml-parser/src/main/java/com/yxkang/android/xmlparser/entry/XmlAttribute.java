package com.yxkang.android.xmlparser.entry;

/**
 * Created by yexiaokang on 2016/9/22.
 *
 * @see {@link com.yxkang.android.xmlparser.annotation.Attribute}
 */

public class XmlAttribute implements Comparable<XmlAttribute> {

    private String name;
    private String value;
    private int order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(XmlAttribute o) {
        if (order != o.getOrder()) {
            return order - o.getOrder();
        } else {
            return name.compareTo(o.name);
        }
    }
}
