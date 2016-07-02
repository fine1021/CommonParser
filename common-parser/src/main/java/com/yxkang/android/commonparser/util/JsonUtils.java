package com.yxkang.android.commonparser.util;

import com.yxkang.android.commonparser.exc.JsonParseException;

import org.json.JSONObject;

/**
 * Created by yexiaokang on 2016/7/2.
 */
public class JsonUtils {

    /**
     * get the current json object value
     *
     * @param jsonObject the json object
     * @return json object value
     * @throws JsonParseException jsonObject values is more than one
     */
    public static String getJsonValue(JSONObject jsonObject) throws JsonParseException {
        if (jsonObject.length() != 1) {
            throw new JsonParseException("jsonObject values is more than one");
        }
        String itemName = jsonObject.keys().next();
        return jsonObject.optString(itemName);
    }
}
