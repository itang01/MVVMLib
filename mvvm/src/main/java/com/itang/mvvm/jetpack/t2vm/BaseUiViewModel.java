package com.itang.mvvm.jetpack.t2vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.classic.common.MultipleStatusView;
import com.itang.mvvm.R;
import com.itang.mvvm.binding.command.BindingAction;
import com.itang.mvvm.binding.command.BindingCommand;
import com.itang.mvvm.bus.event.TitleBarEvent;
import com.itang.mvvm.utils.MVVMUtils;
import com.itang.mvvm.utils.ObjectUtils;
import com.itang.mvvm.widgit.MyMultipleStatusView;
import com.trello.rxlifecycle4.LifecycleProvider;

import com.itang.mvvm.utils.StringUtils;

import java.util.Map;

import static com.itang.mvvm.jetpack.t1view.Config.TYPE_IMAGEBUTTON;
import static com.itang.mvvm.jetpack.t1view.Config.TYPE_NONE;

/**
 * ViewModel 基类
 */
public class BaseUiViewModel extends BaseViewModelAbs {

    private UILiveData uiLiveData;
    /**
     * BaseUiViewModel 的 子类对象引用，目前作用：回调标题栏点击事件给其子类
     */
    private BaseViewModelAbs child;

    public BaseUiViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void injectLifecycleProvider(LifecycleProvider<?> lifecycleProvider) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (uiLiveData != null) {
            uiLiveData = null;
        }
    }

    /**
     * 获取和 UI 关联的 LiveData
     *
     * @return uiLiveData UI关联的 LiveData
     */
    public UILiveData getBaseUiLiveData() {
        if (ObjectUtils.isNull(uiLiveData)) {
            uiLiveData = new UILiveData();
        }
        return uiLiveData;
    }

    /**
     * 显示等待加载对话框
     *
     * @param show
     * @param title
     */
    public void showProgressDialog(boolean show, String... title) {
        if (getBaseUiLiveData() == null) {
            return;
        }
        if (show) {
            getBaseUiLiveData().showProgressDialogLiveData.postValue(title != null && title.length > 0 ? title[0] : "");
        } else {
            getBaseUiLiveData().dismissProgressDialogLiveData.call();
        }
    }

    /**
     * 点击左边视图
     */
    public BindingCommand onLeftViewClickCommand = new BindingCommand(this::onLeftViewClick);

    /**
     * 标题栏左边按钮被点击
     */
    public void onLeftViewClick() {
        if (child == null) return;
        child.onTitleBarClick(new TitleBarEvent().setOnLeftClick(true));
    }

    /**
     * 点击中间视图
     */
    public BindingCommand onCenterViewClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (child == null) return;
            child.onTitleBarClick(new TitleBarEvent().setOnCenterClick(true));
        }
    });

    /**
     * 点击右边视图
     */
    public BindingCommand onMenu1ClickCommand = new BindingCommand(this::onMenu1Click);
    /**
     * 点击右边视图
     */
    public BindingCommand onMenu2ClickCommand = new BindingCommand(this::onMenu2Click);

    /**
     * 标题栏最右边的menu1按钮点击
     */
    public void onMenu1Click() {
        if (child == null) return;
        child.onTitleBarClick(new TitleBarEvent().setOnMenu1Click(true));
    }

    /**
     * 标题栏右边的menu2按钮点击
     */
    public void onMenu2Click() {
        if (child == null) return;
        child.onTitleBarClick(new TitleBarEvent().setOnMenu2Click(true));
    }

    @Override
    public void tip(String message) {
        getBaseUiLiveData().getShowTipLiveData().setValue(message);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        centerTextField.set(title);
    }

    public void setChild(BaseViewModelAbs child) {
        this.child = child;
    }

    /**
     * 显示状态视图
     *
     * @param config
     */
    public void showStatusView(MyMultipleStatusView.Config config) {
        if (getBaseUiLiveData() == null) {
            return;
        }
        if (config.status == MultipleStatusView.STATUS_LOADING) {
            //显示加载中视图
            getBaseUiLiveData().getShowLoadingViewLiveData().call();
        } else if (config.status == MultipleStatusView.STATUS_EMPTY) {
            //显示空视图
            getBaseUiLiveData().getShowEmptyViewLiveData().setValue(config);
        } else if (config.status == MultipleStatusView.STATUS_ERROR) {
            //显示错误视图
            if (!StringUtils.isEmpty(config.msg)) {
                getBaseUiLiveData().getShowErrorViewLiveData().setValue(config.msg);
            } else {
                getBaseUiLiveData().getShowErrorViewLiveData().call();
            }
        } else if (config.status == MultipleStatusView.STATUS_NO_NETWORK) {
            //显示无网络视图
            getBaseUiLiveData().getShowNetworkErrorLiveData().call();
        } else if (config.status == MultipleStatusView.STATUS_CONTENT) {
            //显示内容视图
            getBaseUiLiveData().getShowContentViewLiveData().call();
        }
    }

    public ObservableField<Boolean> getShowTitleBarField() {
        showTitleBarField = MVVMUtils.createObservableField(showTitleBarField, true);
        return showTitleBarField;
    }

    //TODO 这么多变量和对应的get方法，不太优雅，优化：改用Map进行统一管理
    //标题栏相关
    public ObservableField<Boolean> showTitleBarField = getShowTitleBarField();
    public ObservableField<Integer> leftTypeField = new ObservableField<>(TYPE_IMAGEBUTTON);
    public ObservableField<String> centerTextField = new ObservableField<>("标题");
    public ObservableField<Integer> menu1TypeField = new ObservableField<>(TYPE_NONE);
    public ObservableField<String> menu1TextField = new ObservableField<>("menu1");
    public ObservableField<Integer> menu1ImageResourceField = new ObservableField<>(R.mipmap.btn_history);
    public ObservableField<Integer> menu2TypeField = new ObservableField<>(TYPE_NONE);
    public ObservableField<String> menu2TextField = new ObservableField<>("menu2");
    public ObservableField<Integer> menu2ImageResourceField = new ObservableField<>(R.mipmap.btn_history);

    /**
     * 观察 UI 的 LiveData
     */
    public static final class UILiveData extends FixedLiveData {

        //TODO 这么多变量和对应的get方法，不太优雅，优化：改用Map进行统一管理
        //多状态视图相关
        private FixedLiveData<MyMultipleStatusView.Config> showEmptyView;
        private FixedLiveData<String> showErrorView;
        private FixedLiveData<String> showLoadingView;
        private FixedLiveData<String> showNetworkErrorView;
        private FixedLiveData<String> showContentView;
        //其他
        private FixedLiveData<String> showProgressDialogLiveData;
        private FixedLiveData<String> showTip;
        private FixedLiveData<Void> dismissProgressDialogLiveData;
        private FixedLiveData<Map<String, Object>> startViewLiveData;
        private FixedLiveData<Void> finishLiveData;
        private FixedLiveData<Void> onCenterViewClickLiveData;
        private FixedLiveData<Void> onMenu1ClickLiveData;
        private FixedLiveData<Void> onMenu2ClickLiveData;
        private FixedLiveData<Boolean> showSoftKeyboard;

        public FixedLiveData<Boolean> getShowSoftKeyboard() {
            return showSoftKeyboard = createLiveData(showSoftKeyboard);
        }

        public FixedLiveData<Void> getCenterViewClickLiveData() {
            return onCenterViewClickLiveData = createLiveData(onCenterViewClickLiveData);
        }

        public FixedLiveData<Void> getOnMenu1ClickLiveData() {
            return onMenu1ClickLiveData = createLiveData(onMenu1ClickLiveData);
        }

        public FixedLiveData<Void> getOnMenu2ClickLiveData() {
            return onMenu2ClickLiveData = createLiveData(onMenu2ClickLiveData);
        }

        public FixedLiveData<String> getShowContentViewLiveData() {
            return showContentView = createLiveData(showContentView);
        }

        public FixedLiveData<String> getShowNetworkErrorLiveData() {
            return showNetworkErrorView = createLiveData(showNetworkErrorView);
        }

        public FixedLiveData<String> getShowLoadingViewLiveData() {
            return showLoadingView = createLiveData(showLoadingView);
        }

        public FixedLiveData<String> getShowErrorViewLiveData() {
            return showErrorView = createLiveData(showErrorView);
        }

        public FixedLiveData<MyMultipleStatusView.Config> getShowEmptyViewLiveData() {
            showEmptyView = createLiveData(showEmptyView);
            return showEmptyView;
        }

        public FixedLiveData<String> getShowTipLiveData() {
            return showTip = createLiveData(showTip);
        }

        public FixedLiveData<String> getShowProgressDialogLiveData() {
            return showProgressDialogLiveData = createLiveData(showProgressDialogLiveData);
        }

        public FixedLiveData<Void> getDismissProgressDialogLiveData() {
            return dismissProgressDialogLiveData = createLiveData(dismissProgressDialogLiveData);
        }

        public FixedLiveData<Map<String, Object>> getStartViewLiveData() {
            return startViewLiveData = createLiveData(startViewLiveData);
        }

        public FixedLiveData<Void> getFinishLiveData() {
            return finishLiveData = createLiveData(finishLiveData);
        }

        protected <K> FixedLiveData<K> createLiveData(FixedLiveData<K> liveData) {
            if (liveData == null) {
                liveData = new FixedLiveData<>();
            }
            return liveData;
        }
    }
}
