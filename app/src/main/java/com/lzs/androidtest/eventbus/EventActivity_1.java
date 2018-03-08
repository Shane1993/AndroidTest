package com.lzs.androidtest.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.util.Log;
import android.widget.TextView;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by LEE on 2018/3/1.
 */

public class EventActivity_1 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_1);
        EventBus.getDefault().register(this);

        findViewById(R.id.btn_go).setOnClickListener(v -> goActivity(this, EventActivity_2.class));

        fv(R.id.btn_eventbus_send_event).setOnClickListener(v -> {
            sendEvent(new TextEvent(123, "OrdinaryEvent"));
        });

        fv(R.id.btn_eventbus_send_stikyevent).setOnClickListener(v -> {
            sendStikyEvent(new TextEvent(789, "StikyEvent"));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(TextEvent event) {
        Log.d("receiveEvent", event.toString());
        ((TextView)findViewById(R.id.tv_text)).setText(event.toString());
    }

    private void sendEvent(TextEvent msg) {
        EventManager.testEvent(msg);
    }

    private void sendStikyEvent(TextEvent msg) {
        EventBus.getDefault().postSticky(msg);
    }

}
