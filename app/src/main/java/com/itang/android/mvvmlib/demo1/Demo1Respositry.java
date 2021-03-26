package com.itang.android.mvvmlib.demo1;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.itang.android.mvvmlib.RouteApi;
import com.itang.mvvm.BaseApp;
import com.itang.mvvm.jetpack.t3respository.BaseRespository;
import com.itang.mvvm.jetpack.t4model.bean.ApiParam;
import com.itang.mvvm.jetpack.t4model.bean.params.ParamsBuilder;
import com.itang.mvvm.jetpack.t4model.net.CommonService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = RouteApi.Demo1)
public class Demo1Respositry implements BaseRespository  {

    private Retrofit retrofit;
    private Gson gson;
    private Context context;

    @Inject
    public Demo1Respositry() {
        gson = new Gson();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))//使用Gson
                .build();
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<ResponseBody> get(ApiParam apiParam) {
        return retrofit.create(CommonService.class).get(apiParam.getUrl());
    }

    @Override
    public Flowable<ResponseBody> post2(ApiParam apiParam) {
        return retrofit.create(CommonService.class).post(apiParam.getUrl(),
                ParamsBuilder.createJsonBody(gson.toJson(apiParam.getParam())));
    }

    @Override
    public Flowable<ResponseBody> postForm2(ApiParam apiParam) {
        return null;
    }

    public void sayHello(){
        Toast.makeText(BaseApp.getAppContext(), "hello hilt", Toast.LENGTH_SHORT).show();
    }

}
