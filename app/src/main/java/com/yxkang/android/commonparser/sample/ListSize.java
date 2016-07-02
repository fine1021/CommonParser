package com.yxkang.android.commonparser.sample;

import com.yxkang.android.commonparser.annotation.MsgItemField;

import java.util.List;

/**
 * Created by yexiaokang on 2016/7/2.
 */
public class ListSize {

    @MsgItemField(value = "data")
    private List<Size> sizes;

    public ListSize() {
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "ListSize {" +
                "sizes=" + sizes +
                '}';
    }
}
