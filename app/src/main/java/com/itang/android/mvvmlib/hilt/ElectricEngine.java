package com.itang.android.mvvmlib.hilt;

import javax.inject.Inject;

public class ElectricEngine implements Engine {

    @Inject
    public ElectricEngine() {
    }

    @Override
    public void start() {
        System.out.println("Electric engine start.");
    }

    @Override
    public void shutdown() {
        System.out.println("Electric engine shutdown.");
    }
}
