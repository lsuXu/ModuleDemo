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

/**
 * Created by 12852 on 2017/11/27.
 * 用于不确定表结构时查询使用，即不确定表中字段，拟作为数据库查询的通用类
 */

public class DBUtilsCommon {

    private static final String TAG = "DBUtils";

    private static final String IP = "192.168.0.102";

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://" + IP  + ":3306/moduledb";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "123456";

    private static Connection connect ;

    //创建Handle,用于不同进程间的通讯传值，以及获取信息后的处理操作
    private static Handler myHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();

            //获取数据行数
            int rowCount = bundle.getInt("rowCount");
            //获取列名数组
            String [] columnNames = bundle.getStringArray("columnNames");
            //获取列数据，大小=[列数][行数]
            String [][] columnValueArrays = new String[columnNames.length][rowCount];
            for(int i = 0;i<columnNames.length;i++){
                columnValueArrays[i] = bundle.getStringArray(columnNames[i]);
            }
            for(int i=0;i<rowCount;i++){
                for(int j = 0; j < columnNames.length;j++) {
                    Log.i(TAG, columnNames[j] + "=" + columnValueArrays[j][i] + ";");
                }
            };

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

    public static void query(final Connection conn){
        if(conn == null){
            Log.i(TAG,"connect 为空");
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("select * from testCommon");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Bundle bundle = new Bundle();
                    //创建用于获取列信息和属性的对象
                    ResultSetMetaData data = resultSet.getMetaData();
                    //列行数
                    int columnCount = data.getColumnCount();
                    //用于存放列名
                    String [] columnNames = new String [columnCount];
                    //取出每一列的名称存放到ArrayList中
                    for(int i = 0;i<columnCount;i++){
                        String columnName = data.getColumnName(i+1);
                        columnNames[i] = columnName;
                    }
                    //将存放列名的ArrayList加到Bundle中
                    bundle.putStringArray("columnNames",columnNames);
                    //记录resultSet中的数据行数
                    int rowCount = 0;
                    //将resultSet指针指到最后一条记录。获取行数，再回滚至第一条数据
                    if(resultSet.last()){
                        rowCount = resultSet.getRow();
                        resultSet.beforeFirst();
                    }

                    bundle.putInt("rowCount",rowCount);
                    //创建和列数相同的ArrayList数组，保存每一列的数据
                    String[][] values = new String[columnCount][rowCount];
                    for(int i = 0;resultSet.next();i++){
                        for(int j = 0;j<columnCount;j++){
                            values[j][i] = (resultSet.getString(j+1));
                        }
                    }
                    //将每一列的名称做为key,列数据数组做为值加到bundle中
                    for(int i = 0;i<columnCount;i++) {
                        bundle.putStringArray(columnNames[i], values[i]);
                    }
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
