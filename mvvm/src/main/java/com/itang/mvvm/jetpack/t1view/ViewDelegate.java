package com.itang.mvvm.jetpack.t1view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStoreOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.itang.mvvm.BaseApp;
import com.itang.mvvm.MVVMConstants;
import com.itang.mvvm.databinding.MvvmFragmentBaseBinding;
import com.itang.mvvm.jetpack.t2vm.BaseTargetViewModel;
import com.itang.mvvm.jetpack.t2vm.BaseViewModelAbs;
import com.itang.mvvm.utils.DialogUtils;
import com.itang.mvvm.utils.KeyBoardUtils;
import com.itang.mvvm.utils.L;
import com.itang.mvvm.utils.MVVMUtils;
import com.itang.mvvm.widgit.MyMultipleStatusView;
import com.itang.mvvm.widgit.ProgressDialog;
import com.trello.rxlifecycle4.LifecycleProvider;

import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Activity 和 Fragment 的代理类
 *
 * @param <Binding>
 * @param <ViewModel>
 */
public class ViewDelegate<Binding extends ViewDataBinding, ViewModel extends BaseViewModelAbs> implements IBaseView {

    private Context context;
    private SupportActivity activity;
    private SupportFragment fragment;

    private Binding binding;
    private ViewModel viewModel;
    private int variableId;
    private Class viewModelClass;
    private Lifecycle lifecycle;
    private LifecycleOwner lifecycleOwner;
    private LifecycleProvider lifecycleProvider;
    private ViewModelStoreOwner viewModelStoreOwner;
    //等待进度条对话框
    private ProgressDialog progressDialog;//wait dialog

    public ViewDelegate(Context context) {
        this.context = context;
        this.activity = (SupportActivity) context;
    }

    @Override
    public void init() {
        //私有的初始化 Databinding 和 ViewModel 方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        //注册RxBus
        getViewModel().registerBus();
    }

    @Override
    public void initParam(Bundle bundle) {

    }

    /**
     * 注：不能在这里获取 viewModel 的 Class，因为 getClass().getGenericSuperclass() 的返回，
     * 表示此 Class所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
     * 初始化 viewModel 的代码，详见 {@link ViewDelegate#initViewDataBinding()}
     */
    public void initViewDataBinding() {
        viewModel = (ViewModel) MVVMUtils.createViewModel(BaseApp.getAppContext(), getViewModelStoreOwner(), getViewModelClass());
        getBinding().setVariable(variableId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        getBinding().setLifecycleOwner(getLifecycleOwner());
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(getLifecycleProvider());
    }

    @Override
    public void registerUIChangeLiveDataCallBack() {
        if (getViewModel().getBaseUiLiveData() == null) {
            return;
        }
        //加载对话框显示
        getViewModel().getBaseUiLiveData().getShowProgressDialogLiveData().observe(getLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String title) {
                showProgressDialog(true, title);
            }
        });
        //加载对话框消失
        getViewModel().getBaseUiLiveData().getDismissProgressDialogLiveData().observe(getLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                showProgressDialog(false);
            }
        });
        //跳入新页面
        getViewModel().getBaseUiLiveData().getStartViewLiveData().observe(getLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Config config = (Config) params.get(MVVMConstants.KEY_OUTER_CONFIG);
                Bundle bundle = new Bundle();
                bundle.putParcelable(MVVMConstants.KEY_OUTER_CONFIG, config);
                ISupportFragment fragment = (ISupportFragment) ARouter.getInstance()
                        .build(config.getFragmentPath())
                        .with(bundle)
                        .navigation();
                getFragment().start(fragment);
            }
        });
        //关闭界面
        getViewModel().getBaseUiLiveData().getFinishLiveData().observe(getLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
        //提示（一般为吐司）
        getViewModel().getBaseUiLiveData().getShowTipLiveData().observe(getLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                tip(message);
            }
        });
        //显示或隐藏软键盘
        getViewModel().getBaseUiLiveData().getShowSoftKeyboard().observe(getLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if (!show) {
                    KeyBoardUtils.hideSoftInput(activity);
                }
            }
        });
        //多布局视图
        if (getBinding() instanceof MvvmFragmentBaseBinding) {
            MvvmFragmentBaseBinding binding = (MvvmFragmentBaseBinding) getBinding();
            //显示空视图
            getViewModel().getBaseUiLiveData().getShowEmptyViewLiveData().observe(getLifecycleOwner(), new Observer<MyMultipleStatusView.Config>() {
                @Override
                public void onChanged(MyMultipleStatusView.Config config) {
                    binding.statusView.setEmptyImageId(config.emptyImageId);
                    binding.statusView.showEmpty(config.msg);
                }
            });
            //显示错误视图
            getViewModel().getBaseUiLiveData().getShowErrorViewLiveData().observe(getLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    binding.statusView.showError();
                }
            });
            //显示加载中视图
            getViewModel().getBaseUiLiveData().getShowLoadingViewLiveData().observe(getLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    binding.statusView.showLoading();
                }
            });
            //显示无网络视图
            getViewModel().getBaseUiLiveData().getShowNetworkErrorLiveData().observe(getLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    binding.statusView.showNoNetwork();
                }
            });
            //显示内容视图
            getViewModel().getBaseUiLiveData().getShowContentViewLiveData().observe(getLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    binding.statusView.showContent();
                }
            });
        }
    }

    @Override
    public void showStatusView(MyMultipleStatusView.Config config) {
        getViewModel().showStatusView(config);
    }

    @Override
    public void onCleared() {
        if (viewModel != null) {
            viewModel.removeBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void tip(String message) {
        //TODO TUtils.showToast(message);
    }

    @Override
    public void showProgressDialog(boolean show, String... title) {
        String message = title != null && title.length > 0 ? title[0] : "";
        if (show) {
            if (progressDialog != null) {
                progressDialog.setMessage(message);
            } else {
                progressDialog = DialogUtils.getWaitDialog(context, message);
            }
            progressDialog.show();
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressedSupport();
    }

    @Override
    public void log(Object obj) {
        if (obj instanceof String) {
            L.d((String) obj);
        } else {
            L.object(obj);
        }
    }

    public Class getViewModelClass() {
        return viewModelClass != null ? viewModelClass : BaseTargetViewModel.class;
    }

    public SupportFragment getFragment() {
        return fragment;
    }

    public ViewDelegate<Binding, ViewModel> setFragment(SupportFragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    public ViewDelegate<Binding, ViewModel> setViewModelClass(Class viewModelClass) {
        this.viewModelClass = viewModelClass;
        return this;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public ViewDelegate<Binding, ViewModel> setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        return this;
    }

    public ViewModelStoreOwner getViewModelStoreOwner() {
        return viewModelStoreOwner;
    }

    public ViewDelegate<Binding, ViewModel> setViewModelStoreOwner(ViewModelStoreOwner viewModelStoreOwner) {
        this.viewModelStoreOwner = viewModelStoreOwner;
        return this;
    }

    public LifecycleProvider<?> getLifecycleProvider() {
        return lifecycleProvider;
    }

    public ViewDelegate<Binding, ViewModel> setLifecycleProvider(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
        return this;
    }

    public SupportActivity getActivity() {
        return activity;
    }

    public ViewDelegate<Binding, ViewModel> setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        return this;
    }

    public ViewDelegate<Binding, ViewModel> setVariableId(int variableId) {
        this.variableId = variableId;
        return this;
    }
}
