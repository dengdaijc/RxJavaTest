package com.by.rxjavatest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

            Log.e("onCompleted", "completed");

        }

        @Override
        public void onError(Throwable e) {

            e.printStackTrace();

        }

        @Override
        public void onNext(String s) {

            Log.e("onNext", s);

        }
    };

    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("a");
            subscriber.onNext("b");
            subscriber.onCompleted();

        }
    });

    Observable<String> observable1 = Observable.just("a", "b");

    String[] str = {"a", "b"};
    Observable<String> observable2 = Observable.from(str);


    Action1<String> onNext = new Action1<String>() {
        @Override
        public void call(String s) {

            Log.e("onNext", s);

        }
    };
    Action1<Throwable> onError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            throwable.printStackTrace();
        }
    };
    Action0 onComplete = new Action0() {
        @Override
        public void call() {
            Log.e("onCompleted", "completed");

        }
    };

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                observable.subscribe(subscriber);
//                observable1.subscribe(subscriber);
//                observable2.subscribe(subscriber);

//                observable.subscribe(onNext);
//                observable.subscribe(onNext, onError);
//                observable.subscribe(onNext, onError, onComplete);
//
//
//                observable.observeOn(AndroidSchedulers.mainThread()) //定义观察者的线程
//                        .subscribeOn(Schedulers.io()) //定义被观察者的线程
//                        .subscribe(subscriber);

//                 Observable.just("a", "b")
//                         .subscribeOn(AndroidSchedulers.mainThread())
//                         .observeOn(Schedulers.io())
//                         .map(new Func1<String, User>() {
//
//                             @Override
//                             public User call(String s) {
//                                 try {
//                                     Thread.sleep(1000);
//                                 } catch (InterruptedException e) {
//                                     e.printStackTrace();
//                                 }
//                                 User user = new User();
//                                 user.setName(s);
//
//                                 return user;
//                             }
//                         })
//                         .subscribe(new Action1<User>() {
//                             @Override
//                             public void call(User user) {
//                                Log.e("call", user.getName());
//                             }
//                         });

                String[] str = {"abc", "bcd"};
                Observable.from(str)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.io())
                        .flatMap(new Func1<String, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(String s) {
                                char[] c = s.toCharArray();
                                Integer[] is = new Integer[c.length];
                                for (int i = 0; i < c.length; i++) {
                                    is[i] = Integer.valueOf(c[i]);
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return Observable.from(is);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer c) {
//                                Log.e("test", c + "");
                                textView.setText(c + "");
                            }
                        });

            }
        });

    }
}
