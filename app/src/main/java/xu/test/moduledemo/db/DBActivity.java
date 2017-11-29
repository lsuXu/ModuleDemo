package xu.test.moduledemo.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;

import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2017/11/27.
 */

public class DBActivity extends AppCompatActivity {

    private static final String TAG = "DBActivity";
    Connection connection;

    private Button findAllBtn;
    private Button insertBtn;
    private Button deleteBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_activity);
        initView();
        initClickListener();
        //获取数据库连接，多线程方式访问网络
        connection = DBUtils.getConnection();
    }

    private void initView(){
        findAllBtn = (Button) findViewById(R.id.findAllBtn);
        insertBtn = (Button) findViewById(R.id.insertBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
    }

    private void initClickListener(){
        findAllBtn.setOnClickListener(new CustomClickListener());
        insertBtn.setOnClickListener(new CustomClickListener());
        deleteBtn.setOnClickListener(new CustomClickListener());
    }


    private class CustomClickListener implements View.OnClickListener{

        DBUtils dbUtils = new DBUtils();

        @Override
        public void onClick(View v) {
            if(connection == null){
                connection = dbUtils.getConnection();
            }
            switch(v.getId()){
                case R.id.findAllBtn:
                    DBUtilsCommon.query(connection);
                    Log.i(TAG,"findAll");
                    break;
                case R.id.insertBtn:
                    Log.i(TAG,"insert");
                    break;
                case R.id.deleteBtn:
                    Log.i(TAG,"delete");
                    break;
            }
        }
    }

}
