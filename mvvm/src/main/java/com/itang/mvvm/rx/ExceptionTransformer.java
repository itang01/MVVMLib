package com.itang.mvvm.rx;

import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.functions.Function;

/**
 * 异常处理
 *
 * @param <Upstream>
 */
public class ExceptionTransformer<@NonNull Upstream, @NonNull Downstream> implements FlowableTransformer<@NonNull Upstream, @NonNull Downstream> {
    @Override
    public @NonNull Publisher<Downstream> apply(@NonNull Flowable<Upstream> upstream) {
        return upstream.onErrorResumeNext(new HttpResponseFunc());
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Flowable<T>> {
        @Override
        public Flowable<T> apply(Throwable t) {
            return Flowable.error(ExceptionHandle.handleException(t));
        }
    }
}
