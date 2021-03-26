package com.itang.mvvm.bus.event;

public class TitleBarEvent {
    public boolean onLeftClick;
    public boolean onCenterClick;
    public boolean onMenu1Click;
    public boolean onMenu2Click;

    public TitleBarEvent setOnLeftClick(boolean onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public TitleBarEvent setOnCenterClick(boolean onCenterClick) {
        this.onCenterClick = onCenterClick;
        return this;
    }

    public TitleBarEvent setOnMenu1Click(boolean onMenu1Click) {
        this.onMenu1Click = onMenu1Click;
        return this;
    }

    public TitleBarEvent setOnMenu2Click(boolean onMenu2Click) {
        this.onMenu2Click = onMenu2Click;
        return this;
    }
}
