package xu.test.moduledemo.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import xu.test.moduledemo.MainActivity;

/**
 * Created by 12852 on 2018/1/8.
 */

public class AlarmManagerReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date());
        Log.i("AlarmManagerReceiver","接到广播：" + intent.getAction() + ";时间：" + calendar.getTimeInMillis());
        Toast.makeText(MainActivity.getCon(),"收到广播" ,Toast.LENGTH_LONG);
        if(intent.getAction().equals("repeating"))
        Log.i("AlarmManagerReceiver","广播来了的嘎：" + intent.getStringExtra("msg"));
    }
}
