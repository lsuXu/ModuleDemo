package xu.test.moduledemo.rxjavaTest;


import android.util.Log;
import android.util.TimeUtils;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 12852 on 2017/12/19.
 */

public class Tools {

    Disposable dis;


    public Tools(){

    }

    Observable<String> subscriber = Observable.just("helllo");


    public void send(){
        Observable ob = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter observableEmitter) throws Exception {
                observableEmitter.onNext("hh");
                observableEmitter.onNext("hh1");
                observableEmitter.onNext("hh2");
                observableEmitter.onNext("hh3");
                observableEmitter.onNext("hh4");

            }
        }).throttleFirst(15,TimeUnit.MILLISECONDS);
        ob.doOnNext(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i("matTest","11111111111" + o);
            }
        });
        Consumer c = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("matTest",s);
            }
        };
        try {
            ob.subscribe(c);
        }catch (Exception e){
            Log.e("matTest","error:" + e.getMessage());
            e.printStackTrace();
        }

    }
//    Observable o = Observable.create(new ObservableOnSubscribee<Integer>);

    public void cycleSend() {

        //用于执行周期的心跳间隔轮询任务
        dis = Flowable.interval(1500, TimeUnit.MILLISECONDS).throttleFirst(3000,TimeUnit.MILLISECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.i("matTest","doOnNext" + aLong + "");
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.i("matTest","subscribe:"  + aLong);
                if(aLong == 10){

                }
            }
        });

    }

    public void stopHeart() {
        dis.dispose();
    }
}
