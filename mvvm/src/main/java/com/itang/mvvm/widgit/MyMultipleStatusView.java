package com.itang.mvvm.widgit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.classic.common.MultipleStatusView;
import com.itang.mvvm.R;

import java.lang.reflect.Field;

public class MyMultipleStatusView extends MultipleStatusView {
    public MyMultipleStatusView(Context context) {
        super(context);
    }

    public MyMultipleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //mEmptyViewResId
    public void setEmptyImageId(int emptyImageId) {
        try {
            Field mEmptyViewField = MultipleStatusView.class.getDeclaredField("mEmptyView");
            mEmptyViewField.setAccessible(true);
            View emptyView = (View) mEmptyViewField.get(this);
            if (emptyView == null) {
                showEmpty();
                emptyView = (View) mEmptyViewField.get(this);
            }
            ImageView icon = emptyView.findViewById(R.id.icon);
            icon.setImageResource(emptyImageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class Config {
        /**
         * <p>
         * MultipleStatusView.STATUS_LOADING   //当前为加载中视图
         * MultipleStatusView.STATUS_EMPTY     //当前为空视图
         * MultipleStatusView.STATUS_ERROR     //当前为错误视图
         * MultipleStatusView.STATUS_NO_NETWORK//当前为无网络视图
         * MultipleStatusView.STATUS_CONTENT   //当前为内容视图
         *
         * @param status 当前view的状态
         */
        public int status = -1;
        public int emptyImageId = R.mipmap.icon_no_data;
        public String msg = "暂无内容";

        public Config() {
        }

        public Config(int status) {
            this.status = status;
        }

        public Config(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public Config(int status, int emptyImageId, String msg) {
            this.status = status;
            this.emptyImageId = emptyImageId;
            this.msg = msg;
        }

        public Config setStatus(int status) {
            this.status = status;
            return this;
        }

        public Config setEmptyImageId(int emptyImageId) {
            this.emptyImageId = emptyImageId;
            return this;
        }

        public Config setMsg(String msg) {
            this.msg = msg;
            return this;
        }
    }
}
