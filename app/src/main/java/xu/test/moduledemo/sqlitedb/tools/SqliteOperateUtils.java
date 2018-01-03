package xu.test.moduledemo.sqlitedb.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xu.test.moduledemo.sqlitedb.bean.Btn;
import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Product;
import xu.test.moduledemo.sqlitedb.bean.Unique;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.DaoMaster;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.ProductDao;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;
import xu.test.moduledemo.sqlitedb.result.B;

/**
 * Created by 12852 on 2017/12/27.
 */

public class SqliteOperateUtils {

    private static final String TAG = "SqliteOperateUtils";

    private Context context ;

    private DaoMaster.DevOpenHelper daoHelp ;

    private SQLiteDatabase db ;

    private DaoMaster daoMaster ;

    private DaoSession daoSession ;

    private UniqueDao uniqueDao;

    private CustomerDao customerDao ;

    private BtnDao btnDao ;

    private GsonBuilder gsonBuilder;

    private ProductDao productDao ;

    public SqliteOperateUtils(Context context){
        this.context = context;
        init();
    }

    private void init(){
        daoHelp = new DaoMaster.DevOpenHelper(context,"testDb",null);
        db = daoHelp.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        btnDao = daoSession.getBtnDao();
        uniqueDao = daoSession.getUniqueDao();
        customerDao = daoSession.getCustomerDao();
        productDao = daoSession.getProductDao();
        gsonBuilder = new GsonBuilder();
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

    public String getJson(Object o){
        return gsonBuilder.create().toJson(o);
    }

    public Customer insertCustomer(Long unique_id){
        Customer customer =new Customer(null,unique_id,"customer_code","sex","age","entry_time","end_time","image_name","create_time");
        customer.setId(customerDao.insert(customer));
        return customer;
    }

    public Unique insertUnique(){
        Unique unique = new Unique(null,"unique_code","bt_mac","satrt_time","end_time","create_time");
        unique.setId(uniqueDao.insert(unique));
        return unique;
    }

    public Btn insertBtn(Long unique_id){
        Btn btn = new Btn(null,unique_id,"btn_code","click_time","product_code","create_time");
        btn.setId(btnDao.insert(btn));
        return btn;
    }

    public Product insertProduct(Long unique_id){
        Product product = new Product(null,unique_id,"产品","产品","产品","产品");
        product.setId(productDao.insert(product));
        return product;
    }

    public List<B> queryAll() {
        ArrayList<B> bList = new ArrayList<>();
        QueryBuilder<Unique> uniqueBuild = uniqueDao.queryBuilder();
        QueryBuilder<Customer> customerBuild = customerDao.queryBuilder();
        QueryBuilder<Btn> btnBuild = btnDao.queryBuilder();
        QueryBuilder<Product> productBuild = productDao.queryBuilder();
        for(Unique unique : uniqueBuild.list()){
            B b = new B();
            b.setUnique(unique);
            QueryBuilder<Customer> customers = customerBuild;
            List<Customer> customerList = customers.where(CustomerDao.Properties.Unique_id.eq(unique.getId())).list();
            for(Customer c:customerList){
                b.setCustomer(c);
            }
            QueryBuilder<Btn> btnQueryBuilder = btnBuild;
            List<Btn> btnList = btnQueryBuilder.where(BtnDao.Properties.Unique_id.eq(unique.getId())).list();
            b.setBtnList(btnList);

            QueryBuilder<Product> productQueryBuilder = productBuild;
            List<Product> productList = productQueryBuilder.where(ProductDao.Properties.Unique_id.eq(unique.getId())).list();
            b.setProductList(productList);
            bList.add(b);
        }
        return bList;
    }
}
