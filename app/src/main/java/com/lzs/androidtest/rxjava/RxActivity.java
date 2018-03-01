package com.lzs.androidtest.rxjava;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import com.lzs.androidtest.R;
import com.lzs.androidtest.utils.ToastUtil;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by LEE on 2018/2/27.
 */

public class RxActivity extends Activity {

    private static String TAG = "RxActivity";

    private ImageView img1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        img1 = (ImageView) findViewById(R.id.img_rx);

        findViewById(R.id.btn_test1).setOnClickListener(v -> test1());
        findViewById(R.id.btn_test2).setOnClickListener(v -> test2());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.btn_test3).setOnClickListener(v -> test3());
        }

        findViewById(R.id.btn_test4).setOnClickListener(v -> test4());
        findViewById(R.id.btn_test5).setOnClickListener(v -> test5());
        findViewById(R.id.btn_test6).setOnClickListener(v -> test6());
        findViewById(R.id.btn_test7).setOnClickListener(v -> test7());
    }

    Observable observable1;
    Observer<String> observer1;

    /**
     * 最基本的工作流程
     */
    private void test1() {
        if (observable1 == null) {
            observable1 = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                    observableEmitter.onNext("Hello");
                    observableEmitter.onNext("World");
                    observableEmitter.onComplete();
                }
            });

            observer1 = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(String s) {
                    Log.d("RxActivity", s);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "complete");
                }
            };
        }

        observable1.subscribe(observer1);
    }


    /**
     * 使用from方法遍历数组，并为数组中的每一个元素执行方法
     */
    private void test2() {
        String[] arr = {"Java", "C++", "GO", "PHP", "Python"};
        Observable.fromArray(arr).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * 使用Scheduler切换线程
     * 这点很重要，因为RxJava通常会和Retrofit网络框架配合
     * 而处理网络数据的时候往往需要线程的来回切换，此时使用RxJava的好处就会体现出来，只需要两个方法
     * subscribeOn(thread): 被观察者Observable操作数据的线程，一般在io线程中处理，此时执行的是Observable中的call方法
     * observeOn(mainThread): 观察者Observer显示数据的线程，一般在MainThread，此时执行性的是Observer中的onNext()等方法
     * RxJava一个很方便的地方就是，它提供了Schedulers这个类，避免我们手动创建Thread类
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void test3() {

        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> observableEmitter) throws Exception {
                Drawable drawableRes = getDrawable(R.mipmap.ic_launcher);
                observableEmitter.onNext(drawableRes);
            }
        }).subscribeOn(Schedulers.io()) // Observable执行在io线程
                .observeOn(AndroidSchedulers.mainThread()) // Observer执行在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        img1.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 这里先插播一下Observable.just方法
     * 可以将just方法理解成一个简化create的方法，他可以直接通过just中的参数获取外部的数据，然后可以直接调用subscribe传给Observer
     * 如Observable.just("Hello, world!").subscribe(s -> System.out.println(s + " -Dan"));
     * 不过这样写没有什么意义，只是证明了just了可以简约的将数据放到订阅事件发布当中
     * 一般just会和map方法一起使用
     * <p>
     * map的用法：
     * map的作用相当于通过Func类将just传进来的数据进行一个转化，然后将最终数据传给Observer
     */
    private void test4() {
        int res = R.mipmap.ic_launcher_round;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Observable.just(res).map(id -> {

                Log.d(TAG, "subscribeOn " + Thread.currentThread().getName());
                return RxActivity.this.getDrawable(id);
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(d ->
                    {

                        Log.d(TAG, "observeOn " + Thread.currentThread().getName());
                        img1.setImageDrawable(d);
                    });
        }
    }


    /**
     * flatMap的测试
     * 他的作用跟from有一点不一样，那就是from是将数据拆分了交给Observable处理
     * 而flatMap的作用是将Observable中call返回的数据拆分了交给Observer处理
     */
    private void test5() {
        String[] arr = {"Java", "C++", "GO", "PHP", "Python"};

        Observable.just(arr)
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String[] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept" + s);
            }
        });

        Log.d(TAG, "=============");

        // 上面的作用和下面的用法是一样的
        Observable.fromArray(arr)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept" + s);
                    }
                });

    }

    /**
     * 测试compose和ObservableTransformer
     */
    private void test6() {
        int res = R.mipmap.ic_launcher_round;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Observable.just(res).map(id -> {

                Log.d(TAG, "subscribeOn " + Thread.currentThread().getName());
                return RxActivity.this.getDrawable(id);
            })
                    .compose(RxUtil.schedulers())
                    .subscribe(d ->
                    {

                        Log.d(TAG, "observeOn " + Thread.currentThread().getName());
                        img1.setImageDrawable(d);
                    });
        }
    }

    /**
     * 测试zip功能
     * 其实就是同时处理两个Observable中的参数(即onNext中的数据)，然后通过一个BiFunction进行处理
     * 最后将数据返回给Observer
     *  注意BiFunction的apply触发次数由几个Observable中最少的onNext个数决定
     *      比如下面的apply只会触发两次，因为o2中只有两个onNext
     */
    private void test7() {
        Observable<Integer> o1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
            }
        });

        Observable<String> o2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("A");
                observableEmitter.onNext("B");
            }
        });

        Observable.zip(o1, o2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                Log.d(TAG, "'zip --->'" + integer + s);
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "Observer---->" + s);
            }
        });

    }

}
