package com.yxkang.android.commonparser.converter;

import android.text.TextUtils;

import com.yxkang.android.commonparser.Converter;
import com.yxkang.android.commonparser.Reader;
import com.yxkang.android.commonparser.exc.JsonParseException;
import com.yxkang.android.commonparser.trace.Logger;
import com.yxkang.android.commonparser.util.JsonUtils;
import com.yxkang.android.commonparser.util.ParserLogger;
import com.yxkang.android.commonparser.util.ParserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexiaokang on 2016/6/1.
 */
public class JsonConverter implements Converter {

    private Logger logger;

    public JsonConverter() {
        logger = new ParserLogger();
    }

    @Override
    public <T> T convert(Class<T> clazz, String text) {
        try {
            JSONObject jsonObject = new JSONObject(text);
            return fromJson(clazz, jsonObject);
        } catch (JSONException e) {
            logger.error("convert JSONException", e);
            return null;
        }
    }

    private <T> T fromJson(Class<T> clazz, final JSONObject jsonObject) {
        return Converters.convert(clazz, new Reader() {

            @Override
            public boolean contain(String name) {
                logger.debug("contain: name = %s", name);
                return jsonObject.has(name);
            }

            @Override
            public Object getPrimitiveObject(String name) {
                logger.debug("getPrimitiveObject: name = %s", name);
                return jsonObject.opt(name);
            }

            @Override
            public Object getObject(String name, Class<?> type) throws Exception {
                logger.debug("getObject: name = %s", name);
                JSONObject jsonObj = jsonObject.optJSONObject(name);
                if (jsonObj != null) {
                    Object object;
                    int jsonObjLength = jsonObj.length();
                    int annotationFieldCount = ParserUtils.getAnnotationFieldCount(type);
                    logger.info("getObject: json mappings sizes = %d, annotationFieldCount = %d",
                            jsonObjLength, annotationFieldCount);
                    if (jsonObjLength == 1 && annotationFieldCount == 0) {
                        logger.debug("the custom class has only one mappings value and no annotation field, " +
                                "use the constructor with one String parameter type to instance an object");
                        Constructor constructor = type.getConstructor(String.class);
                        constructor.setAccessible(true);
                        object = constructor.newInstance(JsonUtils.getJsonValue(jsonObj));
                    } else {
                        object = fromJson(type, jsonObj);
                    }
                    return object;
                }
                return null;
            }

            @Override
            public List<?> getListObjects(String listName, String itemName, Class<?> subType) throws Exception {
                logger.debug("getObject: listName = %s, itemName = %s", listName, itemName);
                JSONArray jsonArray = jsonObject.optJSONArray(listName);
                if (jsonArray != null) {
                    int length = jsonArray.length();
                    logger.info("getListObjects: array length = %d", length);
                    List<Object> list = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        int jsonObjLength = jsonObj.length();
                        Object object;
                        if (ParserUtils.isPrimitiveType(subType)) {
                            if (TextUtils.isEmpty(itemName)) {
                                if (jsonObjLength > 1) {
                                    throw new JsonParseException("lack of itemName, listName = " + listName);
                                } else if (jsonObjLength == 1) {
                                    itemName = jsonObj.keys().next();
                                }
                            }
                            object = jsonObj.opt(itemName);
                        } else {             // subType is not primitive type, regard it as custom class
                            int annotationFieldCount = ParserUtils.getAnnotationFieldCount(subType);
                            logger.info("getListObjects: json mappings sizes = %d, annotationFieldCount = %d",
                                    jsonObjLength, annotationFieldCount);
                            if (jsonObjLength == 1 && annotationFieldCount == 0) {
                                logger.debug("the custom class has only one mappings value and no annotation field, " +
                                        "use the constructor with one String parameter type to instance an object");
                                Constructor constructor = subType.getConstructor(String.class);
                                constructor.setAccessible(true);
                                object = constructor.newInstance(JsonUtils.getJsonValue(jsonObj));
                            } else {
                                object = fromJson(subType, jsonObj);
                            }
                        }

                        if (object != null) {
                            if (String.class.isAssignableFrom(subType)) {
                                object = String.valueOf(object);
                            } else if (Number.class.isAssignableFrom(subType)) {
                                if (Byte.class.isAssignableFrom(subType)) {
                                    object = Byte.valueOf(object.toString());
                                } else if (Short.class.isAssignableFrom(subType)) {
                                    object = Short.valueOf(object.toString());
                                } else if (Integer.class.isAssignableFrom(subType)) {
                                    object = Integer.valueOf(object.toString());
                                } else if (Long.class.isAssignableFrom(subType)) {
                                    object = Long.valueOf(object.toString());
                                } else if (Float.class.isAssignableFrom(subType)) {
                                    object = Float.valueOf(object.toString());
                                } else if (Double.class.isAssignableFrom(subType)) {
                                    object = Double.valueOf(object.toString());
                                }
                            } else if (Boolean.class.isAssignableFrom(subType)) {
                                object = Boolean.valueOf(object.toString());
                            }
                            list.add(object);
                        }
                    }
                    return list;
                }
                return null;
            }
        }, logger);
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
