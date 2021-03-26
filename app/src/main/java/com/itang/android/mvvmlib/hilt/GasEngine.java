package com.itang.android.mvvmlib.hilt;

import javax.inject.Inject;

public class GasEngine implements Engine {

    @Inject
    public GasEngine() {
    }

    @Override
    public void start() {
        System.out.println("Gas engine start.");
    }

    @Override
    public void shutdown() {
        System.out.println("Gas engine shutdown.");
    }
}
