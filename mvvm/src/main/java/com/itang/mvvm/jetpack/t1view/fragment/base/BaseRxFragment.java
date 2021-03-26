package com.itang.mvvm.jetpack.t1view.fragment.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.android.RxLifecycleAndroid;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by tangfh on 2017/4/9.
 * rxlifecycle2原理分析：{https://www.jianshu.com/p/0d07fba84cb8?utm_campaign}
 */

public abstract class BaseRxFragment extends SupportFragment implements LifecycleProvider<FragmentEvent> {
        private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * 是否在Fragment onPause or onStop 的时候取消／停止RxJava事件，默认：取消／停止RxJava事件
     * <p>
     * 场景：当前页面的一个事件触发了当前页面的的刷新（从网络获取新数据），并跳转其他Activity的时候，如果不设置该标记为false，
     * Fragment走到onPause or onStop生命周期，此时刷新页面的 请求／事件 会被 取消／停止，这个时候需要将该标记设置为false，
     * 最后，请记得请求成功后将该标记设置为true
     */
    protected boolean isPauseOrStop2CancelNetRequest = true;

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        if (isPauseOrStop2CancelNetRequest())
            lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        if (isPauseOrStop2CancelNetRequest())
            lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    public boolean isPauseOrStop2CancelNetRequest() {
        return isPauseOrStop2CancelNetRequest;
    }

    public BaseRxFragment setPauseOrStop2CancelNetRequest(boolean pauseOrStop2CancelNetRequest) {
        isPauseOrStop2CancelNetRequest = pauseOrStop2CancelNetRequest;
        return this;
    }
}
