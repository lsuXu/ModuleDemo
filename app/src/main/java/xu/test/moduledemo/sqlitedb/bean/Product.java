package xu.test.moduledemo.sqlitedb.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 12852 on 2017/12/15.
 */
public class Product {


    /**
     * product_code : 1
     * pick_up_time : 1381420600
     * let_down_time : 1381420600
     * create_time : 1381420600
     */
    private Long id;
    private Long unique_id;
    private String product_code;
    private String pick_up_time;
    private String let_down_time;
    private String create_time;

}
