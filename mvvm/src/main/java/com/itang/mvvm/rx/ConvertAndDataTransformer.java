package com.itang.mvvm.rx;

import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * 封装 ConvertFunction 和 HttpResultFunc
 *
 * @param <T>
 */
public class ConvertAndDataTransformer<T> implements ObservableTransformer<ResponseBody, T>, FlowableTransformer<ResponseBody, T> {

    private Type type;

    public ConvertAndDataTransformer(Type type) {
        this.type = type;
    }

    @Override
    public ObservableSource<T> apply(Observable<ResponseBody> upstream) {
        return upstream.map(new ConvertFunction<T>(type))// 利用 Gson 手动将 ResponseBody 转化为 HttpResult<T>
                .map(new HttpResultFunc<T>());// 将 HttpResult 的成员 data 剥离出来
    }

    @Override
    public @NonNull Publisher<T> apply(io.reactivex.rxjava3.core.@NonNull Flowable<ResponseBody> upstream) {
        return upstream.map(new ConvertFunction<T>(type))// 利用 Gson 手动将 ResponseBody 转化为 HttpResult<T>
                .map(new HttpResultFunc<T>());// 将 HttpResult 的成员 data 剥离出来
    }
}
