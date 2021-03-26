package com.itang.mvvm.jetpack.t4model.bean;

import com.itang.mvvm.utils.StringUtils;

/**
 * Created by itang on 2016/5/23.
 */
public class HttpResult<T> {

    // 这个项目
    private int status;
    private String msg;
    private String timestamp;
    private String error_type;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HttpResult<T> setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getError_type() {
        return StringUtils.toInt(error_type, 2);
    }

    public HttpResult<T> setError_type(String error_type) {
        this.error_type = error_type;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
