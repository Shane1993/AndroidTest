package com.lzs.androidtest.dagger;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LEE on 2018/2/26.
 */

/**
 * @Component表示该类是注射器，通过Platform来为拥有@Inject注解的类进行注射（即初始化）
 */
@Singleton
@Component(modules = ShangjiaAModule.class)
public interface Platform {
    ZhaiNan waimai();

    void inject(ZhaiNan zhaiNan);

    void inject(DaggerActivity a);
}
