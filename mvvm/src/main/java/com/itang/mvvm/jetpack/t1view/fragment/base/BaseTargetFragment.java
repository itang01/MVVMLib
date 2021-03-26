package com.itang.mvvm.jetpack.t1view.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.classic.common.MultipleStatusView;
import com.flyco.dialog.widget.NormalDialog;
import com.itang.mvvm.MVVMConstants;
import com.itang.mvvm.R;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.ViewDelegate;
import com.itang.mvvm.jetpack.t2vm.BaseTargetViewModel;
import com.itang.mvvm.utils.DialogUtils;
import com.itang.mvvm.utils.MVVMUtils;
import com.itang.mvvm.widgit.MyMultipleStatusView;

import org.jetbrains.annotations.NotNull;

/**
 * MVVM 架构 带 TitleBar 的 TargetFragment 基类
 */
@Route(path = MVVMConstants.RouteHub.BaseTitleBarFragment)
public class BaseTargetFragment<Binding extends ViewDataBinding, ViewModel extends BaseTargetViewModel<?>, C extends Config>
        extends BaseFragment {

    protected NormalDialog mTipDialog;
    private ViewDelegate<Binding, ViewModel> targetViewDelegate;

    @Override
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        super.buildInnerConfig(innerConfig, outerConfig);
        innerConfig.setMenu1Icon(R.mipmap.btn_history)
                .setMenu1Type(Config.TYPE_NONE);
    }

    @Override
    protected void onCreateTargetView(LayoutInflater inflater) {
        super.onCreateTargetView(inflater);
        if (getInnerConfig().getTargetViewLayout() <= 0) return;
        Binding binding = MVVMUtils.inflate(inflater, getInnerConfig().getTargetViewLayout(), getRootViewBinding().rootView);
        getRootViewBinding().statusView.showContent(binding.getRoot(), getTargetViewLayoutParams(binding.getRoot().getLayoutParams()));
        getTargetViewDelegate().setBinding(binding);
    }

    protected ViewGroup.LayoutParams getTargetViewLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null ? layoutParams : new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void init() {
        super.init();
        initTargetView();
    }

    private void initTargetView() {
        if (getTargetBinding() == null) return;
        getTargetViewDelegate().setViewModelClass(getInnerConfig().getTargetViewModelClass())
                .setVariableId(getInnerConfig().getTargetViewVariableId())
                .setLifecycle(getLifecycle())
                .setLifecycleOwner(this)
                .setLifecycleProvider(this)
                .setViewModelStoreOwner(this);
        getTargetViewDelegate().init();
        //将外部 config 设置到 TargetViewModel 中
        getTargetViewModel().setConfigLiveData(getOuterConfig());
        //将带有 TitleBar 的 ViewModel 设置到 TargetViewModel 中
        getTargetViewModel().setBaseUiViewModel(getBaseUiViewModel());
        getBaseUiViewModel().showTitleBarField.set(getInnerConfig().isHadDefaultToolbar);
        getBaseUiViewModel().leftTypeField.set(getInnerConfig().leftType);
        getBaseUiViewModel().centerTextField.set(getInnerConfig().title);
        getBaseUiViewModel().menu1TypeField.set(getInnerConfig().menu1Type);
        getBaseUiViewModel().menu1TextField.set(getInnerConfig().menu1Text);
        getBaseUiViewModel().menu1ImageResourceField.set(getInnerConfig().menu1Icon);
        getBaseUiViewModel().menu2TypeField.set(getInnerConfig().menu2Type);
        getBaseUiViewModel().menu2TextField.set(getInnerConfig().menu2Text);
        getBaseUiViewModel().menu2ImageResourceField.set(getInnerConfig().menu2Icon);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (getTargetViewModel() == null) return;
        getTargetViewModel().init();
    }

    @NotNull
    protected ViewGroup getTargetView() {
        return (ViewGroup) getTargetBinding().getRoot();
    }

    public ViewDelegate<Binding, ViewModel> getTargetViewDelegate() {
        if (null == targetViewDelegate) {
            targetViewDelegate = new ViewDelegate<>(context);
        }
        return targetViewDelegate;
    }

    public ViewModel getTargetViewModel() {
        return getTargetViewDelegate().getViewModel();
    }

    public Binding getTargetBinding() {
        return getTargetViewDelegate().getBinding();
    }

    public C getOuterConfig() {
        return (C) outerConfig;
    }

    public void finish() {
        getBaseUiViewModel().getBaseUiLiveData().getFinishLiveData().call();
    }

    protected void setTitle(String title) {
        getBaseUiViewModel().centerTextField.set(title);
    }

    @Override
    protected void onRetryGetData() {
        showStatusView(new MyMultipleStatusView.Config(MultipleStatusView.STATUS_LOADING));
        getTargetViewModel().getData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.destory(mTipDialog);
    }

    @Override
    public void registerUIChangeLiveDataCallBack() {
        super.registerUIChangeLiveDataCallBack();
        getBaseUiViewModel().getBaseUiLiveData().getOnMenu1ClickLiveData().observe(getViewLifecycleOwner(), o -> onMenu1Click());
    }

    protected void onMenu1Click() {

    }
}
