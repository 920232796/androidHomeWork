package com.example.sample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParseJson {

    public static String getResponseRet(String jsonString) {


        JSONObject resultJson = JSONObject.parseObject(jsonString);
        return resultJson.getString("ret");

    }

    public static JSONArray getResponseArrays(String jsonString) {
        JSONObject resultJson = JSONObject.parseObject(jsonString);
        return resultJson.getJSONArray("arrays");

    }

    public static JSONObject getResponseObj(String jsonString) {
        JSONObject resultJson = JSONObject.parseObject(jsonString);
        return resultJson.getJSONObject("obj");

    }

    public static String getResponseMsg(String jsonString) {
        JSONObject resultJson = JSONObject.parseObject(jsonString);
        return resultJson.getString("msg");

    }

}
