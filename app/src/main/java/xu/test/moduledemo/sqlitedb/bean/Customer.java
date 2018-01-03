package xu.test.moduledemo.sqlitedb.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;

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
    // 唯一标识id
    private Long unique_id;
    //顾客标识
    private String customer_code;
    //性别
    private String sex;
    //年龄
    private String age;
    //顾客进入时间
    private String entry_time;
    //顾客离开时间
    private String end_time;
    //对应图片名称
    private String img_name;
    //记录创建时间
    private String create_time;
    @ToOne(joinProperty = "unique_id")
    private Unique unique ;
    @Generated(hash = 575787175)
    private transient Long unique__resolvedKey;
    /** Used for active entity operations. */
    @Generated(hash = 1697251196)
    private transient CustomerDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
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
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2111974403)
    public void setUnique(Unique unique) {
        synchronized (this) {
            this.unique = unique;
            unique_id = unique == null ? null : unique.getId();
            unique__resolvedKey = unique_id;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 403720596)
    public Unique getUnique() {
        Long __key = this.unique_id;
        if (unique__resolvedKey == null || !unique__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UniqueDao targetDao = daoSession.getUniqueDao();
            Unique uniqueNew = targetDao.load(__key);
            synchronized (this) {
                unique = uniqueNew;
                unique__resolvedKey = __key;
            }
        }
        return unique;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462117449)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCustomerDao() : null;
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
