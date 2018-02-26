package com.lzs.androidtest.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEE on 2018/2/26.
 */

@Module
public class ShangjiaAModule {

    String restaurant;

    public ShangjiaAModule(String restaurant) {
        this.restaurant = restaurant;
    }

    @Provides
    @Singleton
    public TestSingleton provideSingleTon() {
        return new TestSingleton();
    }

    @Provides
    public Baozi provideBaozi() {
        return new Baozi("豆沙包");
    }

//    @Provides
//    public Noodle provideNoodle(Tongyi noodle) {
//        return noodle;
//    }

    @Provides
    public Noodle provideKangshifu() {
        return new Kangshifu("微辣");
    }

    @Provides
    public String provideRestaurant() {
        return this.restaurant;
    }

}
