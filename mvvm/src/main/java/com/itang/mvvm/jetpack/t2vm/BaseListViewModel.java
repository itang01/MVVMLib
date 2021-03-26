package com.itang.mvvm.jetpack.t2vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.classic.common.MultipleStatusView;
import com.itang.mvvm.R;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t3respository.BaseRespository;
import com.itang.mvvm.jetpack.t4model.bean.IList;
import com.itang.mvvm.utils.CollectionUtils;
import com.itang.mvvm.widgit.MyMultipleStatusView;

import java.util.ArrayList;
import java.util.List;


/**
 * 列表 ViewModel 基类（具备功能：下拉刷新，加载更多，分页，item点击）
 *
 */
public abstract class BaseListViewModel<C extends Config>
        extends BaseTargetViewModel<C> {

    //TODO
    private List items = new ArrayList();

    //上拉加载
    private FixedLiveData<Void> onLoadMoreFinishLiveData;
    //上拉加载到最后一页
    private FixedLiveData<Boolean> onLoadMoreEndLiveData;

    private FixedLiveData<Boolean> restoreRecyclerViewInstanceStateLiveData;

    public BaseListViewModel(@NonNull Application application) {
        super(application);
    }

    public FixedLiveData getOnLoadMoreFinishLiveData() {
        if (onLoadMoreFinishLiveData == null) {
            onLoadMoreFinishLiveData = new FixedLiveData<>();
        }
        return onLoadMoreFinishLiveData;
    }


    public FixedLiveData<Boolean> getOnLoadMoreEndLiveData() {
        if (onLoadMoreEndLiveData == null) {
            onLoadMoreEndLiveData = new FixedLiveData<>();
        }
        return onLoadMoreEndLiveData;
    }

    @Override
    public void init() {
        boolean isFirstInit = true;
        getData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGetData) {
            int size = items.size();
            limit = Math.max(limit, size + 1);
            page = 1;
            getData(true);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getIsRefreshByHand().setValue(true);
        page = 1;
        getData(true);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getData(false);
    }

    @Override
    protected void onNextList(IList list) {
        if (isRefresh || page == 1) {
            items.clear();
            getOnLoadMoreEndLiveData().setValue(false);
        }
        if (isLoadMore) {
            getOnLoadMoreFinishLiveData().call();
        }
        totalPages = list.getTotalPages();
        int size = !CollectionUtils.isEmpty(list.getDataList()) ? list.getDataList().size() : 0;
        if ((isRefresh || isLoadMore) && (page >= totalPages || totalPages == 1 || size < limit)) {
            getOnLoadMoreEndLiveData().setValue(true);
        }
        page = Math.min(++page, totalPages);
        if (isRefresh && CollectionUtils.isEmpty(list.getDataList())) {
            showEmptyStatusView();
        }
        limit = DEFAULT_LIMIT;
    }

    protected void showEmptyStatusView() {
        showStatusView(new MyMultipleStatusView.Config(MultipleStatusView.STATUS_EMPTY, getEmptyViewResId(), getEmptyMessage()));
    }

    /**
     * 恢复RecyclerView的状态（滚动位置）
     */
    protected void restoreRecyclerViewInstanceState() {
        if (isRefresh && !getIsRefreshByHand().getValue()) {
            getRestoreRecyclerViewInstanceStateLiveData().call();
        }
        getIsRefreshByHand().setValue(false);
    }

    /**
     * 空数据时显示的icon
     *
     * @return
     */
    protected int getEmptyViewResId() {
        return R.mipmap.icon_no_data;
    }

    /**
     * 空数据时显示的message
     *
     * @return
     */
    protected String getEmptyMessage() {
        return "暂无内容";
    }

    public FixedLiveData<Boolean> getRestoreRecyclerViewInstanceStateLiveData() {
        if (restoreRecyclerViewInstanceStateLiveData == null) {
            restoreRecyclerViewInstanceStateLiveData = new FixedLiveData();
        }
        return restoreRecyclerViewInstanceStateLiveData;
    }
}
