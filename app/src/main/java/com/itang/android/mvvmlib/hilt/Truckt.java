package com.itang.android.mvvmlib.hilt;

import com.itang.android.mvvmlib.hilt.qualifier.BindElectricEngine;
import com.itang.android.mvvmlib.hilt.qualifier.BindGasEngine;

import javax.inject.Inject;

//@ActivityScoped
public class Truckt {

    @Inject
    Driver driver;

//    @Inject
//    GasEngine gasEngine;
//    @Inject
//    ElectricEngine electricEngine;

    @BindGasEngine
    @Inject
    Engine gasEngine;

    @BindElectricEngine
    @Inject
    Engine electricEngine;

    @Inject
    public Truckt() {
    }

    public void deliver() {
//        gasEngine.start();
//        electricEngine.start();
        gasEngine.start();
        electricEngine.start();
        System.out.println("Truck is delivering cargo. Driven by " + driver);
//        gasEngine.shutdown();
//        electricEngine.shutdown();
        gasEngine.shutdown();
        electricEngine.shutdown();
    }
}
