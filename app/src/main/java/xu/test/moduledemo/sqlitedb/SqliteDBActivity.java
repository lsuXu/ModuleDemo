package xu.test.moduledemo.sqlitedb;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xu.test.moduledemo.R;
import xu.test.moduledemo.sqlitedb.bean.Btn;
import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Unique;
import xu.test.moduledemo.sqlitedb.daotools.ADao;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.DaoMaster;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;
import xu.test.moduledemo.sqlitedb.result.A;

/**
 * Created by 12852 on 2017/12/13.
 */

public class SqliteDBActivity extends AppCompatActivity{

    @BindView(R.id.insertBtn)
    android.widget.Button insertBtn ;

    @BindView(R.id.queryAllBtn)
    android.widget.Button queryAllBtn ;

    private static String TAG = "SqliteDBActivity";

    private DaoMaster.DevOpenHelper daoHelp ;

    private SQLiteDatabase db ;

    private DaoMaster daoMaster ;

    private DaoSession daoSession ;

    private UniqueDao uniqueDao;

    private CustomerDao customerDao ;

    private BtnDao btnDao ;

    private ADao aDao ;

    private GsonBuilder gsonBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sqlite_db_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        daoHelp = new DaoMaster.DevOpenHelper(this,"testDb",null);
        db = daoHelp.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        btnDao = daoSession.getBtnDao();
        aDao = daoSession.getADao();
        uniqueDao = daoSession.getUniqueDao();
        customerDao = daoSession.getCustomerDao();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().contains(("id"))||fieldAttributes.getName().contains("uniqueId")||fieldAttributes.getName().contains("createTime");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
    }

    @OnClick({R.id.queryAllBtn,R.id.insertBtn})
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.queryAllBtn:
                Log.i(TAG,"查询所有");
                QueryBuilder<A> aQueryBuilder = aDao.queryBuilder();
                //连表查询
                Log.i(TAG,"------------------");
                for(A a : aQueryBuilder.list()) {
                    Gson gson = gsonBuilder.create();
                    //不加这句json封装后不展示对象
                    a.setUnique(a.getUnique());
                    a.setCustomer(a.getCustomer());
                    String aString = gson.toJson(a);
                    Log.i(TAG, aString);
                }
                break;
            case R.id.insertBtn:
                Log.i(TAG,"增加数据");
                Date date = new Date();
                try {
                    //插入唯一标识
                    Unique unique = new Unique(null,"1101","bt_mac","20171210","20171213",date.getTime()+"");
                    long uniqueId = uniqueDao.insert(unique);
                    //将唯一标识的id作为ButtonHit的外键插入ButtonHit表
                    Btn btn = new Btn(null,uniqueId,"123", "ss", "ss", date.getTime()+"");
                    long btnId = btnDao.insert(btn);
                    Log.i(TAG, "column:" + btnId);

                    Customer customer = new Customer(null,uniqueId,"customer_code","1","18","188541254","195215412","customer_code",date.getTime()+"");
                    long customerId = customerDao.insert(customer);
                    Log.i(TAG, "column:" + customerId);

                    //记录中间信息的表
                    A a = new A(null,uniqueId,"" + date.getTime());
                    a.setCreateTime("123" + date.getTime());
                    long aId = aDao.insert(a);
                    Log.i(TAG,"column:" + aId);

                }catch(Exception e){
                    Log.i(TAG,e.getMessage());
                }
                break;
        }
    }
}
