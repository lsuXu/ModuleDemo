package xu.test.moduledemo.sqlitedb.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;

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

    private Long unique_id;
    //按钮标识
    private String button_code;
    //点击时间
    private String click_time;
    //产品标识
    private String product_code;
    //记录创建时间
    private String create_time;

    @ToOne(joinProperty = "unique_id")
    private Unique unique ;

    @Generated(hash = 575787175)
    private transient Long unique__resolvedKey;

    /** Used for active entity operations. */
    @Generated(hash = 758912384)
    private transient BtnDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
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
    @Generated(hash = 1465672213)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBtnDao() : null;
    }
}
