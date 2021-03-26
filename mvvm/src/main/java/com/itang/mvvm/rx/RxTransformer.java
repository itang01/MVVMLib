package com.itang.mvvm.rx;

import com.trello.rxlifecycle4.LifecycleProvider;

import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * 将 ResponseBody 转化为 T
 * <p>
 * 针对 ResponseBody，进行 compose、map、subscribeOn、observeOn 等一系列操作封装
 *
 * @param <T>
 */
public class RxTransformer<T> implements ObservableTransformer<ResponseBody, T>, FlowableTransformer<ResponseBody, T> {

    private LifecycleProvider lifecycleProvider;

    /**
     * 由外部通过 new TypeToken<HttpResult<具体类型>>(){}.getType() 传递进来的
     */
    private Type type;

    public RxTransformer(LifecycleProvider lifecycleProvider, Type type) {
        this.lifecycleProvider = lifecycleProvider;
        this.type = type;
    }

    @Override
    public ObservableSource<T> apply(Observable<ResponseBody> upstream) {
        return upstream
                .compose(lifecycleProvider.bindToLifecycle())
                .compose(new ConvertAndDataTransformer<T>(type))
                .compose(new SchedulerTransformer<>());
    }

    @Override
    public @NonNull Publisher<T> apply(@NonNull Flowable<ResponseBody> upstream) {
        return upstream
                .compose(lifecycleProvider.bindToLifecycle())
                .compose(new ConvertAndDataTransformer<T>(type))
                .compose(new SchedulerTransformer<>())
                .compose(new ExceptionTransformer<>());
    }
}
