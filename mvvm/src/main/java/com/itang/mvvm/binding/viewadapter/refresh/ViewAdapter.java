package com.itang.mvvm.binding.viewadapter.refresh;

import androidx.databinding.BindingAdapter;

import com.itang.mvvm.binding.command.BindingCommand;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class ViewAdapter {
    @BindingAdapter(value = {"onRefreshCommand", "onLoadMoreCommand"}, requireAll = false)
    public static void onSmartRefreshLayout(SmartRefreshLayout refreshLayout, final BindingCommand onRefreshCommand,
                                            final BindingCommand onLoadMoreCommand) {
        if (onRefreshCommand != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                    onRefreshCommand.execute();
                }
            });
        }
        if (onLoadMoreCommand != null) {
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    //refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    onLoadMoreCommand.execute();
                }
            });
        }
    }
}
