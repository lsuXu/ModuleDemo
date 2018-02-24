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
import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2017/12/19.
 */

public class RXMainActivity extends AppCompatActivity {

    @BindView(R.id.sendData)
    Button sendBtn ;
    @BindView(R.id.startHeart)
    Button cycleBtn;
    @BindView(R.id.cancelHerat)
    Button cancelHeart;
    Tools tools ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_activity);
        ButterKnife.bind(this);
        tools = new Tools();
    }

    @OnClick({R.id.sendData,R.id.startHeart,R.id.cancelHerat})
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.sendData:
                tools.send();
                break;
            case R.id.startHeart:
                tools.cycleSend();
                break;
            case R.id.cancelHerat:
                tools.stopHeart();
        }
    }

}
