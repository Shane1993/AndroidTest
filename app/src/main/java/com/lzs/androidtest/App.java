package com.lzs.androidtest;

import android.app.Application;

import com.lzs.androidtest.common.Constants;
import com.lzs.androidtest.picasso.PicassoUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import zlc.season.rxdownload3.core.DownloadConfig;

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
        initDownLoad();
    }

    private void initDownLoad() {
        DownloadConfig.Builder builder = DownloadConfig.Builder.Companion.create(this)
                .enableDb(true)
                .setOkHttpClientFacotry(() -> new OkHttpClient().newBuilder()
                        .connectTimeout(Constants.DownLoadConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .build());

        DownloadConfig.INSTANCE.init(builder);
    }
}
