package com.itang.android.mvvmlib.demo2;

import android.app.Application;

import androidx.annotation.NonNull;

import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t2vm.BaseTargetViewModel;

public class Demo2ViewModel extends BaseTargetViewModel<Config> {
    public Demo2ViewModel(@NonNull Application application) {
        super(application);
    }
}
