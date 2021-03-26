package com.itang.android.mvvmlib.demo1;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.reflect.TypeToken;
import com.itang.android.mvvmlib.RouteApi;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t2vm.BaseTargetViewModel;
import com.itang.mvvm.jetpack.t4model.bean.ApiParam;
import com.itang.mvvm.rx.SchedulerTransformer;
import com.itang.mvvm.utils.L;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class Demo1ViewModel extends BaseTargetViewModel<Config> {
    public Demo1ViewModel(@NonNull Application application) {
        super(application);
        Type type = new TypeToken<ResponseBody>() {
        }.getType();
        Object navigation = ARouter.getInstance().build(RouteApi.Demo1).navigation();
        addSubscribe(((Demo1Respositry) navigation)
                .get(new ApiParam("https://www.wanandroid.com//hotkey/json"))
                .compose(new SchedulerTransformer<>())
                .subscribe(responseBody -> {
                    L.d("请求成功：");
                    L.json(responseBody.string());
                }));
    }
}
