package xu.test.moduledemo.sqlitedb.result;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import xu.test.moduledemo.sqlitedb.bean.Btn;

import org.greenrobot.greendao.annotation.ToOne;

import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Product;
import xu.test.moduledemo.sqlitedb.bean.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import xu.test.moduledemo.sqlitedb.daotools.DaoSession;
import xu.test.moduledemo.sqlitedb.daotools.BDao;
import xu.test.moduledemo.sqlitedb.daotools.CustomerDao;
import xu.test.moduledemo.sqlitedb.daotools.UniqueDao;
import xu.test.moduledemo.sqlitedb.daotools.BtnDao;
import xu.test.moduledemo.sqlitedb.daotools.ProductDao;

/**
 * Created by 12852 on 2017/12/18.
 */

@Entity
public class B {
    @Id(autoincrement = true)
    private Long id ;
    private Long unique_id;
    //0:没有进入周期，1:进入周期
    private int status ;

    @ToOne(joinProperty = "unique_id")
    private Customer customer;

    @ToOne(joinProperty = "unique_id")
    private Unique unique;

    //此处的unique_id是
    @ToMany(referencedJoinProperty = "unique_id")
    private List<Btn> btnList;

    @ToMany(referencedJoinProperty = "unique_id")
    private List<Product> productList;

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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 489242486)
    public synchronized void resetProductList() {
        productList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 148330128)
    public List<Product> getProductList() {
        if (productList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductDao targetDao = daoSession.getProductDao();
            List<Product> productListNew = targetDao._queryB_ProductList(id);
            synchronized (this) {
                if(productList == null) {
                    productList = productListNew;
                }
            }
        }
        return productList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1414010162)
    public synchronized void resetBtnList() {
        btnList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 512120818)
    public List<Btn> getBtnList() {
        if (btnList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BtnDao targetDao = daoSession.getBtnDao();
            List<Btn> btnListNew = targetDao._queryB_BtnList(id);
            synchronized (this) {
                if(btnList == null) {
                    btnList = btnListNew;
                }
            }
        }
        return btnList;
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

    @Generated(hash = 575787175)
    private transient Long unique__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1029207098)
    public void setCustomer(Customer customer) {
        synchronized (this) {
            this.customer = customer;
            unique_id = customer == null ? null : customer.getId();
            customer__resolvedKey = unique_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 345339730)
    public Customer getCustomer() {
        Long __key = this.unique_id;
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
    @Generated(hash = 929946539)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 1410285858)
    private transient BDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Generated(hash = 167264219)
    public B() {
    }

    @Generated(hash = 1664582815)
    public B(Long id, Long unique_id, int status) {
        this.id = id;
        this.unique_id = unique_id;
        this.status = status;
    }

}
