package com.lzs.androidtest.dagger;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lzs.androidtest.MainActivity;
import com.lzs.androidtest.R;

import javax.inject.Inject;

/**
 * Created by LEE on 2018/2/26.
 */

public class DaggerActivity extends Activity {

    @Inject
    Baozi baozi;

    @Inject
    Noodle kangshifu;

    @Inject
    TestSingleton singleton1;

    @Inject
    TestSingleton singleton2;

    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        mButton = (Button) findViewById(R.id.btn_test);


        Platform platform = DaggerPlatform.builder()
                .shangjiaAModule(new ShangjiaAModule("第二家饭店"))
                .build();

        // 第一种方式，直接创建对象
        final ZhaiNan zainan = platform.waimai();


        // 第二种方式，先创建空对象，然后再注射
        ZhaiNan zhaiNan2 = new ZhaiNan();
        platform.inject(zhaiNan2);

        // 为@Inject字段赋值
        platform.inject(this);

        mButton.setOnClickListener(v -> Toast.makeText(DaggerActivity.this, singleton1.toString() + "\n" + singleton2.toString(),Toast.LENGTH_LONG).show());
//        mButton.setOnClickListener(v -> Toast.makeText(DaggerActivity.this,baozi.toString() + " -> " + kangshifu.toString(),Toast.LENGTH_LONG).show());


    }
}
