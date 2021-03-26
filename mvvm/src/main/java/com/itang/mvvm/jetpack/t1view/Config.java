package com.itang.mvvm.jetpack.t1view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.LayoutRes;
import androidx.databinding.BaseObservable;

import com.itang.mvvm.R;

/**
 * 页面配置类
 * <p>
 * 注：新增字段需要重新 implements Parcelable
 */
public class Config extends BaseObservable implements Parcelable, Cloneable, IViewConfig {

    public static final int TYPE_CUSTOM_VIEW = 3;
    public static final int TYPE_RIGHT_CUSTOM_VIEW = 4;
    public static final int TYPE_IMAGEBUTTON = 2;
    public static final int TYPE_TEXTVIEW = 1;
    public static final int TYPE_NONE = 0;

    public String fragmentPath;//Fragment 的 Route 路径
    public int variableId;//ViewModel 对应的 id
    public int targetViewVariableId;//targetViewViewModel 对应的 id
    public Class viewModelClass;//ViewModel 对应的 Class，不参与 Parcelable
    public Class targetViewModelClass;//targetViewViewModel 对应的 Class，不参与 Parcelable
    public String title = "";//标题
    @LayoutRes
    public int contentViewLayout = R.layout.mvvm_fragment_base;// BaseFragment 类及其子类中有效 start...
    @LayoutRes
    public int targetViewLayout = 0;// targetView.addView(布局资源id)

    public int navBackIcon = R.mipmap.btn_bar_back;//返回按钮id
    public boolean isSwipeBackEnable = true;//是否可以侧滑返回
    public boolean isShowBackBtn = true;//是否显示标题栏返回按钮
    public boolean isNeedToolbarPadding;//是否需要设置顶部标题栏的marginTop=StatusBarHeight
    public boolean isHadDefaultToolbar = true;//是否有标题栏（自定义）
    public boolean isHadMenu1;//是否有标题栏按钮1
    public boolean isHadMenu2;//是否有标题栏按钮2
    public boolean isKeyboardVisibilityListener;//是否需要设置软键盘弹出和消失时的监听
    public int leftType = TYPE_IMAGEBUTTON;
    public int menu1Type = TYPE_NONE;
    public int menu2Type = TYPE_NONE;
    private int menu1ActionType;
    
    public int menu1Icon = 0;//标题栏按钮1的图标
    
    public int menu2Icon = 0;//标题栏按钮2的图标
    public String menu1Text = "";//标题栏按钮1的文字
    public String menu2Text = "";//标题栏按钮2的文字
    public boolean isNeedBottomBtn;//是否添加底部按钮
    public boolean isRefreshEnable;// BaseFragment 类及其子类中有效 ...end
    public boolean isLoadMoreEnable = true;// BaseRecyclerViewFragment 类及其子类中有效
    public boolean isList;// BaseRecyclerViewFragment 类及其子类中有效
    public boolean isNeedSearchBar = true;// 是否需要搜索View，CommonRecyclerViewWithFilterBarFragment 中有效
    public boolean isNeedSetRecyclerViewOnItemClickListener = true;//是否需要设置 RecyclerView Item 和 Item下的子按钮的点击事件
    public boolean isNeedRvFilterCondition;// 是否需要筛选条件View，CommonTabViewPagerWithFilterConditionFragment 中有效
    public boolean isNeedConditionRemoveShowWaitDialog = true;// 是否在移除筛选条件时显示加载框，CommonTabViewPagerWithFilterConditionFragment 中有效
    public String showLodingMsg = "加载中...";//页面打开时，加载进度下面的提示语
    public boolean isOnStartShowLoading = false;//页面打开时，是否显示页面内的加载进度
    public boolean isUseDataBinding;//是否使用DataBinding

    public Config() {
    }

    public int getLeftType() {
        return leftType;
    }

    public Config setLeftType(int leftType) {
        this.leftType = leftType;
        return this;
    }

    public int getMenu1ActionType() {
        return menu1ActionType;
    }

    public Config setMenu1ActionType(int menu1ActionType) {
        this.menu1ActionType = menu1ActionType;
        return this;
    }

    public int getMenu1Type() {
        return menu1Type;
    }

    public Config setMenu1Type(int menu1Type) {
        this.menu1Type = menu1Type;
        return this;
    }

    public int getMenu2Type() {
        return menu2Type;
    }

    public Config setMenu2Type(int menu2Type) {
        this.menu2Type = menu2Type;
        return this;
    }

    public Class getViewModelClass() {
        return viewModelClass;
    }

    public int getContentViewLayout() {
        return contentViewLayout;
    }

    public Config setContentViewLayout(int contentViewLayout) {
        this.contentViewLayout = contentViewLayout;
        return this;
    }

    public Config setTargetViewLayout(@LayoutRes int targetViewLayout) {
        this.targetViewLayout = targetViewLayout;
        return this;
    }

    public int getTargetViewLayout() {
        return targetViewLayout;
    }

    public int getTargetViewVariableId() {
        return targetViewVariableId;
    }

    public Config setTargetViewVariableId(int targetViewVariableId) {
        this.targetViewVariableId = targetViewVariableId;
        return this;
    }

    public Class getTargetViewModelClass() {
        return targetViewModelClass;
    }

    public Config setTargetViewModelClass(Class targetViewModelClass) {
        this.targetViewModelClass = targetViewModelClass;
        return this;
    }

    public Config setViewModelClass(Class viewModelClass) {
        this.viewModelClass = viewModelClass;
        return this;
    }

    public String getFragmentPath() {
        return fragmentPath;
    }

    public Config setFragmentPath(String fragmentPath) {
        this.fragmentPath = fragmentPath;
        return this;
    }

    public Config setShowBackBtn(boolean showBackBtn) {
        this.isShowBackBtn = showBackBtn;
        return this;
    }


    public int getVariableId() {
        return variableId;
    }

    public Config setVariableId(int variableId) {
        this.variableId = variableId;
        return this;
    }

    public static Config getInstance() {
        return new Config();
    }

    public Config setUseDataBinding(boolean useDataBinding) {
        isUseDataBinding = useDataBinding;
        return this;
    }

    public Config setOnStartShowLoading(boolean onStartShowLoading) {
        isOnStartShowLoading = onStartShowLoading;
        return this;
    }

    public Config setKeyboardVisibilityListener(boolean keyboardVisibilityListener) {
        isKeyboardVisibilityListener = keyboardVisibilityListener;
        return this;
    }

    public Config setTitle(String title) {
        this.title = title;
        return this;
    }

    public Config setHadDefaultToolbar(boolean hadDefaultToolbar) {
        isHadDefaultToolbar = hadDefaultToolbar;
        return this;
    }

    public Config setNeedSearchBar(boolean needSearchBar) {
        isNeedSearchBar = needSearchBar;
        return this;
    }

    public Config setList(boolean list) {
        isList = list;
        return this;
    }

    public Config setNavBackIcon(int navBackIcon) {
        this.navBackIcon = navBackIcon;
        return this;
    }

    public Config setSwipeBackEnable(boolean swipeBackEnable) {
        isSwipeBackEnable = swipeBackEnable;
        return this;
    }

    public Config setNeedToolbarPadding(boolean needToolbarPadding) {
        isNeedToolbarPadding = needToolbarPadding;
        return this;
    }

    public Config setHadMenu1(boolean hadMenu1) {
        isHadMenu1 = hadMenu1;
        return this;
    }

    public Config setHadMenu2(boolean hadMenu2) {
        isHadMenu2 = hadMenu2;
        return this;
    }

    public Config setShowLodingMsg(String showLodingMsg) {
        this.showLodingMsg = showLodingMsg;
        return this;
    }

    public Config setMenu1Icon(int menu1Icon) {
        this.menu1Icon = menu1Icon;
        return this;
    }

    public Config setMenu2Icon(int menu2Icon) {
        this.menu2Icon = menu2Icon;
        return this;
    }

    public Config setMenu1Text(String menu1Text) {
        this.menu1Text = menu1Text;
        return this;
    }

    public Config setMenu2Text(String menu2Text) {
        this.menu2Text = menu2Text;
        return this;
    }

    public Config setNeedBottomBtn(boolean needBottomBtn) {
        isNeedBottomBtn = needBottomBtn;
        return this;
    }

    public Config setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
        return this;
    }

    public Config setLoadMoreEnable(boolean loadMoreEnable) {
        isLoadMoreEnable = loadMoreEnable;
        return this;
    }

    public Config setNeedSetRecyclerViewOnItemClickListener(boolean needSetRecyclerViewOnItemClickListener) {
        isNeedSetRecyclerViewOnItemClickListener = needSetRecyclerViewOnItemClickListener;
        return this;
    }

    public Config setNeedRvFilterCondition(boolean needRvFilterCondition) {
        isNeedRvFilterCondition = needRvFilterCondition;
        return this;
    }

    public Config setNeedConditionRemoveShowWaitDialog(boolean needConditionRemoveShowWaitDialog) {
        isNeedConditionRemoveShowWaitDialog = needConditionRemoveShowWaitDialog;
        return this;
    }

    @Override
    public Object clone() {
        Config config = null;
        try {
            config = (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return config;
    }

    public Config obtain() {
        return (Config) clone();
    }

    @Override
    public boolean isOnStartShowLoading() {
        return isOnStartShowLoading;
    }

    //如果Config没有CREATE会报错：
    //android.os.BadParcelableException: Parcelable protocol requires a Parcelable.Creator object
    //called CREATOR on class mvp.view.config.Config

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fragmentPath);
        dest.writeInt(this.variableId);
        dest.writeInt(this.targetViewVariableId);
        dest.writeSerializable(this.viewModelClass);
        dest.writeSerializable(this.targetViewModelClass);
        dest.writeString(this.title);
        dest.writeInt(this.contentViewLayout);
        dest.writeInt(this.targetViewLayout);
        dest.writeInt(this.navBackIcon);
        dest.writeByte(this.isSwipeBackEnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowBackBtn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNeedToolbarPadding ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHadDefaultToolbar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHadMenu1 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHadMenu2 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isKeyboardVisibilityListener ? (byte) 1 : (byte) 0);
        dest.writeInt(this.leftType);
        dest.writeInt(this.menu1Type);
        dest.writeInt(this.menu2Type);
        dest.writeInt(this.menu1ActionType);
        dest.writeInt(this.menu1Icon);
        dest.writeInt(this.menu2Icon);
        dest.writeString(this.menu1Text);
        dest.writeString(this.menu2Text);
        dest.writeByte(this.isNeedBottomBtn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRefreshEnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLoadMoreEnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isList ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNeedSearchBar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNeedSetRecyclerViewOnItemClickListener ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNeedRvFilterCondition ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNeedConditionRemoveShowWaitDialog ? (byte) 1 : (byte) 0);
        dest.writeString(this.showLodingMsg);
        dest.writeByte(this.isOnStartShowLoading ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isUseDataBinding ? (byte) 1 : (byte) 0);
    }

    protected Config(Parcel in) {
        this.fragmentPath = in.readString();
        this.variableId = in.readInt();
        this.targetViewVariableId = in.readInt();
        this.viewModelClass = (Class) in.readSerializable();
        this.targetViewModelClass = (Class) in.readSerializable();
        this.title = in.readString();
        this.contentViewLayout = in.readInt();
        this.targetViewLayout = in.readInt();
        this.navBackIcon = in.readInt();
        this.isSwipeBackEnable = in.readByte() != 0;
        this.isShowBackBtn = in.readByte() != 0;
        this.isNeedToolbarPadding = in.readByte() != 0;
        this.isHadDefaultToolbar = in.readByte() != 0;
        this.isHadMenu1 = in.readByte() != 0;
        this.isHadMenu2 = in.readByte() != 0;
        this.isKeyboardVisibilityListener = in.readByte() != 0;
        this.leftType = in.readInt();
        this.menu1Type = in.readInt();
        this.menu2Type = in.readInt();
        this.menu1ActionType = in.readInt();
        this.menu1Icon = in.readInt();
        this.menu2Icon = in.readInt();
        this.menu1Text = in.readString();
        this.menu2Text = in.readString();
        this.isNeedBottomBtn = in.readByte() != 0;
        this.isRefreshEnable = in.readByte() != 0;
        this.isLoadMoreEnable = in.readByte() != 0;
        this.isList = in.readByte() != 0;
        this.isNeedSearchBar = in.readByte() != 0;
        this.isNeedSetRecyclerViewOnItemClickListener = in.readByte() != 0;
        this.isNeedRvFilterCondition = in.readByte() != 0;
        this.isNeedConditionRemoveShowWaitDialog = in.readByte() != 0;
        this.showLodingMsg = in.readString();
        this.isOnStartShowLoading = in.readByte() != 0;
        this.isUseDataBinding = in.readByte() != 0;
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel source) {
            return new Config(source);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };
}
