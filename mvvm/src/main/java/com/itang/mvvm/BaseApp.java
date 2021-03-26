package com.itang.mvvm;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

import dagger.hilt.android.HiltAndroidApp;

public class BaseApp extends MultiDexApplication {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ARouter.init(this);
    }
    public static synchronized BaseApp getAppContext() {
        return (BaseApp) context;
    }
}
