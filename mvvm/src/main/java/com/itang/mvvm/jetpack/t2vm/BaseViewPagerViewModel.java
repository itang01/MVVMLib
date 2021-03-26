package com.itang.mvvm.jetpack.t2vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.itang.mvvm.bus.RxBus;
import com.itang.mvvm.bus.event.TabChangeEvent;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t3respository.BaseRespository;

import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

public class BaseViewPagerViewModel<C extends Config>
        extends BaseTargetViewModel<C> {
    private FixedLiveData<List<String>> countsLiveData;

    public FixedLiveData<List<String>> getCountsLiveData() {
        if (countsLiveData == null) {
            countsLiveData = new FixedLiveData<>();
        }
        return countsLiveData;
    }
    public BaseViewPagerViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void registerBus() {
        super.registerBus();
        addSubscribe(RxBus.getDefault().toObservable(TabChangeEvent.class).subscribe(new Consumer<TabChangeEvent>() {
            @Override
            public void accept(TabChangeEvent tabChangeEvent) throws Throwable {
                onTabChange(tabChangeEvent);
            }
        }));
    }

    protected void onTabChange(TabChangeEvent tabChangeEvent) {

    }
}
