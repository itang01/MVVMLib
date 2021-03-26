package com.itang.mvvm.bus.event;

import java.util.List;

public class TabChangeEvent {
    public String channel = "";
    public boolean isOnFilterByClickEnsureBtn;
    public List<String> counts;

    public TabChangeEvent(String channel, List<String> counts) {
        this.channel = channel;
        this.counts = counts;
    }

    public TabChangeEvent setOnFilterByClickEnsureBtn(boolean onFilterByClickEnsureBtn) {
        isOnFilterByClickEnsureBtn = onFilterByClickEnsureBtn;
        return this;
    }
}
