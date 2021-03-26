package com.itang.mvvm.jetpack.t1view.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itang.mvvm.BR;
import com.itang.mvvm.MVVMConstants;
import com.itang.mvvm.R;
import com.itang.mvvm.databinding.MvvmFragmentBaseBindingImpl;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.IBaseView;
import com.itang.mvvm.jetpack.t1view.ViewDelegate;
import com.itang.mvvm.jetpack.t2vm.BaseUiViewModel;
import com.itang.mvvm.utils.MVVMUtils;
import com.itang.mvvm.widgit.MyMultipleStatusView;

import org.jetbrains.annotations.NotNull;

/**
 * MVVM 架构 Fragment 基类
 */
public abstract class BaseFragment extends BaseRxFragment implements IBaseView, View.OnClickListener {

    public Context context;
    private Config innerConfig;
    protected Config outerConfig;
    private ViewDelegate<MvvmFragmentBaseBindingImpl, BaseUiViewModel> rootViewDelegate;

    /**
     * 页面配置
     *
     * @param innerConfig 内部配置
     * @param outerConfig 外部配置
     */
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        innerConfig.setContentViewLayout(R.layout.mvvm_fragment_base)
                .setVariableId(BR.vm)
                .setViewModelClass(BaseUiViewModel.class);
    }

    /**
     * Fragment 依附到 Activity
     *
     * @param context
     */
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;
        initRootViewDelegate();
        getRootViewDelegate().setFragment(this);
        Bundle arguments = getArguments();
        initParam(arguments);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MvvmFragmentBaseBindingImpl binding = MVVMUtils.inflate(inflater, getInnerConfig().getContentViewLayout(), container);
        getRootViewDelegate().setBinding(binding);
        onCreateTargetView(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRootViewDelegate().setViewModelClass(getInnerConfig().getViewModelClass())
                .setVariableId(getInnerConfig().getVariableId())
                .setLifecycle(getLifecycle())
                .setLifecycleOwner(this)
                .setLifecycleProvider(this)
                .setViewModelStoreOwner(this);
        getRootViewDelegate().init();
        getRootViewBinding().statusView.setOnRetryClickListener(this);
        init();
        registerUIChangeLiveDataCallBack();
    }

    //执行顺序 onViewCreated() -> onLazyInitView()
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void init() {
    }

    @Override
    public void initParam(Bundle bundle) {
        try {
            //创建内部 Config
            innerConfig = new Config();
            //获取外部 Config
            initOuterConfig(bundle);
            //可能需要 outerConfig 去初始化 innerConfig
            //注：外部传递进来的 outerConfig 可能不是泛型 C 类型，而是 Config 类型
            buildInnerConfig(innerConfig, outerConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化外部配置
     *
     * @param bundle
     */
    protected void initOuterConfig(Bundle bundle) {
        outerConfig = bundle.getParcelable(MVVMConstants.KEY_OUTER_CONFIG);
    }

    @NonNull
    public ViewDelegate<MvvmFragmentBaseBindingImpl, BaseUiViewModel> getRootViewDelegate() {
        initRootViewDelegate();
        return rootViewDelegate;
    }

    /**
     * 初始化 MVVMView
     */
    private void initRootViewDelegate() {
        if (null == rootViewDelegate) {
            rootViewDelegate = new ViewDelegate<>(context);
        }
    }

    /**
     * 创建 TargetView
     *
     * @param inflater
     */
    protected void onCreateTargetView(LayoutInflater inflater) {
    }

    protected BaseUiViewModel getBaseUiViewModel() {
        return getRootViewDelegate().getViewModel();
    }

    protected MvvmFragmentBaseBindingImpl getRootViewBinding() {
        return getRootViewDelegate().getBinding();
    }

    public Config getInnerConfig() {
        return innerConfig;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onCleared();
    }

    @Override
    public void onCleared() {
        getRootViewDelegate().onCleared();
    }

    @Override
    public void onBackPressed() {
        getRootViewDelegate().getActivity().onBackPressedSupport();
    }

    @Override
    public void registerUIChangeLiveDataCallBack() {

    }


    @Override
    public void tip(String message) {
        getRootViewDelegate().tip(message);
    }

    @Override
    public void showProgressDialog(boolean show, String... title) {
        getRootViewDelegate().showProgressDialog(show, title);
    }

    /**
     * 打印日志
     *
     * @param obj
     */
    @Override
    public void log(Object obj) {
        getRootViewDelegate().log(obj);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.empty_retry_view || id == R.id.error_retry_view || id == R.id.no_network_retry_view) {
            onRetryGetData();
        }
    }

    /**
     * 重新获取数据
     */
    protected void onRetryGetData() {

    }

    @Override
    public void showStatusView(MyMultipleStatusView.Config config) {
        getRootViewDelegate().showStatusView(config);
    }

}
