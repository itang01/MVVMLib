package com.itang.mvvm.jetpack.t1view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.itang.mvvm.MVVMConstants;
import com.itang.mvvm.R;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.utils.RouteUtils;
import com.itang.mvvm.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Objects;

import me.yokeyword.fragmentation.ISupportFragment;


/**
 * 盛装 Fragment 的一个容器(代理) Activity
 * 普通界面只需要编写 Fragment,使用此 Activity 盛装，这样就不需要每个界面都在 AndroidManifest 中注册一遍
 */
@Route(path = MVVMConstants.RouteHub.ContainerActivity)
public class ContainerActivity extends BaseRxActivity {
    private static final String FRAGMENT_TAG = "content_fragment_tag";
    protected WeakReference<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvvm_activity_container);
        FragmentManager fm = getSupportFragmentManager();
        ISupportFragment fragment = null;
        if (savedInstanceState != null) {
            fragment = (ISupportFragment) fm.getFragment(savedInstanceState, FRAGMENT_TAG);
        }
        if (fragment == null) {
            fragment = (ISupportFragment) initFragment(getIntent());
        }
        loadRootFragment(R.id.content, fragment);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragment != null) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment.get());
        }
    }

    protected Fragment initFragment(Intent data) {
        if (data == null) {
            throw new RuntimeException(
                    "you must provide a page info to display");
        }
        try {
            Bundle bundle = data.getExtras();
            Config outerConfig = Objects.requireNonNull(bundle).getParcelable((MVVMConstants.KEY_OUTER_CONFIG));
            if (outerConfig == null || StringUtils.isEmpty(outerConfig.getFragmentPath())) {
                throw new IllegalArgumentException("can not find page fragmentPath");
            }
            // 通过 ARouter 获取Fragment
            Fragment fragment = RouteUtils.getFragment(outerConfig.getFragmentPath(), outerConfig);
            fragment.setArguments(bundle);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("fragment initialization failed!");
    }
}
