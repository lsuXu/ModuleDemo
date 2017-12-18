package xu.test.moduledemo.mysqldb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 12852 on 2017/11/27.
 */

public class MysqlDBUtils {

    private static final String TAG = "MysqlDBUtils";

    private static final String IP = "192.168.0.116";

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://" + IP  + ":3306/moduledb";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "123456";

    private static Connection connect ;

    private static Handler myHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch((String)bundle.get("method")){
                case "queryOne":
                case "query":
                    if(bundle.getStringArrayList("id").size() ==0 )
                        Log.i(TAG,"No data find");
                    else
                        for(int i = 0;i<bundle.getStringArrayList("id").size();i++){
                            Log.i(TAG,"id =" + bundle.getStringArrayList("id").get(i));
                            Log.i(TAG,"message =" + bundle.getStringArrayList("message").get(i));
                        };
                    break;
                case "insert":
                    break;

            }

        }
    };

    //获取连接
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

    //查询所有
    public static void query(final Connection conn){
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
                    sendBundle(resultSet,"query");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG,e.getMessage() + "");
                }
            }
        }).start();
    }
    //查找单个记录
    public static void queryOne(final Connection conn ,final int id){
        if(conn == null){
            Log.i(TAG,"connect 为空");
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("select * from message where id = ?;");
                    preparedStatement.setInt(1,id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    MysqlDBUtils.sendBundle(resultSet,"queryOne");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG,e.getMessage() + "");
                }
            }

        }).start();
    }

    //插入数据
    public static void insert(final Connection conn,final String message){
        if(conn == null){
            Log.i(TAG,"connection为空");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("insert into message value(null,?)");
                    preparedStatement.setString(1,message);
                    preparedStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //删除数据
    public static void delete(final Connection conn,final int id){
        if(conn == null){
            Log.i(TAG,"connection为空");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("delete from message where id = ?");
                    preparedStatement.setInt(1,id);
                    boolean resultSet = preparedStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //将查询后的resultSet结果集发送给handle
    private static void sendBundle(ResultSet resultSet,String methodName){
        Bundle bundle = new Bundle();
        //id列表信息存放
        ArrayList<String> idList = new ArrayList<String>();
        //message列表信息存放
        ArrayList<String> messageList = new ArrayList<String>();
        try {
            for(int i = 0;resultSet.next();i++){
                idList.add(resultSet.getString("id"));
                messageList.add(resultSet.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bundle.putStringArrayList("id",idList);
        bundle.putStringArrayList("message",messageList);
        bundle.putString("method",methodName);
        Message message = new Message();
        message.setData(bundle);
        myHandle.sendMessage(message);
    }
}
