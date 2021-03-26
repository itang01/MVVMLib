package com.itang.mvvm.jetpack.t2vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.classic.common.MultipleStatusView;
import com.itang.mvvm.binding.command.BindingAction;
import com.itang.mvvm.binding.command.BindingCommand;
import com.itang.mvvm.bus.event.TitleBarEvent;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.fragment.base.BaseTargetFragment;
import com.itang.mvvm.jetpack.t3respository.BaseRespository;
import com.itang.mvvm.jetpack.t4model.bean.IList;
import com.itang.mvvm.rx.ResponseThrowable;
import com.itang.mvvm.utils.L;
import com.itang.mvvm.widgit.MyMultipleStatusView;
import com.trello.rxlifecycle4.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * BaseTargetFragment 对应的 ViewModel，即目标视图对应的 ViewModel
 * 目标视图的解释，请看 DOC.md 文件（目标视图：意思是标题栏以下的视图内容（即二次封装后的 ContentLayout 视图））
 * 具备数据管理 和 UI 控制的 ViewModel
 *
 */
public class BaseTargetViewModel<C extends Config> extends BaseViewModelAbs {

    /**
     * 外部传递进来的 {@link BaseUiViewModel} (带有 标题栏 和 多状态视图 的 ViewModel)
     * 通过持有 BaseUiViewModel，使 BaseTargetViewModel 具备管理 标题栏 和 多状态视图 的功能
     */
    private BaseUiViewModel baseUiViewModel;
    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     */
    private CompositeDisposable mCompositeDisposable;
    /**
     * 弱引用持有 LifecycleProvider
     */
    private WeakReference<LifecycleProvider> lifecycle;
    /**
     * 外部页面配置 LiveData
     */
    private FixedLiveData<C> configLiveData;
    /**
     * 分页相关
     */
    private FixedLiveData<Void> onRefreshFinishLiveData;
    public static final int DEFAULT_LIMIT = 10;
    public int totalPages = 1;
    public int page = 1;
    public int limit = DEFAULT_LIMIT;
    private FixedLiveData<Boolean> isRefreshByHand;//是否手动刷新
    public boolean isRefresh = true;
    public boolean isLoadMore = false;
    public boolean isGetData = false;

    public BaseTargetViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void init() {
        super.init();
        getData(true);
    }

    /**
     * BaseTargetFragment 不需要 UILiveData，如果需要 改变 ui，调用 getTitleBarViewModel()
     *
     * @return
     */
    @Override
    public BaseUiViewModel.UILiveData getBaseUiLiveData() {
        if (getBaseUiViewModel() != null) {
            return getBaseUiViewModel().getBaseUiLiveData();
        }
        return null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (lifecycle != null) {
            lifecycle.clear();
            lifecycle = null;
        }
        baseUiViewModel = null;
    }

    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    //下拉刷新
    public void onRefresh() {
        this.isRefresh = true;
    }

    //上拉加载
    public void onLoadMore() {
        this.isLoadMore = true;
    }

    //上拉加载
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onRefresh();
        }
    });
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onLoadMore();
        }
    });

    /**
     * 获取 BaseUiViewModel
     *
     * @return
     */
    public BaseUiViewModel getBaseUiViewModel() {
        return baseUiViewModel;
    }

    /**
     * 将 BaseUiViewModel 传递来，用于控制基础 UI
     *
     * @param baseUiViewModel
     * @return
     */
    public BaseTargetViewModel<C> setBaseUiViewModel(BaseUiViewModel baseUiViewModel) {
        this.baseUiViewModel = baseUiViewModel;
        baseUiViewModel.setChild(this);
        return this;
    }

    /**
     * 获取外部 Config LiveData（实际为 C 泛型类型）
     *
     * @return
     */
    public FixedLiveData<C> getConfigLiveData() {
        if (configLiveData == null) {
            configLiveData = new FixedLiveData<>();
        }
        return configLiveData;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        getBaseUiViewModel().centerTextField.set(title);
    }

    public C getConfig() {
        return getConfigLiveData().getValue();
    }

    /**
     * 将 View（Activity 和 Fragment 中的 Config -> {@link BaseTargetFragment#getOuterConfig()}）
     * 传递进 ViewModel 中
     *
     * @param config
     */
    public void setConfigLiveData(Config config) {
        FixedLiveData configLiveData = getConfigLiveData();
        configLiveData.setValue(config);
    }

    public FixedLiveData getOnRefreshFinishLiveData() {
        if (onRefreshFinishLiveData == null) {
            onRefreshFinishLiveData = new FixedLiveData<>();
        }
        return onRefreshFinishLiveData;
    }

    public FixedLiveData<Boolean> getIsRefreshByHand() {
        if (isRefreshByHand == null) {
            isRefreshByHand = new FixedLiveData<>();
            isRefreshByHand.setValue(false);
        }
        return isRefreshByHand;
    }

    /**
     * 获取 LifecycleProvider
     *
     * @return LifecycleProvider
     */
    public <Event> LifecycleProvider<Event> getLifecycle() {
        return lifecycle.get();
    }

    @Override
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    @Override
    public void log(Object obj) {
        if (obj instanceof String) {
            L.d((String) obj);
        } else {
            L.object(obj);
        }
    }

    @Override
    public void tip(String message) {
        getBaseUiLiveData().getShowTipLiveData().setValue(message);
    }

    @Override
    public void getData(boolean isRefresh) {
        this.isRefresh = isRefresh;
        if (this.isRefresh) {
            page = 1;
        }
    }

    @Override
    public void onTitleBarClick(TitleBarEvent titleBarEvent) {
        if (titleBarEvent.onLeftClick) {
            onLeftViewClick();
        } else if (titleBarEvent.onCenterClick) {
            onCenterClick();
        } else if (titleBarEvent.onMenu1Click) {
            onMenu1Click();
        } else if (titleBarEvent.onMenu2Click) {
            onMenu2Click();
        }
    }

    @Override
    public void showStatusView(MyMultipleStatusView.Config config) {
        getBaseUiViewModel().showStatusView(config);
    }

    /**
     * 标题栏左边按钮被点击
     */
    public void onLeftViewClick() {
        finish();
    }

    public void onCenterClick() {
        getBaseUiLiveData().getCenterViewClickLiveData().call();
    }

    protected void onMenu1Click() {
        getBaseUiLiveData().getOnMenu1ClickLiveData().call();
    }

    protected void onMenu2Click() {
        getBaseUiLiveData().getOnMenu2ClickLiveData().call();
    }

    public void finish() {
        getBaseUiLiveData().getFinishLiveData().call();
    }

    /**
     * 带 BaseViewModel 和 可取消订阅的 观察者
     *
     * @param <T>
     */
    public class DisposableSubscriber<T> extends io.reactivex.rxjava3.subscribers.DisposableSubscriber<T> {

        public boolean isNeed2ShowErrorStatusView = false;

        public DisposableSubscriber() {
        }

        public DisposableSubscriber(boolean isNeed2ShowErrorStatusView) {
            this.isNeed2ShowErrorStatusView = isNeed2ShowErrorStatusView;
        }

        @Override
        public void onNext(T data) {
            isGetData = true;
            if (getBaseUiViewModel() != null) {
                getBaseUiViewModel().showProgressDialog(false);
            }
            if (isRefresh) {
                getOnRefreshFinishLiveData().call();
            }
            getBaseUiLiveData().getShowContentViewLiveData().call();
            if (data instanceof IList) {
                onNextList((IList) data);
            }
        }

        @Override
        public void onError(Throwable t) {
            if (getBaseUiViewModel() != null) {
                getBaseUiViewModel().showProgressDialog(false);
                if (t instanceof ResponseThrowable) {
                    ResponseThrowable throwable = (ResponseThrowable) t;
                    String message = (throwable).message;
                    getBaseUiViewModel().tip(message);
                    if (isNeed2ShowErrorStatusView) {
                        showStatusView(new MyMultipleStatusView.Config(MultipleStatusView.STATUS_ERROR, message));
                    }
                    onError(throwable.code, throwable.message);
                }
            }
        }

        public void onError(int code, String message) {

        }

        @Override
        public void onComplete() {
            isRefreshByHand.setValue(false);
            isRefresh = false;
            isLoadMore = false;
        }
    }

    /**
     * 该方法处理列表公用逻辑
     *
     * @param list
     */
    protected void onNextList(IList list) {

    }

}
