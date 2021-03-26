package com.itang.mvvm.utils;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * MVVM 架构帮助类
 */
public class MVVMUtils {

    public static <T extends ViewDataBinding> T inflate(LayoutInflater inflater, int layotId) {
        return DataBindingUtil.inflate(inflater, layotId, null, false);
    }

    public static <T extends ViewDataBinding> T inflate(LayoutInflater inflater, int layotId, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, layotId, container, false);
    }

    public static <T extends ViewModel> T createViewModel(Application application, ViewModelStoreOwner owner, Class<T> clazz) {
        return new ViewModelProvider(owner, new ViewModelProvider.AndroidViewModelFactory(application)).get(clazz);
    }

    public static <K> ObservableField<K> createObservableField(ObservableField<K> field, K value) {
        if (field == null) {
            field = new ObservableField<>(value);
        }
        return field;
    }
}
