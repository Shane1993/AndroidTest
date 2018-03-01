package com.lzs.androidtest.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LEE on 2018/3/1.
 */

public class EventActivity_2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_2);

        findViewById(R.id.btn_send).setOnClickListener(v -> send());
    }

    private void send() {
        EventBus.getDefault().post(new TextEvent(100, "Lizhisheng"));
    }
}
