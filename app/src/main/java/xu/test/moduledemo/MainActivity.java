package xu.test.moduledemo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.compress.CompressActivity;
import xu.test.moduledemo.faceRecognition.FaceRecognitionActivity;
import xu.test.moduledemo.mysqldb.MysqlDBActivity;
import xu.test.moduledemo.rxjavaTest.RXMainActivity;
import xu.test.moduledemo.sqlitedb.SqliteDBActivity;

public class MainActivity extends AppCompatActivity {

    Button mysqlDbBtn ;
    Button sqliteDbBtn ;

    @BindView(R.id.compressCaseBtn)
    Button compressCaseBtn;

    @BindView(R.id.rxTestBtn)
    Button rxTestBtn;

    @BindView(R.id.faceRecognitionBtn)
    Button faceRecognitionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,
                    }
                    , 1);
        }

        initView();
        initOnClickEvent();
    }

    private void initView(){
        mysqlDbBtn = (Button) findViewById(R.id.mysqlDbBtn);
        sqliteDbBtn = (Button) findViewById(R.id.sqliteDbBtn);
    }

    private void initOnClickEvent(){
        rxTestBtn.setOnClickListener(new CustomerOnclick());
        mysqlDbBtn.setOnClickListener(new CustomerOnclick());
        sqliteDbBtn.setOnClickListener(new CustomerOnclick());
        compressCaseBtn.setOnClickListener(new CustomerOnclick());
        faceRecognitionBtn.setOnClickListener(new CustomerOnclick());
    }

    public class CustomerOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch(v.getId()){
                case R.id.mysqlDbBtn:
                    intent.setClass(MainActivity.this,MysqlDBActivity.class);
                    break;
                case R.id.sqliteDbBtn:
                    intent.setClass(MainActivity.this, SqliteDBActivity.class);
                    break;
                case R.id.rxTestBtn:
                    intent.setClass(MainActivity.this, RXMainActivity.class);
                    break;
                case  R.id.compressCaseBtn:
                    intent.setClass(MainActivity.this, CompressActivity.class);
                    break;
                case R.id.faceRecognitionBtn:
                    intent.setClass(MainActivity.this, FaceRecognitionActivity.class);
            }
            startActivity(intent);
        }
    }
}
