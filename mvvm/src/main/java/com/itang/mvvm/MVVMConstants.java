package com.itang.mvvm;

public interface MVVMConstants {

    String KEY_OUTER_CONFIG = "OUNTER_CONFIG";
    String MVVM = "/Mvvm";

    //Route Path Of Fragment
    interface RouteHub {
        String ContainerActivity = MVVM + "/ContainerActivity";
        String BaseTabViewPagerFragment = MVVM + "/BaseTabViewPagerFragment";
        String BaseTitleBarFragment = MVVM + "/BaseTitleBarFragment";
        String BaseListFragment = MVVM + "/BaseListFragment";
    }
}