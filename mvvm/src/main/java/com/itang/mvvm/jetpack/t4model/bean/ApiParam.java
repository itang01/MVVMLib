package com.itang.mvvm.jetpack.t4model.bean;

import android.util.ArrayMap;

import com.itang.mvvm.jetpack.t4model.bean.params.Param;
import com.itang.mvvm.jetpack.t4model.bean.params.ParamsBuilder;
import com.itang.mvvm.utils.CollectionUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 接口请求参数封装
 */
public class ApiParam {

    private String url;//接口地址
    private ArrayMap<String, Object> param;//普通参数
    private ArrayMap<String, List<File>> filesMap;//文件参数

    public ApiParam() {
        this.url = "";
    }

    public ApiParam(String url) {
        this.url = url;
    }

    public ApiParam setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加普通参数
     *
     * @param key
     * @param value
     * @return
     */
    public ApiParam addParam(String key, Object value) {
        if (param == null) {
            param = new ArrayMap<>();
        }
        param.put(key, value);
        return this;
    }

    /**
     * 添加文件参数
     *
     * @param key
     * @param files
     * @return
     */
    public ApiParam addFileParam(String key, List<File> files) {
        if (filesMap == null) {
            filesMap = new ArrayMap<>();
        }
        filesMap.put(key, files);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArrayMap<String, Object> getParam() {
        return param;
    }

    public ArrayMap<String, List<File>> getFilesMap() {
        return filesMap;
    }

}