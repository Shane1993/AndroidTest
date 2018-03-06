package com.lzs.androidtest.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;



import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LEE on 2018/3/3.
 * 全局loading对话框
 *      只能按后退键取消
 */

public class ProgressDialog {

    private MaterialDialog dialog;
    private Disposable timeoutSubscription;

    /**
     * 显示计数, 避免显示多个
     */
    private AtomicInteger showingCount;

    public ProgressDialog(Context context) {
        showingCount = new AtomicInteger(0);
        dialog = new MaterialDialog.Builder(context)
                .content("加载中...")
                .progress(true, 0)
                .dismissListener(dialog1 -> showingCount.set(0))
                .cancelable(false)
                .build();
    }

    public void show() {
        dialog.show();
        showingCount.getAndIncrement();
        timeout();
    }

    public void show(String text) {
        dialog.setCancelable(false);
        dialog.setContent(text);
        dialog.show();
        showingCount.getAndIncrement();
        timeout();
    }

    private void timeout() {
        if (timeoutSubscription != null && !timeoutSubscription.isDisposed()) {
            timeoutSubscription.dispose();
        }
        timeoutSubscription = Observable.just("timeout")
                .delay(3, TimeUnit.SECONDS)
                .subscribe(s -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.setCancelable(true);
                    }
                });
    }

    public void dismiss() {
        int count = showingCount.decrementAndGet();
        // 显示计数为0时才真正关闭
        if (count <= 0) {
            dialog.dismiss();
            showingCount.set(0);
        }
    }

    /**
     * 强制关闭, 忽略showingCount
     */
    public void forceDismiss() {
        dialog.dismiss();
        showingCount.set(0);
    }
}
