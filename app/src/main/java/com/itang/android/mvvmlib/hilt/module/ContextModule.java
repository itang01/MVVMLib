package com.itang.android.mvvmlib.hilt.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class ContextModule {

    @Provides
    public Context provideContext(){
        return null;
    }

}
