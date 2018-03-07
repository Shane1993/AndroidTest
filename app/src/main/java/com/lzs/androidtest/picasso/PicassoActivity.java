package com.lzs.androidtest.picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;
import com.lzs.androidtest.utils.ToastUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by LEE on 2018/3/6.
 */

public class PicassoActivity extends BaseActivity {


    ImageView iv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        iv1 = (ImageView) fv(R.id.iv_picasso);

        fv(R.id.btn_picasso_get).setOnClickListener(v -> {
            Picasso.with(this)
                    .load("http://pic1.win4000.com/wallpaper/e/526c9f87129d9.jpg")
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(iv1.getWidth(), iv1.getHeight())
                    .into(iv1, new Callback() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.l(PicassoActivity.this, "OnSuccess");
                        }

                        @Override
                        public void onError() {
                            ToastUtil.l(PicassoActivity.this, "onError");

                        }
                    });
        });

    }
}
