package com.itang.mvvm.rx;

import com.itang.mvvm.exception.ApiException;
import com.itang.mvvm.jetpack.t4model.bean.HttpResult;
import com.itang.mvvm.utils.StringUtils;

import io.reactivex.rxjava3.functions.Function;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> httpResult) throws Exception {
        if (httpResult.getStatus() != ApiException.SUCCESS && !StringUtils.isEmpty(httpResult.getMsg())) {
            throw new ApiException(httpResult.getStatus(), ApiException.TIP_VIEW_TOAST, httpResult.getMsg());
        }
        return httpResult.getData();
    }
}