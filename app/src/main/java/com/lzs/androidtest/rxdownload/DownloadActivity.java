package com.lzs.androidtest.rxdownload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;
import com.lzs.androidtest.common.Constants;
import com.lzs.androidtest.utils.FileUtil;
import com.lzs.androidtest.utils.ListUtil;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;

/**
 * Created by LEE on 2018/3/7.
 */

public class DownloadActivity extends BaseActivity {

    TextView tvStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        tvStatus = (TextView) fv(R.id.tv_download_status);

        fv(R.id.btn_download_1).setOnClickListener( v -> test1());
        fv(R.id.btn_download_2).setOnClickListener( v -> test2());
        fv(R.id.btn_download_3).setOnClickListener( v -> test3());
        fv(R.id.btn_download_4).setOnClickListener( v -> test4());
        fv(R.id.btn_download_5).setOnClickListener( v -> test5());
    }


    /**
     * 基本的下载测试
     *  测试文件格式：jpg,pdf,apk
     */
    private void test1() {
        String path = FileUtil.getFileDir(Constants.DownLoadConfig.DEFAULT_DIR_NAME, DownloadActivity.this);
        Mission mission = new Mission("http://www.it55.com/upload/2010-07/100708090966041.jpg", Constants.DownLoadConfig.DEFAULT_APK_NAME,
                path);


        RxDownload.INSTANCE
                .create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    setProgress(status);
                });
        RxDownload.INSTANCE.start(mission).subscribe();
    }


    /**
     * 根据数据库下载任务及下载地址设置
     */
    private void setMission(Mission missionDb, String downloadUrl) {

        String fileName = Constants.DownLoadConfig.DEFAULT_APK_NAME;

        if (missionDb == null) {
            Mission newMission = new Mission(downloadUrl, fileName,
                    FileUtil.getFileDir(Constants.DownLoadConfig.DEFAULT_DIR_NAME, DownloadActivity.this));
            // 创建新的Mission
            createMission(newMission);
        } else {
            // 判断传进来的任务和目标url是不是一样
            if (!TextUtils.equals(downloadUrl, missionDb.getUrl())) {
                // 如果已经有相同的任务了，就删除旧任务，并创建新的任务
                deleteAndCreateMission(missionDb, downloadUrl);
            } else {
                createMission(missionDb);
            }
        }
    }

    /**
     * 创建新的任务
     * @param newMission
     */
    private void createMission(Mission newMission) {

        // create才是真正的创建任务的方式
        RxDownload.INSTANCE.create(newMission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    setProgress(status);
                });
        // start是开始任务
        startMission(newMission);
    }

    /**
     * 开始执行下载任务
     */
    private void startMission(Mission mission) {
        RxDownload.INSTANCE.start(mission).subscribe();
    }

    /**
     * 停止下载任务
     */
    private void stopMission(Mission mission) {
        RxDownload.INSTANCE.stop(mission).subscribe();
    }

    /**
     * 删除就人物并创建新的任务
     * @param oldMission
     * @param url
     */
    private void deleteAndCreateMission(Mission oldMission, String url) {
        RxDownload.INSTANCE.create(oldMission).subscribe(status -> {

            if (status instanceof Normal) {
                // 下载失败，旧任务没开始
                delete(oldMission, url);
            }

            if (status instanceof Suspend) {
                // 旧任务下载暂停
                delete(oldMission, url);
            }

            if (status instanceof Failed) {
                // 旧任务正常下载失败
                delete(oldMission, url);
            }

            if (status instanceof Succeed) {
                // 旧任务下载完成
                delete(oldMission, url);
            }
        });
    }


    /**
     * 删除任务
     * 使用delete删除任务
     */
    private void delete(Mission mission, String url) {
        RxDownload.INSTANCE.delete(mission, true).subscribe(o -> {
            //删除成功，创建新的任务
            newCreate(url);
        }, throwable -> {
            throwable.printStackTrace();
            //删除失败，创建新的任务
            newCreate(url);
        });
    }

    /**
     * 创建新任务
     */
    private void newCreate(String url) {
        String fileName = Constants.DownLoadConfig.DEFAULT_APK_NAME;
        //清除文件
        File file = new File(FileUtil.getFileDir(Constants.DownLoadConfig.DEFAULT_DIR_NAME, DownloadActivity.this));
        FileUtil.deleteFile(file);

        Mission newMission = new Mission(url, fileName,
                FileUtil.getFileDir(Constants.DownLoadConfig.DEFAULT_DIR_NAME, DownloadActivity.this));
        createMission(newMission);
    }

    private void test2() {

    }

    private void test3() {

    }

    private void test4() {

    }

    private void test5() {

    }


    private void setProgress(Status status) {
        StringBuilder sb = new StringBuilder();
        sb.append("percent: " + status.percent() + "\n");
        sb.append("downloadSize: " + status.getDownloadSize() + "\n");
        sb.append("formatDownloadSize: " + status.formatDownloadSize() + "\n");
        sb.append("totalSize: " + status.getTotalSize() + "\n");
        sb.append("formatTotalSize: " + status.formatTotalSize() + "\n");
        tvStatus.setText(sb.toString());
        p("onNext", sb.toString());
    }
}
