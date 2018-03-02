package com.lzs.androidtest.timer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

import org.joda.time.DateTime;

/**
 * Created by LEE on 2018/3/2.
 */

public class TimerActivity extends BaseActivity {

    TextView tvTime;

    Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvTime = (TextView) fv(R.id.tv_time);

        long sumTime = 3 * 60 * 1000L; // 60s
        long intervalTime = 10 * 1000L; // 10s

        DateTime time1 = DateTime.now().plusMinutes(2);
        DateTime time2 = DateTime.now();
        long timeLeft = time1.getMillis() - time2.getMillis();

        // timer的作用是作为定时器
        timer = new Timer(5000, Timer.SECOND, new Timer.TimerCallback() {
            @Override
            public void onTick(long time) {
                tvTime.setText(String.valueOf(time) + " seconds left");
            }

            @Override
            public void onFinish() {
                tvTime.setText("时间到");
            }
        });

        fv(R.id.btn_timer_start).setOnClickListener(v -> start());
        fv(R.id.btn_timer_pause).setOnClickListener(v -> pause());
        fv(R.id.btn_timer_resume).setOnClickListener(v -> resume());
    }

    private void start() {
        timer.start();
    }

    private void pause() {
        timer.cancel();
    }

    private void resume() {
        timer.onTick(5000); // 直接触发onTick方法
    }



}
