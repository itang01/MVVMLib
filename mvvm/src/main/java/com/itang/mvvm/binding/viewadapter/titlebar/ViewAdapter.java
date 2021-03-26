package com.itang.mvvm.binding.viewadapter.titlebar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.itang.mvvm.R;
import com.itang.mvvm.binding.command.BindingCommand;
import com.itang.mvvm.widgit.MyCommonTitleBar;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.functions.Consumer;

import static com.itang.mvvm.jetpack.t1view.Config.TYPE_IMAGEBUTTON;
import static com.itang.mvvm.jetpack.t1view.Config.TYPE_TEXTVIEW;

public class ViewAdapter {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickLeftViewCommand", "onClickCenterViewCommand", "onMenu1ClickCommand", "onMenu2ClickCommand",
            "centerTextField", "leftTypeField", "showTitleBarField", "menu1TypeField",
            "menu1TextField", "menu1ImageResourceField", "menu2TypeField", "menu2TextField", "menu2ImageResourceField",
            "isThrottleFirst"}, requireAll = false)
    public static void onTitleBarCommand(MyCommonTitleBar titleBar,
                                         final BindingCommand onClickLeftViewCommand,
                                         final BindingCommand onClickCenterViewCommand,
                                         final BindingCommand onMenu1ClickCommand,
                                         final BindingCommand onMenu2ClickCommand,
                                         final ObservableField<String> centerTextField,
                                         final ObservableField<Integer> leftTypeField,
                                         final ObservableField<Boolean> showTitleBarField,
                                         final ObservableField<Integer> menu1TypeField,
                                         final ObservableField<String> menu1TextField,
                                         final ObservableField<Integer> menu1ImageResourceField,
                                         final ObservableField<Integer> menu2TypeField,
                                         final ObservableField<String> menu2TextField,
                                         final ObservableField<Integer> menu2ImageResourceField,
                                         final boolean isThrottleFirst) {
        if (centerTextField != null) {
            if (titleBar.getCenterTextView() != null) {
                titleBar.getCenterTextView().setText(centerTextField.get());
            }
        }
        if (leftTypeField != null) {
            if (TYPE_IMAGEBUTTON == leftTypeField.get() && titleBar.getLeftImageButton() != null) {
                titleBar.getLeftImageButton().setVisibility(View.VISIBLE);
            } else if (TYPE_TEXTVIEW == leftTypeField.get() && titleBar.getLeftTextView() != null) {
                titleBar.getLeftTextView().setVisibility(View.VISIBLE);
            } else {
                if (titleBar.getLeftImageButton() != null) {
                    titleBar.getLeftImageButton().setVisibility(View.GONE);
                }
                if (titleBar.getLeftTextView() != null) {
                    titleBar.getLeftTextView().setVisibility(View.GONE);
                }
            }
        }
        if (menu1TypeField != null) {
            if (TYPE_IMAGEBUTTON == menu1TypeField.get()) {
                if (menu1ImageResourceField != null) {
                    getIvMenu1(titleBar).setVisibility(View.VISIBLE);
                    getIvMenu1(titleBar).setImageResource(menu1ImageResourceField.get());
                }
            } else if (TYPE_TEXTVIEW == menu1TypeField.get()) {
                if (menu1TextField != null) {
                    getTvMenu1(titleBar).setVisibility(View.VISIBLE);
                    getTvMenu1(titleBar).setText(menu1TextField.get());
                }
            } else {
                if (getIvMenu1(titleBar) != null) {
                    getIvMenu1(titleBar).setVisibility(View.GONE);
                }
                if (getTvMenu1(titleBar) != null) {
                    getTvMenu1(titleBar).setVisibility(View.GONE);
                }
            }
        }
        if (menu2TypeField != null) {
            if (TYPE_IMAGEBUTTON == menu2TypeField.get()) {
                if (menu2ImageResourceField != null) {
                    getIvMenu2(titleBar).setVisibility(View.VISIBLE);
                    getIvMenu2(titleBar).setImageResource(menu2ImageResourceField.get());
                }
            } else if (TYPE_TEXTVIEW == menu2TypeField.get()) {
                if (menu2TextField != null) {
                    getTvMenu2(titleBar).setVisibility(View.VISIBLE);
                    getTvMenu2(titleBar).setText(menu2TextField.get());
                }
            } else {
                if (getIvMenu2(titleBar) != null) {
                    getIvMenu2(titleBar).setVisibility(View.GONE);
                }
                if (getTvMenu2(titleBar) != null) {
                    getTvMenu2(titleBar).setVisibility(View.GONE);
                }
            }
        }

        if (showTitleBarField != null) {
            titleBar.setVisibility(showTitleBarField.get() ? View.VISIBLE : View.GONE);
        }

        //点击 标题栏 返回按钮
        if (isThrottleFirst) {
            if (titleBar.getLeftImageButton() != null) {
                RxView.clicks(titleBar.getLeftImageButton())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onClickLeftViewCommand != null) {
                                    onClickLeftViewCommand.execute();
                                }
                            }
                        });
            }
            if (titleBar.getCenterLayout() != null) {
                RxView.clicks(titleBar.getCenterLayout())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onClickCenterViewCommand != null) {
                                    onClickCenterViewCommand.execute();
                                }
                            }
                        });
            }
            if (getIvMenu1(titleBar) != null) {
                RxView.clicks(getIvMenu1(titleBar))
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu1ClickCommand != null) {
                                    onMenu1ClickCommand.execute();
                                }
                            }
                        });
            }
            if (getTvMenu1(titleBar) != null) {
                RxView.clicks(getTvMenu1(titleBar))
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu1ClickCommand != null) {
                                    onMenu1ClickCommand.execute();
                                }
                            }
                        });
            }
            if (getIvMenu2(titleBar) != null && menu2TypeField.get() == TYPE_IMAGEBUTTON) {
                RxView.clicks(getIvMenu2(titleBar))
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu2ClickCommand != null) {
                                    onMenu2ClickCommand.execute();
                                }
                            }
                        });
            }
            if (getTvMenu2(titleBar) != null && menu2TypeField.get() == TYPE_TEXTVIEW) {
                RxView.clicks(getTvMenu2(titleBar))
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu2ClickCommand != null) {
                                    onMenu2ClickCommand.execute();
                                }
                            }
                        });
            }
        } else {
            if (titleBar.getLeftImageButton() != null) {
                RxView.clicks(titleBar.getLeftImageButton())
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onClickLeftViewCommand != null) {
                                    onClickLeftViewCommand.execute();
                                }
                            }
                        });
            }

            if (titleBar.getCenterLayout() != null) {
                RxView.clicks(titleBar.getCenterLayout())
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onClickCenterViewCommand != null) {
                                    onClickCenterViewCommand.execute();
                                }
                            }
                        });
            }

            if (getIvMenu1(titleBar) != null) {
                RxView.clicks(getIvMenu1(titleBar))
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu1ClickCommand != null) {
                                    onMenu1ClickCommand.execute();
                                }
                            }
                        });
            } else if (getTvMenu1(titleBar) != null) {
                RxView.clicks(getTvMenu1(titleBar))
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu1ClickCommand != null) {
                                    onMenu1ClickCommand.execute();
                                }
                            }
                        });
            }

            if (getIvMenu2(titleBar) != null && menu2TypeField.get() == TYPE_IMAGEBUTTON) {
                RxView.clicks(getIvMenu2(titleBar))
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu2ClickCommand != null) {
                                    onMenu2ClickCommand.execute();
                                }
                            }
                        });
            }
            if (getTvMenu2(titleBar) != null && menu2TypeField.get() == TYPE_TEXTVIEW) {
                RxView.clicks(getTvMenu2(titleBar))
                        .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object object) throws Exception {
                                if (onMenu2ClickCommand != null) {
                                    onMenu2ClickCommand.execute();
                                }
                            }
                        });
            }
        }
    }

    private static ImageView getIvMenu1(MyCommonTitleBar titleBar) {
        return titleBar.findViewById(R.id.ivMenu1);
    }

    private static ImageView getIvMenu2(MyCommonTitleBar titleBar) {
        return titleBar.getRightCustomView().findViewById(R.id.ivMenu2);
    }

    private static TextView getTvMenu1(MyCommonTitleBar titleBar) {
        return titleBar.findViewById(R.id.tvMenu1);
    }

    private static TextView getTvMenu2(MyCommonTitleBar titleBar) {
        return titleBar.getRightCustomView().findViewById(R.id.tvMenu2);
    }
}
