package xu.test.moduledemo.sqlitedb.result;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import org.greenrobot.greendao.DaoException;

import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Unique;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.ADao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;

/**
 * Created by 12852 on 2017/12/18.
 */
@Entity
public class A {
    @Id(autoincrement = true)
    private Long id;

    private Long uniqueId ;

    private String createTime;
    @ToOne(joinProperty = "uniqueId")
    private Unique unique;

    @ToOne(joinProperty = "uniqueId")
    private Customer customer ;

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
    @Generated(hash = 1132397195)
    public void setCustomer(Customer customer) {
        synchronized (this) {
            this.customer = customer;
            uniqueId = customer == null ? null : customer.getId();
            customer__resolvedKey = uniqueId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1686514643)
    public Customer getCustomer() {
        Long __key = this.uniqueId;
        if (customer__resolvedKey == null || !customer__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CustomerDao targetDao = daoSession.getCustomerDao();
            Customer customerNew = targetDao.load(__key);
            synchronized (this) {
                customer = customerNew;
                customer__resolvedKey = __key;
            }
        }
        return customer;
    }

    @Generated(hash = 8592637)
    private transient Long customer__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 180845565)
    public void setUnique(Unique unique) {
        synchronized (this) {
            this.unique = unique;
            uniqueId = unique == null ? null : unique.getId();
            unique__resolvedKey = uniqueId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1438892712)
    public Unique getUnique() {
        Long __key = this.uniqueId;
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

    @Generated(hash = 575787175)
    private transient Long unique__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 296909522)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getADao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 438824226)
    private transient ADao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1464439503)
    public A(Long id, Long uniqueId, String createTime) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.createTime = createTime;
    }

    @Generated(hash = 2349630)
    public A() {
    }


}
