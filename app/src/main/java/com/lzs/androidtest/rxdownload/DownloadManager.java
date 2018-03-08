package com.lzs.androidtest.rxdownload;

import io.reactivex.android.schedulers.AndroidSchedulers;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

/**
 * Created by LEE on 2018/3/7.
 */

public class DownloadManager {


    /**
     * 创建下载任务
     */
    private void createMission(Mission newMission) {

        RxDownload.INSTANCE.create(newMission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
//                    setProgress(status);
//                    setAction(status);
                });
//        startMission(newMission);

    }
}
