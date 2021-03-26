package com.itang.mvvm.jetpack.t1view.fragment.base;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.itang.mvvm.BR;
import com.itang.mvvm.MVVMConstants;
import com.itang.mvvm.R;
import com.itang.mvvm.databinding.MvvmFragmentBaseListBindingImpl;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t2vm.BaseListViewModel;

/**
 * 列表 基类
 */
@Route(path = MVVMConstants.RouteHub.BaseListFragment)
public class BaseListFragment<VM extends BaseListViewModel<?>, C extends Config>
        extends BaseTargetFragment<MvvmFragmentBaseListBindingImpl, VM, C> {
    @Override
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        super.buildInnerConfig(innerConfig, outerConfig);
        innerConfig.setTargetViewLayout(R.layout.mvvm_fragment_base_list)
                .setTargetViewVariableId(BR.vm)
                .setTargetViewModelClass(BaseListViewModel.class)
                .setRefreshEnable(true);
    }

    @Override
    public void init() {
        super.init();
        getTargetBinding().refreshLayout.setEnableRefresh(getInnerConfig().isRefreshEnable);
        getTargetBinding().refreshLayout.setEnableLoadMore(getInnerConfig().isLoadMoreEnable);
        getTargetBinding().refreshLayout.setEnableFooterFollowWhenLoadFinished(false);
    }

    @Override
    public void registerUIChangeLiveDataCallBack() {
        super.registerUIChangeLiveDataCallBack();
        getTargetViewModel().getOnRefreshFinishLiveData().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                getTargetBinding().refreshLayout.finishRefresh(300);
            }
        });
        getTargetViewModel().getOnLoadMoreFinishLiveData().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                getTargetBinding().refreshLayout.finishLoadMore(300);
            }
        });
        getTargetViewModel().getOnLoadMoreEndLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMoreEnd) {
                if (isLoadMoreEnd) {
                    getTargetBinding().refreshLayout.finishLoadMore();
                    getTargetBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    getTargetBinding().refreshLayout.setNoMoreData(false);
                }
            }
        });
    }
}
