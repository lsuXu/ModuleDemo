package xu.test.moduledemo.db;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 * Created by 12852 on 2017/11/27.
 */

public class DBUtils {

    private static final String TAG = "DBUtils";

    private static final String IP = "192.168.0.102";

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://" + IP  + ":3306/moduledb";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "123456";

    private static Connection connect ;

    private static Handler myHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            for(int i = 0;i<data.getStringArrayList("id").size();i++){
                Log.i(TAG,"id =" + data.getStringArrayList("id").get(i));
                Log.i(TAG,"message =" + data.getStringArrayList("message").get(i));
            }

        }
    };

    public static Connection getConnection(){
        if(connect == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Class.forName(DRIVER_NAME);
                        connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return connect;
    }

    public static void query(final Connection conn, int id){
        if(conn == null){
            Log.i(TAG,"connect 为空");
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("select * from message;");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Bundle bundle = new Bundle();
                    //id列表信息存放
                    ArrayList<String> idList = new ArrayList<String>();
                    //message列表信息存放
                    ArrayList<String> messageList = new ArrayList<String>();
                    for(int i = 0;resultSet.next();i++){
                        idList.add(resultSet.getString("id"));
                        messageList.add(resultSet.getString("message"));
                    }
                    bundle.putStringArrayList("id",idList);
                    bundle.putStringArrayList("message",messageList);
                    Message message = new Message();
                    message.setData(bundle);
                    myHandle.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG,e.getMessage() + "");
                }
            }
        }).start();
    }
}
