package com.lzs.androidtest.timer;

import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by LEE on 2018/3/2.
 *  定时器工具类
 *      可用于倒计时
 *      使用方法可参考main方法
 */

public class Timer {

/*    public static void main(String[] args) {
        Timer timer = new Timer(6000, 1000, new TimerCallback() {
            @Override
            public void onTick(long time) {
                System.out.println(String.valueOf(time));
            }

            @Override
            public void onFinish() {
                System.out.println("时间到");
            }
        });
        timer.start();
    }*/

    public static final int SECOND = 0;
    public static final int MINUTE = 1;
    public static final int HOURS = 2;

    private CountDownTimer timer;
    private TimerCallback callback;

    public Timer(long timeLeft, TimerCallback callback) {
        this(timeLeft, MINUTE, callback); // 默认以1分钟为触发单位
    }

    public Timer(long timeLeft, int unit, TimerCallback callback) {

        this.callback = callback;

        long interval = 1000L;

        switch (unit) {
            case SECOND:
                interval = 1000; // 1s
                break;
            case MINUTE:
                interval = 60000; // 1minute 60 * 1000
                break;
            case HOURS:
                interval = 3600000; // 1hour 60 * 60 * 1000
                break;
        }

        long finalInterval = interval;

        timer = new CountDownTimer(timeLeft, finalInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (callback != null) {
                    // 返回的时间单位与创建Timer时传进来的unit一样
                    callback.onTick(millisUntilFinished / finalInterval + 1); // + 1是为了消除计时误差
                }
            }

            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }
        };
    }

    public void start() {
        timer.start();
    }

    public void cancel() {
        timer.cancel();
    }

    public void reset() {
        cancel();
        timer.onTick(-1L);
    }

    // 直接触发onTick方法
    public void onTick(long count) {
        timer.onTick(count);
    }


    public interface TimerCallback {
        void onTick(long time); // 返回的time单位与unit一样
        void onFinish();
    }

}
