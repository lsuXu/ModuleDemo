package xu.test.moduledemo.sqlitedb;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.google.gson.Gson;
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
import xu.test.moduledemo.sqlitedb.daotools.ADao;
import xu.test.moduledemo.sqlitedb.daotools.BDao;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.DaoMaster;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;
import xu.test.moduledemo.sqlitedb.tools.BDeserializer;
import xu.test.moduledemo.sqlitedb.result.A;
import xu.test.moduledemo.sqlitedb.result.B;
import xu.test.moduledemo.sqlitedb.tools.util;

/**
 * Created by 12852 on 2017/12/13.
 */

public class SqliteDBActivity extends AppCompatActivity{

    @BindView(R.id.insertBtn)
    android.widget.Button insertBtn ;

    @BindView(R.id.queryAllBtn)
    android.widget.Button queryAllBtn ;

    @BindView(R.id.insertBtnB)
    Button insertBtnB ;

    @BindView(R.id.queryAllBtnB)
    Button queryAllBtnB;

    private static String TAG = "SqliteDBActivity";

    private DaoMaster.DevOpenHelper daoHelp ;

    private SQLiteDatabase db ;

    private DaoMaster daoMaster ;

    private DaoSession daoSession ;

    private UniqueDao uniqueDao;

    private CustomerDao customerDao ;

    private BtnDao btnDao ;

    private ADao aDao ;

    private BDao bDao ;

    private GsonBuilder gsonBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sqlite_db_activity);
        ButterKnife.bind(this);
        init();
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

    private void init(){
        daoHelp = new DaoMaster.DevOpenHelper(this,"testDb",null);
        db = daoHelp.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        btnDao = daoSession.getBtnDao();
        aDao = daoSession.getADao();
        uniqueDao = daoSession.getUniqueDao();
        customerDao = daoSession.getCustomerDao();
        bDao = daoSession.getBDao();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(B.class,new BDeserializer());
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                boolean b = fieldAttributes.getName().contains(("id"))||fieldAttributes.getName().contains("uniqueId")||fieldAttributes.getName().contains("createTime");
                return b;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
    }

    @OnClick({R.id.queryAllBtn,R.id.insertBtn,R.id.insertBtnB,R.id.queryAllBtnB})
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.queryAllBtn:
                queryAllA();
                break;
            case R.id.insertBtn:
                insertA();
                break;
            case R.id.insertBtnB:
                insertB();
                break;
            case R.id.queryAllBtnB:
                queryAllB();
                break;
        }
    }

    private void queryAllA(){
        Log.i(TAG,"查询所有A");
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
    }
    private void insertA(){
        Log.i(TAG,"增加数据A");
        Date date = new Date();
        long seconds = date.getTime()/1000;
        try {
            //插入唯一标识
            Unique unique = new Unique(null,"1101","bt_mac","20171210","20171213",seconds+"");
            long uniqueId = uniqueDao.insert(unique);
            //将唯一标识的id作为ButtonHit的外键插入ButtonHit表
            Btn btn = new Btn(null,uniqueId,"123", "ss", "ss", seconds+"");
            long btnId = btnDao.insert(btn);
            Log.i(TAG, "column:" + btnId);

            Customer customer = new Customer(null,uniqueId,"customer_code","1","18","188541254","195215412","customer_code",seconds+"");
            long customerId = customerDao.insert(customer);
            Log.i(TAG, "column:" + customerId);

            //记录中间信息的表
            A a = new A(null,uniqueId,"" + seconds);
            a.setCreateTime("123" + seconds);
            long aId = aDao.insert(a);
            Log.i(TAG,"column:" + aId);

        }catch(Exception e){
            Log.i(TAG,e.getMessage());
        }
    }

    private void insertB(){
        Log.i(TAG,"插入数据B");
        Date date = new Date();
        //插入唯一标识
        Unique unique = new Unique(null,"1101","bt_mac","1513598839","1513598840",date.getTime()/1000+"");
        long uniqueId = uniqueDao.insert(unique);

        //插入用户数据
        Customer customer = new Customer(null,uniqueId,"Alice","1","18","1513598839","1513598840","heheda.jpg",date.getTime()/1000 + "");
        Log.i(TAG,"column :" + customerDao.insert(customer));

        for(int i = 0; i < 3;i++){
            Btn btn = new Btn(null,uniqueId,"button_code"+ i,"1513598839" + i,"product_code" + i,date.getTime()/1000 + "");
            Log.i(TAG,"id="+ btnDao.insert(btn));
        }
        B b = new B(null,uniqueId,1);
        bDao.insert(b);
    }

    private void queryAllB(){
        Log.i(TAG,"查询所有B");
        QueryBuilder<B> bQueryBuilder = bDao.queryBuilder();
        //连表查询
        Log.i(TAG,"------------------");
        for(B b : bQueryBuilder.list()) {
            Gson gson = gsonBuilder.create();
            /*Log.i(TAG,"**********    start    *************");
            //不加这句json封装后不展示对象
            List<Btn> btns = b.getBtnList();
            for(int i = 0; i < btns.size();i++){
                Log.i(TAG,btns.toString());
            };
            Log.i(TAG,"**********    end    *************");*/
            List<Btn> btns = b.getBtnList();
            try {
                b.getProductList();
                b.getCustomer();
                Log.i(TAG,b.getCustomer() + "");
                b.getUnique();
                String aString = gson.toJson(b);
                Log.i(TAG,"写文件");
                util.saveLogFile(aString);
                Log.i(TAG, aString);
            }catch (Exception e){
                Log.i(TAG,e.getMessage());
            }

        }
    }
}
