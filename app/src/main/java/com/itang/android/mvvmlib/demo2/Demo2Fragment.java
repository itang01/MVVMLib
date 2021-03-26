package com.itang.android.mvvmlib.demo2;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.itang.android.mvvmlib.BR;
import com.itang.android.mvvmlib.R;
import com.itang.android.mvvmlib.RoutePath;
import com.itang.android.mvvmlib.databinding.FragmentDemo2BindingImpl;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.fragment.base.BaseTargetFragment;

@Route(path = RoutePath.Demo2Fragment)
public class Demo2Fragment extends BaseTargetFragment<FragmentDemo2BindingImpl, Demo2ViewModel, Config> {

    @Override
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        super.buildInnerConfig(innerConfig, outerConfig);
        innerConfig
                .setTitle("demo2")
                .setTargetViewLayout(R.layout.fragment_demo2)
                .setTargetViewVariableId(BR.vm)
                .setTargetViewModelClass(Demo2ViewModel.class)
        ;
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
            onBackPressed();
        }
    }
}
