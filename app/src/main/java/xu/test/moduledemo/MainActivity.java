package xu.test.moduledemo;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.alarmManager.AlarmManagerReceiver;
import xu.test.moduledemo.compress.CompressActivity;
import xu.test.moduledemo.faceRecognition.DetecterActivity;
import xu.test.moduledemo.faceRecognition.FaceRecognitionActivity;
import xu.test.moduledemo.mysqldb.MysqlDBActivity;
import xu.test.moduledemo.rxjavaTest.RXMainActivity;
import xu.test.moduledemo.sqlitedb.SqliteDBActivity;
import xu.test.moduledemo.webView.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    private static Context con;
    Button mysqlDbBtn ;
    Button sqliteDbBtn ;

    AlarmManagerReceiver alarmManagerReceiver;

    AlarmManager alarmManager;

    PendingIntent pendingIntent;

    @BindView(R.id.compressCaseBtn)
    Button compressCaseBtn;

    @BindView(R.id.rxTestBtn)
    Button rxTestBtn;

    @BindView(R.id.faceRecognitionBtn)
    Button faceRecognitionBtn;

    @BindView(R.id.webViewBtn)
    Button webViewBtn;

    @BindView(R.id.alarmManageTest)
    Button alarmManageTest;

    @BindView(R.id.normalCheck)
    Button normalCheckBtn;

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
        con = this;
        initView();
        initOnClickEvent();
    }

    private void initView(){
        mysqlDbBtn = (Button) findViewById(R.id.mysqlDbBtn);
        sqliteDbBtn = (Button) findViewById(R.id.sqliteDbBtn);
    }

    private void initOnClickEvent(){
        rxTestBtn.setOnClickListener(new JumpPageOnClick());
        mysqlDbBtn.setOnClickListener(new JumpPageOnClick());
        sqliteDbBtn.setOnClickListener(new JumpPageOnClick());
        compressCaseBtn.setOnClickListener(new JumpPageOnClick());
        faceRecognitionBtn.setOnClickListener(new JumpPageOnClick());
        webViewBtn.setOnClickListener(new JumpPageOnClick());
        alarmManageTest.setOnClickListener(new EventOnClick());
        normalCheckBtn.setOnClickListener(new EventOnClick());
    }

    public class JumpPageOnClick implements View.OnClickListener{

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
                    intent.setClass(MainActivity.this, DetecterActivity.class);
                    break;
                case R.id.webViewBtn:
                    intent.setClass(MainActivity.this, WebViewActivity.class);
                    break;
                default:
                    break;
            }

            startActivity(intent);
        }
    }

    private class EventOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.alarmManageTest:
                    start();
                    Log.i("test","开启闹钟");
                    break;
                case R.id.normalCheck:
                    int [] ints = new int[12];
                    ints[0] = 1;
                    Log.i("arrayTest","size" + ints.length);
                    for(int i:ints){
                        Log.i("arrayTest","" + i);
                    };
                default:
                    break;
            }
        }
    }

    public void start(){
        Intent sender = new Intent();
        sender.setAction("repeating");
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //从日历中获取当前年月日，重新初始化日历日期
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),15,39,0);
        //
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1,sender,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
    
    public static Context getCon(){
        return con;
    }
}
