package xu.test.moduledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xu.test.moduledemo.db.DBActivity;

public class MainActivity extends AppCompatActivity {

    Button dbModuleBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initOnClickEvent();
    }

    private void initView(){
        dbModuleBtn = (Button) findViewById(R.id.dbmodule);
    }

    private void initOnClickEvent(){
        dbModuleBtn.setOnClickListener(new CustomerOnclick());

    }

    public class CustomerOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch(v.getId()){
                case R.id.dbmodule:
                    intent.setClass(MainActivity.this,DBActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
