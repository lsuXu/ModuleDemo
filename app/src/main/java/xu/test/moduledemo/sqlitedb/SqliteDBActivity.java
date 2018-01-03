package xu.test.moduledemo.sqlitedb;

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xu.test.moduledemo.R;
import xu.test.moduledemo.sqlitedb.bean.Btn;
import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Unique;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.DaoMaster;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;
import xu.test.moduledemo.sqlitedb.result.B;
import xu.test.moduledemo.sqlitedb.tools.SqliteOperateUtils;

/**
 * Created by 12852 on 2017/12/13.
 */

public class SqliteDBActivity extends AppCompatActivity{

    private static final String TAG = "SqliteDBActivity";

    @BindView(R.id.insertBtnBtn)
    android.widget.Button insertBtnBtn ;

    @BindView(R.id.insertCustomerBtn)
    android.widget.Button insertCustomerBtn ;

    @BindView(R.id.insertProductBtn)
    Button insertProductBtn ;

    @BindView(R.id.queryAllBtn)
    Button queryAllBtn;

    private Unique unique ;

    private SqliteOperateUtils sqliteOperateUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sqlite_db_activity);
        ButterKnife.bind(this);
        sqliteOperateUtils = new SqliteOperateUtils(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                             Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                    }
                    , 1);
        }

    }


    @OnClick({R.id.insertCustomerBtn,R.id.insertProductBtn,R.id.insertBtnBtn,R.id.queryAllBtn})
    public void onClick(View v) {
        if(unique == null)
            unique = sqliteOperateUtils.insertUnique();
        switch(v.getId()){
            case R.id.insertCustomerBtn:
                sqliteOperateUtils.insertCustomer(unique.getId());
                break;
            case R.id.insertProductBtn:
                sqliteOperateUtils.insertProduct(unique.getId());
                break;
            case R.id.insertBtnBtn:
                sqliteOperateUtils.insertBtn(unique.getId());
                break;
            case R.id.queryAllBtn:
                queryAll();
                break;
        }
    }

    private String  queryAll(){
        List<B> list = sqliteOperateUtils.queryAll();
        for(B b :list){
            Log.i("queryAll",sqliteOperateUtils.getJson(list));
        }
        return null;
    }

}
