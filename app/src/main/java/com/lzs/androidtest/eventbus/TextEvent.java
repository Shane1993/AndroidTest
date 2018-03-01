package com.lzs.androidtest.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LEE on 2018/3/1.
 */

public class TextEvent  {
    private int id;
    private String name;
    public TextEvent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TextEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
