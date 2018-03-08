package com.lzs.androidtest.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by LEE on 2018/3/1.
 */

public class EventActivity_2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_2);
        EventBus.getDefault().register(this);

        findViewById(R.id.btn_send).setOnClickListener(v -> send());
    }

    private void send() {
        EventBus.getDefault().post(new TextEvent(100, "Lizhisheng"));
    }

    @Subscribe
    public void receiveEvent(TextEvent msg) {
        ((TextView) fv(R.id.tv_eventbus_event)).setText(msg.toString());
    }

    @Subscribe(sticky = true)
    public void receiveStikyEvent(TextEvent msg) {
        ((TextView) fv(R.id.tv_eventbus_stikyevent)).setText(msg.toString());
    }

}
