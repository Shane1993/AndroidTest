package com.lzs.androidtest.joda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lzs.androidtest.BaseActivity;
import com.lzs.androidtest.R;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import java.util.Date;

/**
 * Created by LEE on 2018/3/1.
 */

public class JodaActivity extends BaseActivity{

    private static final String TAG = "JodaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joda);

        // 要记得初始化
        JodaTimeAndroid.init(this);

        fv(R.id.btn_1).setOnClickListener( v -> test1());
        fv(R.id.btn_2).setOnClickListener( v -> test2());
        fv(R.id.btn_3).setOnClickListener( v -> test3());
        fv(R.id.btn_4).setOnClickListener( v -> test4());
        fv(R.id.btn_5).setOnClickListener( v -> test5());
        fv(R.id.btn_6).setOnClickListener( v -> test6());
    }


    /**
     * 基本用法
         DateTime dateTime;dateTime = new DateTime(); //以当前系统的毫秒级时间构建实例
         dateTime = new DateTime(1481006233254L); //以毫秒级时间参数构建实例
         dateTime = new DateTime("2016-11-22"); // 以String为参数构造实例
         dateTime = new DateTime(2016,12,1,11,22,59); // 以 年.月.日.时.分.秒 构造实例
         dateTime = new DateTime(2016,12,1,11,22,59,114); // 以 年.月.日.时.分.秒.毫秒 构造实例
         dateTime = new DateTime(new Date()); // 以 JDK中Date为参数构造实例
         dateTime = new DateTime(Calendar.getInstance()); // 以 JDK中Calendar为参数构造实例
         dateTime = new DateTime(new DateTime()); // 以 DateTime本身为参数构造实例

         // 注意：以上的每种实例化方式都可以在最后加上DateTimeZone参数来指定时区。
         //     或者加上Chronology参数指定年表。
         //     两个参数都不传或者传null，使用默认值

         DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
         // 通过DateTime的parse方法解析时间
         DateTime dateTime = DateTime.parse("2016-12-1 14:22:45", format);
     */
    private void test1() {
        DateTime dateTime = DateTime.now();
        Log.d("test1", dateTime.toString());
        Log.d("getYear", String.valueOf(dateTime.getYear()));
        Log.d("getHourOfDay", String.valueOf(dateTime.getHourOfDay()));
        Log.d("格式化", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        Log.d("格式化", dateTime.toString("HH:mm"));




    }

    /**
     * 利用Interval和Period计算时间的差值
     *  注意差值是2:24:59的话，那么通过period获取到的hours、minutes和seconds分别是
     *      2,24,59
     *      这里与test3中的差值有着本质的区别,test3指的是总的差值
     */
    private void test2() {

        DateTime time1 = new DateTime();
        DateTime time2 = new DateTime(2018,3,2,23,59,59);

        Log.d("time1", time1.toString());
        Log.d("time2", time2.toString());

        Interval interval = new Interval(time1, time2);
        Period period = interval.toPeriod();
        Log.d("days", String.valueOf(period.getDays()));
        Log.d("hours", String.valueOf(period.getHours()));
        Log.d("minutes", String.valueOf(period.getMinutes()));
        Log.d("seconds", String.valueOf(period.getSeconds()));
    }

    /**
     * 另一种计算两个时间的差值
     *  注意与上面那种方式的区别，这种方式与上面的有区别
     *  这种方式获得的差值时间是总的时间，比如上面的2:24:59
     *  test3中的方式获取到的hours是2，minutes是2*60+24
     */
    private void test3() {

        DateTime time1 = new DateTime();
        DateTime time2 = new DateTime(2018,3,2,23,59,59);

        Log.d("time1", time1.toString());
        Log.d("time2", time2.toString());

        Log.d("days", String.valueOf(Days.daysBetween(time1, time2).getDays()));
        Log.d("hours", String.valueOf(Hours.hoursBetween(time1, time2).getHours()));
        Log.d("minutes", String.valueOf(Minutes.minutesBetween(time1, time2).getMinutes()));
    }

    /**
     * 使用LocalDate  和 LocalDateTime
     * 两个的区别，LocalDateTime可以获取到时分秒
     *
     *  关于使用DateTime还是LocalDateTime
     *      如果是根据毫秒值来进行时间的转换，那么可以直接使用DateTime
     *      但是涉及到使用now()来获取时间时，最好是使用LocalDateTime
     */
    private void test4() {
        Log.d("LocalDate", LocalDate.now().toString());
        Log.d("LocalDate 格式化", LocalDate.now().toString("yyyy/MM/dd"));
        Log.d("Year", String.valueOf(LocalDate.now().getYear()));
        Log.d("DayOfYear", String.valueOf(LocalDate.now().getDayOfYear()));
        Log.d("DayOfMonth", String.valueOf(LocalDate.now().getDayOfMonth()));
        Log.d("DayOfWeek", String.valueOf(LocalDate.now().getDayOfWeek()));



        Log.d("LocalDateTime", LocalDateTime.now().toString());
        Log.d("LocalDateTime 格式化", LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        Log.d("Year", String.valueOf(LocalDateTime.now().getYear()));
        Log.d("DayOfYear", String.valueOf(LocalDateTime.now().getDayOfYear()));
        Log.d("DayOfMonth", String.valueOf(LocalDateTime.now().getDayOfMonth()));
        Log.d("DayOfWeek", String.valueOf(LocalDateTime.now().getDayOfWeek()));
        Log.d("hours", String.valueOf(LocalDateTime.now().getHourOfDay()));
        Log.d("minutes", String.valueOf(LocalDateTime.now().getMinuteOfHour()));

    }

    /**
     * 一些比较常用的计算和比较时间的方法，如：
     *      计算型：
     *          plusHours()
     *          minusMinutes()
     *      比较型：
     *          isAfter()
     *          isBefore()
     *          isAfterNow()
     *          isBeforeNow()
     *
     */
    private void test5() {
        DateTime dateTime1 = new DateTime("2018-3-1");
        DateTime dateTime2 = new DateTime("2018-3-10");

        Log.d(TAG, String.valueOf(DateTime.now().minusHours(24).isAfter(dateTime1)));
        Log.d(TAG, String.valueOf(DateTime.now().minusHours(48).isAfter(dateTime1)));
        Log.d(TAG, String.valueOf(DateTime.now().plusDays(5).isBefore(dateTime2)));
        Log.d(TAG, String.valueOf(DateTime.now().plusDays(10).isBefore(dateTime2)));

    }

    private void test6() {

    }
}
