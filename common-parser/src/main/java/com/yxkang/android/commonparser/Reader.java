package com.yxkang.android.commonparser;

import java.util.List;

/**
 * Created by yexiaokang on 2016/6/7.
 * <p>content reader</p>
 */
public interface Reader {

    /**
     * check if has the property of given name
     *
     * @param name map name
     * @return {@code true} if has, otherwise {@code false}
     */
    boolean contain(String name);

    /**
     * read a primitive type
     *
     * @param name map name
     * @return primitive object
     */
    Object getPrimitiveObject(String name);


    /**
     * read a custom class
     *
     * @param name map name
     * @param type map type
     * @return class entity
     * @throws Exception the parse exception
     */
    Object getObject(String name, Class<?> type) throws Exception;

    /**
     * read a list of object, maybe a custom class or primitive type
     *
     * @param listName list name
     * @param itemName the item name
     * @param subType  sub type
     * @return the list of the sub class entity
     * @throws Exception the parse exception
     */
    List<?> getListObjects(String listName, String itemName, Class<?> subType) throws Exception;
}
