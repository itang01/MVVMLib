package com.itang.mvvm.jetpack.t2vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.itang.mvvm.bus.event.TitleBarEvent;
import com.itang.mvvm.widgit.MyMultipleStatusView;
import com.trello.rxlifecycle4.LifecycleProvider;

public abstract class BaseViewModelAbs extends AndroidViewModel implements IBaseViewModel {

    public BaseViewModelAbs(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    /**
     * 初始化
     */
    public void init() {

    }

    /**
     * 获取页面数据
     *
     * @param isRefresh
     */
    public void getData(boolean isRefresh) {

    }

    /**
     * 注入 RxLifecycle 生命周期
     *
     * @param lifecycleProvider RxLifecycle 生命周期
     */
    public abstract void injectLifecycleProvider(LifecycleProvider<?> lifecycleProvider);

    /**
     * 获取 UiLiveData （监听和控制 UI）
     *
     * @return
     */
    public abstract BaseUiViewModel.UILiveData getBaseUiLiveData();

    /**
     * 注册 Bus
     */
    public void registerBus() {
    }

    /**
     * 移除 Bus
     */
    public void removeBus() {
    }

    /**
     * 打印日志
     *
     * @param obj
     */
    public void log(Object obj) {
    }

    /**
     * 提示（一般为吐司）
     */
    public void tip(String message) {

    }

    /**
     * 显示多状态视图
     *
     * @param config
     */
    public void showStatusView(MyMultipleStatusView.Config config) {

    }

    /**
     * 点击标题栏的子控件
     *
     * @param event
     */
    public void onTitleBarClick(TitleBarEvent event) {

    }
}
