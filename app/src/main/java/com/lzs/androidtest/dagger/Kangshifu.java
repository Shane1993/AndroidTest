package com.lzs.androidtest.dagger;

/**
 * Created by LEE on 2018/2/26.
 */

public class Kangshifu extends Noodle {

    private String spicy = "";

    public Kangshifu() {
    }

    public Kangshifu(String spicy) {
        this.spicy = spicy;
    }

    @Override
    public String toString() {
        return "康师傅方便面" + spicy;
    }
}
