package xu.test.moduledemo.sqlitedb.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 12852 on 2017/12/15.
 */
@Entity
public class Unique {


    /**
     * unique_code : u1
     * bt_mac : 00:B0:0C:A3:9B:55
     * start_time : 1381420600
     * end_time : 1381420600
     * create_time : 1381420600
     */
    @Id(autoincrement = true)
    private Long id;
    private String unique_code;
    private String bt_mac;
    private String start_time;
    private String end_time;
    private String create_time;
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getEnd_time() {
        return this.end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getStart_time() {
        return this.start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getBt_mac() {
        return this.bt_mac;
    }
    public void setBt_mac(String bt_mac) {
        this.bt_mac = bt_mac;
    }
    public String getUnique_code() {
        return this.unique_code;
    }
    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1616477320)
    public Unique(Long id, String unique_code, String bt_mac, String start_time,
            String end_time, String create_time) {
        this.id = id;
        this.unique_code = unique_code;
        this.bt_mac = bt_mac;
        this.start_time = start_time;
        this.end_time = end_time;
        this.create_time = create_time;
    }
    @Generated(hash = 145655764)
    public Unique() {
    }

}
