package xu.test.moduledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by 12852 on 2018/1/12.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewId());
        initView();
    }

    protected abstract int setViewId();

    protected abstract void initView();

    public class TotalClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.i("click","click event view id=" + v.getId());
        }
    };

}
