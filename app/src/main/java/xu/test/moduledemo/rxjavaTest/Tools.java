package xu.test.moduledemo.rxjavaTest;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 12852 on 2017/12/19.
 */

public class Tools {

    private Observer<String> receiver;
    Observable<String> sender = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {

            subscriber.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
        }
    });
    public Tools(Observer re){
        this.receiver = re;
        sender.subscribe(receiver);
    }

    Observable<String> subscriber = Observable.just("helllo");


    public void send(){
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("Hi，jjjjjjjj！");  //发送数据"Hi，Weavey！"
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(receiver);

        subscriber.subscribe(receiver);

    }

}
