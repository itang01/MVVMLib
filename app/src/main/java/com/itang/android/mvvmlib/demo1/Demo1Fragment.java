package com.itang.android.mvvmlib.demo1;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.itang.android.mvvmlib.BR;
import com.itang.android.mvvmlib.R;
import com.itang.android.mvvmlib.RoutePath;
import com.itang.android.mvvmlib.databinding.FragmentDemo1BindingImpl;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.fragment.base.BaseTargetFragment;
import com.itang.mvvm.utils.RouteUtils;

import me.yokeyword.fragmentation.ISupportFragment;

@Route(path = RoutePath.Demo1Fragment)
public class Demo1Fragment extends BaseTargetFragment<FragmentDemo1BindingImpl, Demo1ViewModel, Config> {

    @Override
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        super.buildInnerConfig(innerConfig, outerConfig);
        innerConfig
                .setHadDefaultToolbar(false)
                .setTargetViewLayout(R.layout.fragment_demo1)
                .setTargetViewVariableId(BR.vm)
                .setTargetViewModelClass(Demo1ViewModel.class);
    }

    @Override
    protected void onCreateTargetView(LayoutInflater inflater) {
        super.onCreateTargetView(inflater);
        getTargetBinding().button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.button) {
            start((ISupportFragment) RouteUtils.getFragment(RoutePath.Demo2Fragment));
        }
    }
}
