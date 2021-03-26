package com.itang.mvvm.jetpack.t1view.fragment.base;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.itang.mvvm.BR;
import com.itang.mvvm.R;
import com.itang.mvvm.databinding.MvvmFragmentBaseViewpagerBindingImpl;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.adapter.MyFragmentPagerAdapter;
import com.itang.mvvm.jetpack.t2vm.BaseViewPagerViewModel;
import com.itang.mvvm.jetpack.t3respository.BaseRespository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BaseViewPagerFragment<ViewModel extends BaseViewPagerViewModel<? extends Config>, C extends Config>
        extends BaseTargetFragment<MvvmFragmentBaseViewpagerBindingImpl, ViewModel, C> {

    protected List<String> titles;
    protected List<Fragment> fragments;

    protected MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void buildInnerConfig(Config innerConfig, Config outerConfig) {
        super.buildInnerConfig(innerConfig, outerConfig);
        innerConfig.setTargetViewLayout(R.layout.mvvm_fragment_base_viewpager)
                .setTargetViewVariableId(BR.vm);
    }

    @Override
    public void init() {
        super.init();

        titles = new ArrayList<>();
        fragments = new ArrayList<>();

        initTitlesAndFragments(titles, fragments);
        if (titles != null && titles.size() == 0 && fragments != null && fragments.size() > 0) {
            for (int i = 0; i < fragments.size(); i++) {
                titles.add("tab " + i);
            }
        }
        getTargetBinding().tabLayout.setVisibility(fragments.size() > 1 ? View.VISIBLE : View.GONE);
        initViewPager();

    }

    protected void initTitlesAndFragments(List<String> titles, List<Fragment> fragments) {

    }

    private void initViewPager() {
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments, titles);
        getTargetBinding().viewPager.setAdapter(pagerAdapter);

        //mTabLayout.setTextsize(12f);
        getTargetBinding().tabLayout.setViewPager(getTargetBinding().viewPager, titles.toArray(new String[]{}));

        getTargetBinding().tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                BaseViewPagerFragment.this.onTabSelect(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        getTargetBinding().viewPager.setOffscreenPageLimit(fragments.size() - 1);
        getTargetBinding().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //log("position = " + position + ", positionOffset = " + positionOffset + ", positionOffsetPixels = " + positionOffsetPixels + ", screenWidth = " + ScreenUtils.getScreenWidth(context));
                BaseViewPagerFragment.this.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                getTargetBinding().tabLayout.setCurrentTab(position);
                BaseViewPagerFragment.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                BaseViewPagerFragment.this.onPageScrollStateChanged(state);
            }
        });
    }

    protected void onPageScrollStateChanged(int state) {
    }

    protected void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    protected void onTabSelect(int position) {
        getTargetBinding().viewPager.setCurrentItem(position);
    }

    public void onPageSelected(int position) {

    }

    protected void refreshListData() {

    }

    protected void dealRecyclerViewStyle() {

    }


    @Override
    public void registerUIChangeLiveDataCallBack() {
        super.registerUIChangeLiveDataCallBack();
        getTargetViewModel().getCountsLiveData().observe(getViewLifecycleOwner(), (counts) -> {
            try {
                Field mTabsContainerField = SlidingTabLayout.class.getDeclaredField("mTabsContainer");
                Field mTabCountField = SlidingTabLayout.class.getDeclaredField("mTabCount");
                Field mTitlesField = SlidingTabLayout.class.getDeclaredField("mTitles");
                mTabsContainerField.setAccessible(true);
                mTabCountField.setAccessible(true);
                mTitlesField.setAccessible(true);
                LinearLayout mTabsContainer = (LinearLayout) mTabsContainerField.get(getTargetBinding().tabLayout);
                mTabCountField.set(getTargetBinding().tabLayout, 0);
                mTitlesField.set(getTargetBinding().tabLayout, new ArrayList<String>());
                mTabsContainer.removeAllViews();
                int index = 0;
                for (String count : counts) {
                    getTargetBinding().tabLayout.addNewTab(titles.get(index) + "（" + count + "）");
                    index++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
