package xu.test.moduledemo.sqlitedb.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 12852 on 2017/12/15.
 */
@Entity
public class Customer {

    /**
     * customer_code : p2
     * sex : 0
     * age : 35
     * entry_time : 1381420600
     * end_time : 1381420600
     * img_name : p2.img
     * create_time : 1381420600
     */
    @Id(autoincrement = true)
    private Long id;
    private Long unique_id;
    private String customer_code;
    private String sex;
    private String age;
    private String entry_time;
    private String end_time;
    private String img_name;
    private String create_time;
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getImg_name() {
        return this.img_name;
    }
    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }
    public String getEnd_time() {
        return this.end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getEntry_time() {
        return this.entry_time;
    }
    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getCustomer_code() {
        return this.customer_code;
    }
    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
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
    @Generated(hash = 1721677567)
    public Customer(Long id, Long unique_id, String customer_code, String sex,
            String age, String entry_time, String end_time, String img_name,
            String create_time) {
        this.id = id;
        this.unique_id = unique_id;
        this.customer_code = customer_code;
        this.sex = sex;
        this.age = age;
        this.entry_time = entry_time;
        this.end_time = end_time;
        this.img_name = img_name;
        this.create_time = create_time;
    }
    @Generated(hash = 60841032)
    public Customer() {
    }
  
}
