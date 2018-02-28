package com.lzs.androidtest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LEE on 2018/2/27.
 */

public class ToastUtil {

    public static void l(Context context, Object obj) {
        Toast.makeText(context, obj.toString(), Toast.LENGTH_SHORT).show();
    }
}
