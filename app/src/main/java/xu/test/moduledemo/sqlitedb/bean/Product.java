package xu.test.moduledemo.sqlitedb.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 12852 on 2017/12/15.
 */
@Entity
public class Product {


    /**
     * product_code : 1
     * pick_up_time : 1381420600
     * let_down_time : 1381420600
     * create_time : 1381420600
     */
    @Id(autoincrement = true)
    private Long id;
    //唯一标识Id
    private Long unique_id;
    //产品标志
    private String product_code;
    //拿起时间
    private String pick_up_time;
    //放下时间
    private String let_down_time;
    //记录创建时间
    private String create_time;
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getLet_down_time() {
        return this.let_down_time;
    }
    public void setLet_down_time(String let_down_time) {
        this.let_down_time = let_down_time;
    }
    public String getPick_up_time() {
        return this.pick_up_time;
    }
    public void setPick_up_time(String pick_up_time) {
        this.pick_up_time = pick_up_time;
    }
    public String getProduct_code() {
        return this.product_code;
    }
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    public Long getUnique_id() {
        return this.unique_id;
    }
    public void setUnique_id(Long unique_id) {
        this.unique_id = unique_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1796524932)
    public Product(Long id, Long unique_id, String product_code,
            String pick_up_time, String let_down_time, String create_time) {
        this.id = id;
        this.unique_id = unique_id;
        this.product_code = product_code;
        this.pick_up_time = pick_up_time;
        this.let_down_time = let_down_time;
        this.create_time = create_time;
    }
    @Generated(hash = 1890278724)
    public Product() {
    }

}
