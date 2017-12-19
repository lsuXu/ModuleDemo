package xu.test.moduledemo.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2017/12/19.
 */

public class RXMainActivity extends AppCompatActivity {

    @BindView(R.id.sendData)
    Button sendBtn ;
    Tools tools ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_activity);
        ButterKnife.bind(this);
        tools = new Tools(receiver);
    }

    @OnClick({R.id.sendData})
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.sendData:
                tools.send();;
                break;

        }
    }

    Observer<String> receiver = new Observer<String>() {

        @Override
        public void onCompleted() {

            //数据接收完成时调用
        }

        @Override
        public void onError(Throwable e) {

            //发生错误调用
        }

        @Override
        public void onNext(String s) {

            //正常接收数据调用
           Log.i("hhhh",s);  //将接收到来自sender的问候"Hi，Weavey！"
        }
    };

}
