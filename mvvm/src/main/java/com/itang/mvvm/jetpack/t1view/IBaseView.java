package com.itang.mvvm.jetpack.t1view;

import android.os.Bundle;

import com.itang.mvvm.widgit.MyMultipleStatusView;

/**
 * View协议
 */
public interface IBaseView {

    /**
     * View 初始化，Binding，ViewModel
     */
    void init();

    /**
     * 初始化界面传递参数
     *
     * @param bundle
     */
    void initParam(Bundle bundle);

    /**
     * 私有的ViewModel与View的契约事件回调逻辑
     */
    void registerUIChangeLiveDataCallBack();

    /**
     * 销毁时做清除、解绑的操作
     */
    void onCleared();

    /**
     * 提示（一般为吐司）
     *
     * @param message 提示语
     */
    void tip(String message);

    /**
     * 显示/隐藏 加载对话框
     *
     * @param title 提示语
     */
    void showProgressDialog(boolean show, String... title);

    /**
     * 返回上一页
     */
    void onBackPressed();

    /**
     * 打印日志
     *
     * @param obj
     */
    void log(Object obj);

    /**
     * 显示 多状态视图
     */
    void showStatusView(MyMultipleStatusView.Config config);
}
