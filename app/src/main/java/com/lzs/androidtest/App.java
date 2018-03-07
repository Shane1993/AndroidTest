package com.lzs.androidtest;

import android.app.Application;

import com.lzs.androidtest.picasso.PicassoUtil;

/**
 * Created by LEE on 2018/3/7.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLib();
    }

    private void initLib() {
        PicassoUtil.init(getApplicationContext());
    }
}
