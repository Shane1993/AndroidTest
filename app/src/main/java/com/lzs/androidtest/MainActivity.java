package com.lzs.androidtest;

import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lzs.androidtest.dagger.DaggerActivity;
import com.lzs.androidtest.dialog.DialogActivity;
import com.lzs.androidtest.eventbus.EventActivity_1;
import com.lzs.androidtest.joda.JodaActivity;
import com.lzs.androidtest.picasso.PicassoActivity;
import com.lzs.androidtest.rxdownload.DownloadActivity;
import com.lzs.androidtest.rxjava.RxActivity;
import com.lzs.androidtest.timer.TimerActivity;
import com.lzs.androidtest.utils.PathResolver;
import com.lzs.androidtest.utils.ToastUtil;

import java.net.URISyntaxException;


/**
 * Created by LEE on 2017/7/25.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    Button btnPickFile, btnDagger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPickFile = (Button) findViewById(R.id.btn_pick);
        btnDagger = (Button) findViewById(R.id.btn_dagger);


        btnPickFile.setOnClickListener((view) -> {this.goActivity(this, FilePickerActivity.class);});

        btnDagger.setOnClickListener(v -> {this.goActivity(this, DaggerActivity.class);});

        findViewById(R.id.btn_rx).setOnClickListener(v -> this.goActivity(this, RxActivity.class));

        findViewById(R.id.btn_eventnbus).setOnClickListener(v -> this.goActivity(this, EventActivity_1.class));

        fv(R.id.btn_joda).setOnClickListener(v -> this.goActivity(this, JodaActivity.class));

        fv(R.id.btn_timer).setOnClickListener(v -> this.goActivity(this, TimerActivity.class));

        fv(R.id.btn_dialog).setOnClickListener(v -> this.goActivity(this, DialogActivity.class));

        fv(R.id.btn_picasso).setOnClickListener(v -> this.goActivity(this, PicassoActivity.class));

        fv(R.id.btn_download).setOnClickListener(v -> this.goActivity(this, DownloadActivity.class));

    }


    public void l(Object obj) {
        Log.d(TAG, obj.toString());
    }

}
