package com.itang.mvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.itang.mvvm.MVVMConstants;

public class RouteUtils {
    /**
     * 携带参数的跳转
     *
     * @param context
     * @param param
     */
    public static void navigation(Context context, Parcelable param) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MVVMConstants.KEY_OUTER_CONFIG, param);
        ARouter.getInstance().build(MVVMConstants.RouteHub.ContainerActivity).with(bundle).navigation(context);
    }

    public static void navigation(Parcelable param) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MVVMConstants.KEY_OUTER_CONFIG, param);
        ARouter.getInstance().build(MVVMConstants.RouteHub.ContainerActivity).with(bundle).navigation();
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 这个方法因为没有使用 {@link Activity}跳转
     * 所以 {@link ARouter} 会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link ARouter#getInstance()#navigation(Context, String)} 并传入 {@link Activity}
     *
     * @param path {@code path}
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param context
     * @param path
     */
    public static void navigation(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static Fragment getFragment(String fragmentPath) {
        return getFragment(fragmentPath, null);
    }

    public static Fragment getFragment(String fragmentPath, Parcelable param) {
        return (Fragment) ARouter.getInstance()
                .build(fragmentPath)
                .withParcelable(MVVMConstants.KEY_OUTER_CONFIG, param)
                .navigation();
    }
}
