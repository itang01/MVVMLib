package com.itang.mvvm.jetpack.t3respository;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.itang.mvvm.jetpack.t4model.IModel;
import com.itang.mvvm.jetpack.t4model.bean.ApiParam;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;

/**
 * Model 层 Model 基类
 */
public interface BaseRespository extends IModel, IProvider {

   Flowable<ResponseBody> get(ApiParam apiParam);

   Flowable<ResponseBody> post2(ApiParam apiParam);

   Flowable<ResponseBody> postForm2(ApiParam apiParam);
}
