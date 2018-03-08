package com.lzs.androidtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by LEE on 2018/2/26.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void goActivity(Context context, Class cls) {
        this.startActivity(new Intent(context, cls));
    }

    protected View fv(int id) {
        return this.findViewById(id);
    }

    protected void p(String title, String msg) {
        Log.d(title, msg);
    }

    protected void p(String msg) {
        p(">>>>>>>>>>><<<<<<<<<<<<<<<<<", msg);
    }
}
