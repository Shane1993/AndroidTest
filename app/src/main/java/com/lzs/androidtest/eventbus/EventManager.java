package com.lzs.androidtest.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LEE on 2018/3/7.
 * 用于封装EventBus的发送事件的方法
 *  分为
 *   主线程使用的
 *   io线程使用的（这些事件的接收方注意设置threadMode）
 */

public class EventManager {


    public static void testEvent(TextEvent textEvent) {
        EventBus.getDefault().post(textEvent);
    }
}
