package com.lzs.androidtest.dagger;

import javax.inject.Inject;

/**
 * Created by LEE on 2018/2/26.
 */

public class Baozi {

    String name;

    @Inject
    public Baozi() {
    }

    public Baozi(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
