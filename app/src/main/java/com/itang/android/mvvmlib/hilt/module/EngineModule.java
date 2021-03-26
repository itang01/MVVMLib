package com.itang.android.mvvmlib.hilt.module;

import com.itang.android.mvvmlib.hilt.ElectricEngine;
import com.itang.android.mvvmlib.hilt.Engine;
import com.itang.android.mvvmlib.hilt.GasEngine;
import com.itang.android.mvvmlib.hilt.qualifier.BindElectricEngine;
import com.itang.android.mvvmlib.hilt.qualifier.BindGasEngine;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class EngineModule {

    @BindGasEngine
    @Binds
    public abstract Engine bindGasEngine(GasEngine gasEngine);

    @BindElectricEngine
    @Binds
    public abstract Engine bindElectricEngine(ElectricEngine electricEngine);
}
