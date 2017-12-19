package xu.test.moduledemo.sqlitedb.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 12852 on 2017/12/15.
 */
@Entity
public class Btn {

    /**
     * button_code : b1
     * click_time : 1381420600
     * product_code : 1
     * create_time : 1381420600
     */
    @Id(autoincrement = true)
    private Long id;
    //唯一标识
    private Long unique_id;
    //按钮标识
    private String button_code;
    //点击时间
    private String click_time;
    //产品标识
    private String product_code;
    //记录创建时间
    private String create_time;
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getProduct_code() {
        return this.product_code;
    }
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    public String getClick_time() {
        return this.click_time;
    }
    public void setClick_time(String click_time) {
        this.click_time = click_time;
    }
    public String getButton_code() {
        return this.button_code;
    }
    public void setButton_code(String button_code) {
        this.button_code = button_code;
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
    @Generated(hash = 17106343)
    public Btn(Long id, Long unique_id, String button_code, String click_time,
            String product_code, String create_time) {
        this.id = id;
        this.unique_id = unique_id;
        this.button_code = button_code;
        this.click_time = click_time;
        this.product_code = product_code;
        this.create_time = create_time;
    }
    @Generated(hash = 308065284)
    public Btn() {
    }

    @Override
    public String toString() {
        return "Btn{" +
                "id=" + id +
                ", unique_id=" + unique_id +
                ", button_code='" + button_code + '\'' +
                ", click_time='" + click_time + '\'' +
                ", product_code='" + product_code + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
