package com.itang.mvvm.jetpack.t4model.bean;

public class DialogConfig {
    public boolean isShowTitle;
    public String title = "";
    public String content = "";

    public DialogConfig(String content) {
        this.content = content;
    }

    public DialogConfig setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
        return this;
    }

    public DialogConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogConfig setContent(String content) {
        this.content = content;
        return this;
    }
}
