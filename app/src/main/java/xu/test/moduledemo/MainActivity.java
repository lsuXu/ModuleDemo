package xu.test.moduledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.mysqldb.MysqlDBActivity;
import xu.test.moduledemo.rxjavaTest.RXMainActivity;
import xu.test.moduledemo.sqlitedb.SqliteDBActivity;

public class MainActivity extends AppCompatActivity {

    Button mysqlDbBtn ;
    Button sqliteDbBtn ;

    @BindView(R.id.rxTestBtn)
    Button rxTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
            }
            startActivity(intent);
        }
    }
}
