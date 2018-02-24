package xu.test.moduledemo;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.igexin.sdk.PushManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.alarmManager.AlarmManagerReceiver;
import xu.test.moduledemo.compress.CompressActivity;
import xu.test.moduledemo.faceRecognition.DetecterActivity;
import xu.test.moduledemo.getui.DemoIntentService;
import xu.test.moduledemo.getui.DemoPushService;
import xu.test.moduledemo.mysqldb.MysqlDBActivity;
import xu.test.moduledemo.opencvFace.OpenCvFaceComp;
import xu.test.moduledemo.rxjavaTest.RXMainActivity;
import xu.test.moduledemo.sqlitedb.SqliteDBActivity;
import xu.test.moduledemo.webView.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    private static Context con;
    Button mysqlDbBtn ;
    Button sqliteDbBtn ;

    static {
        System.loadLibrary("opencv_java3");
    }

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

    @BindView(R.id.linuxCommandBtn)
    Button linuxCommandBtn;

    @BindView(R.id.openCvFaceComp)
    Button openCvComp ;

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
        //初始化SDK
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        //注册IntentService类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        //获取设备cid
        String cid = PushManager.getInstance().getClientid(this.getApplicationContext());
        Log.i("GTIntentService","cid = " + cid);

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
        openCvComp.setOnClickListener(new JumpPageOnClick());
        alarmManageTest.setOnClickListener(new EventOnClick());
        normalCheckBtn.setOnClickListener(new EventOnClick());
        linuxCommandBtn.setOnClickListener(new EventOnClick());
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
                case R.id.openCvFaceComp:
                    intent.setClass(MainActivity.this, OpenCvFaceComp.class);
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
                    break;
                case R.id.linuxCommandBtn:
                    /*Process process = Runtime.getRuntime().exec("su");
                    os = new DataOutputStream(process.getOutputStream());
                    os.writeBytes(command + "\n");
                    os.writeBytes("exit\n");
                    os.flush();
                    process.waitFor();*/
                    try {
                        //未获取到root权限
                        List<String> commandList = new ArrayList<String>();
                        commandList.add("mkdir");
                        commandList.add("demo");
                        commandList.add("hello");
                        commandList.add("success.txt");

                            ProcessBuilder pb = new ProcessBuilder(commandList);
//                        String pathPaid = "";
                        pb.directory(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AAA"));
//                        Log.i("testLinuxCommand",pb.directory().getAbsolutePath());
                        Process process = pb.start();
//                        Process process = Runtime.getRuntime().exec("mkdir demo");
                        BufferedReader brE = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        Log.i("testLinuxCommand",pb.directory().getAbsolutePath());
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            Log.i("testLinuxCommand",line);
                        }
                        String lineError;
                        while ((lineError = brE.readLine()) != null) {
                            System.out.println(lineError);
                            Log.e("testLinuxCommand",lineError);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AAA";

                    String pathPaid = "/storage/emulated/legacy/AAA";
                    ProcessBuilder pb = new ProcessBuilder("su","-c","mkdir" + "demo");
//                    ProcessBuilder pb = new ProcessBuilder("ls ", "-l ","mkdir ","web.zip");
                    pb.directory(new File(path));  //切换到工作目录 /AAA

                    Log.i("testLinuxCommand",pb.directory().getAbsolutePath());
                    try {
                        Process p = pb.start();

                        BufferedReader brE = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//                        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            Log.i("testLinuxCommand",line);
                        }
                        String lineError;
                        while ((lineError = br.readLine()) != null) {
                            System.out.println(lineError);
                            Log.i("testLinuxCommand",lineError);
                        }
                    } catch (IOException e) {
                        Log.e("testLinuxCommand","错误：" + e.getMessage());
                        e.printStackTrace();
                    }
                    Log.i("testLinuxCommand","linuxCommandBtn");*/
                    /*String s = "\n";
                    try {
                        String[] cmdline = { "sh", "-c","cd /AAA && ls -l","mkdir demo"};
                        Process p = Runtime.getRuntime().exec(cmdline);
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = null;
                        //PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(p.getOutputStream())), true);
                        //out.println(cmd);
                        while ((line = in.readLine()) != null) {
                            s += line + "\n";
                        }
                        in.close();
//          out.close();
                        Log.v("testLinuxCommand", s);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
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
