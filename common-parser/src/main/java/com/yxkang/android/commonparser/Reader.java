package com.yxkang.android.commonparser;

import java.util.List;

/**
 * Created by yexiaokang on 2016/6/7.
 * 内容读取器
 */
public interface Reader {

    /**
     * 检查是否包含指定的属性
     *
     * @param name 映射名称
     * @return {@code true} 如果包含，否则 {@code false}
     */
    boolean contain(String name);

    /**
     * 读取单个基本对象。
     *
     * @param name 映射名称
     * @return 基本对象值
     */
    Object getPrimitiveObject(String name);

    /**
     * 读取单个自定义对象。
     *
     * @param name 映射名称
     * @param type 映射类型
     * @return 映射类型的实例
     */
    Object getObject(String name, Class<?> type) throws Exception;

    /**
     * 读取多个对象的值。
     *
     * @param listName 列表名称
     * @param itemName 映射名称
     * @param subType  嵌套映射类型
     * @return 嵌套映射类型实例列表
     */
    List<?> getListObjects(String listName, String itemName, Class<?> subType) throws Exception;
}
