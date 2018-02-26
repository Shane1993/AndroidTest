package com.lzs.androidtest.dagger;

import javax.inject.Inject;

/**
 * Created by LEE on 2018/2/26.
 */

public class Tongyi extends Noodle{

    @Inject
    public Tongyi() {}

    @Override
    public String toString() {
        return "统一方便面";
    }
}
