package com.itang.mvvm.rx;

import com.google.gson.Gson;
import com.itang.mvvm.jetpack.t4model.bean.HttpResult;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;

/**
 * 将 ResponseBody 转化为对应的 Bean
 *
 * 原本需要泛型 <T>，如下：
 * public class ConvertFunction<T> implements Function<ResponseBody, HttpResult<T>> {
 * 但是，
 * 这里可以省去泛型定义，因为成员 type 是由外部通过 new TypeToken<HttpResult<具体类型>>(){}.getType() 传递进来的，
 * 这样做是为了解决 泛型擦除导致 Gson 将 HttpResult 中的 data 默认解析为 LinkedHashMap 的问题（原理是 TypeToken 生成了匿名内部类
 * 存储了类上的泛型类型）
 */
public class ConvertFunction<T> implements Function<ResponseBody, HttpResult<T>> {

    /**
     * 由外部通过 new TypeToken<HttpResult<具体类型>>(){}.getType() 传递进来的
     */
    private Type type;

    public ConvertFunction(Type type) {
        this.type = type;
    }

    @Override
    public HttpResult<T> apply(ResponseBody responseBody) throws Exception {
        Gson gson = new Gson();// TODO Gson提取到全局
        return gson.fromJson(responseBody.string(), type);
    }


}