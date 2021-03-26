package com.itang.android.mvvmlib.hilt;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.qualifiers.ApplicationContext;

//@ActivityScoped
public class Driver {

    @Inject
    public Driver(@ApplicationContext Context aplicationContext, @ActivityContext Context activityContext,
                  Application application, Activity activity) {
        System.out.println("driver aplicationContext = " + aplicationContext);
        System.out.println("driver activityContext = " + activityContext);
        System.out.println("driver application = " + application);
        System.out.println("driver activity = " + activity);
    }

}
