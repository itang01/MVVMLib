package com.itang.mvvm.jetpack.t2vm;

import androidx.annotation.NonNull;

public class MultiItemViewModel<VM extends BaseTargetViewModel, T> extends ItemViewModel<VM, T> {
    public Object multiType;
    public int position;
    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel, null);
    }

    public MultiItemViewModel(@NonNull VM viewModel, T value) {
        super(viewModel, value);
    }
    public MultiItemViewModel(@NonNull VM viewModel, T value, Object multiType) {
        super(viewModel, value);
        this.multiType = multiType;
    }
    public MultiItemViewModel(@NonNull VM viewModel, T value, Object multiType, int position) {
        super(viewModel, value);
        this.multiType = multiType;
        this.position = position;
    }

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }
}