package com.itang.android.mvvmlib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.itang.android.mvvmlib.demo1.Demo1Respositry;
import com.itang.android.mvvmlib.hilt.Truckt;
import com.itang.mvvm.jetpack.t1view.Config;
import com.itang.mvvm.jetpack.t1view.activity.BaseRxActivity;
import com.itang.mvvm.jetpack.t1view.activity.ContainerActivity;
import com.itang.mvvm.jetpack.t4model.net.CommonService;
import com.itang.mvvm.rx.SchedulerTransformer;
import com.itang.mvvm.utils.RouteUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class MainActivity extends ContainerActivity {

    @Inject
    public Truckt truckt;
    @Inject
    Retrofit retrofit;
    @Inject
    @ApplicationContext
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        truckt.deliver();
        retrofit.create(CommonService.class).get("https://www.baidu.com/")
                .compose(new SchedulerTransformer<>())
                .subscribe(responseBody -> {
                    System.out.println("hilt request success");
                    System.out.println(responseBody.toString());
                });
        System.out.println("context = " + context);
    }

    @Override
    protected Fragment initFragment(Intent data) {
        return RouteUtils.getFragment(RoutePath.Demo1Fragment);
    }
}