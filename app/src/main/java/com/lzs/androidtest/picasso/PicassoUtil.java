package com.lzs.androidtest.picasso;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by LEE on 2018/3/7.
 * 后续可考虑封装Picasso的相关功能，避免在项目中直接使用Picasso类
 */

public class PicassoUtil {

    /**
     * 初始化配置Picasso，在Application中调用
     * @param context
     */
    public static void init(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

}
