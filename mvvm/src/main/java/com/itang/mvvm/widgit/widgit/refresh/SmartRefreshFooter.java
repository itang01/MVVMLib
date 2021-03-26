package com.itang.mvvm.widgit.widgit.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itang.mvvm.R;
import com.itang.mvvm.utils.L;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * {@link com.scwang.smartrefresh.layout.SmartRefreshLayout 尾部布局}
 */
@SuppressLint("RestrictedApi")
public class SmartRefreshFooter extends LinearLayout implements RefreshFooter {

    private TextView tvMessage;
    private ProgressBar mProgressBar;
    private ImageView mProgressBarStatic;

    protected boolean noMoreData = false;

    public SmartRefreshFooter(Context context) {
        this(context, null);
    }

    public SmartRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.smart_refresh_footer, this, true);
        tvMessage = findViewById(R.id.tvMessage);
        mProgressBar = findViewById(R.id.progressBar2);
        mProgressBarStatic = findViewById(R.id.ivStaticProgress);
        showProgressBar(false);
    }

    private void showProgressBar(boolean show) {
        tvMessage.setText(getContext().getString(R.string.loading));
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBarStatic.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    public void onlyShowMessage() {
        mProgressBar.setVisibility(View.GONE);
        mProgressBarStatic.setVisibility(View.GONE);
        tvMessage.setText("没有更多数据~");
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        showProgressBar(false);
        return 0;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!noMoreData) {
            L.d("footer >>> newState = " + newState);
            switch (newState) {
                case Loading:
                    showProgressBar(true);
                    break;
                case PullUpToLoad:
                case LoadReleased:
                case ReleaseToLoad:
                case None:
                case Refreshing:
                    showProgressBar(false);
                    break;
            }
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (this.noMoreData != noMoreData) {
            this.noMoreData = noMoreData;
            if (noMoreData) {
                onlyShowMessage();
            } else {
                tvMessage.setText(getContext().getString(R.string.loading));
                showProgressBar(false);
            }
        }
        return true;
    }
}
