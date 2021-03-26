package com.itang.mvvm.jetpack.t4model.net;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CommonService {
    /**
     * 发送 POST 请求 （支持背压）
     *
     * @return
     */
    @GET
    Flowable<ResponseBody> get(@Url String url);

    /**
     * 发送 POST 请求 （支持背压）
     *
     * @return
     */
    @POST
    Flowable<ResponseBody> post(@Url String url, @Body RequestBody body);

    /**
     * 发送 POST FORM 请求
     *
     * @param url
     * @param multipartBody
     * @return
     */
    @POST
    Flowable<ResponseBody> postForm(@Url String url, @Body MultipartBody multipartBody);
}
