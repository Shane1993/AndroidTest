package com.lzs.androidtest.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

/**
 * Created by LEE on 2018/3/3.
 */

public class DialogActivity extends BaseActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        dialog = new ProgressDialog(this);

        fv(R.id.btn_show_dialog).setOnClickListener(v -> dialog.show());
        fv(R.id.btn_cancel_dialog).setOnClickListener(v -> dialog.dismiss());
    }
}
