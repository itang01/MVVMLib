package com.itang.mvvm.jetpack.t2vm;


import androidx.annotation.NonNull;

import com.itang.mvvm.binding.command.BindingAction;
import com.itang.mvvm.binding.command.BindingCommand;

/**
 * ItemViewModel
 * Created by goldze on 2018/10/3.
 */

public class ItemViewModel<VM extends BaseTargetViewModel, T> {
    public VM viewModel;
    public FixedLiveData<T> data  = new FixedLiveData();

    public ItemViewModel(@NonNull VM viewModel, T value) {
        this.viewModel = viewModel;
        this.data.setValue(value);
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onItemClick();
        }
    });

    protected void onItemClick() {

    }

    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
            onItemLongClick();
        }
    });

    protected void onItemLongClick() {

    }
}
