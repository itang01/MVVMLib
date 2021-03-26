package com.itang.mvvm.jetpack.t4model.bean.params;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Param implements java.io.Serializable, Comparable<Object> {

    private String key;
    private String value;
    private List<Param[]> valueList;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    public Param(String key, JSONArray jsonArray) {
        this.key = key;
        this.jsonArray = jsonArray;
    }

    public Param(String key, JSONObject jsonObject) {
        this.key = key;
        this.jsonObject = jsonObject;
    }


    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, List<Param[]> valueList) {
        this.key = key;
        this.valueList = valueList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Param[]> getValueList() {
        return valueList;
    }

    public void setValueList(List<Param[]> valueList) {
        this.valueList = valueList;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public Param setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public Param setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int compared;
        /**
         * 值比较
         */
        final Param parameter = (Param) o;
        compared = key.compareTo(parameter.key);
        if (compared == 0) {
            compared = value.compareTo(parameter.value);
        }
        return compared;
    }
}