package com.itang.mvvm.widgit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 标题栏
 */
public class MyCommonTitleBar extends CommonTitleBar {
    public MyCommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}