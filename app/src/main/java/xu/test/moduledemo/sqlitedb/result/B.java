package xu.test.moduledemo.sqlitedb.result;

import java.util.List;

import xu.test.moduledemo.sqlitedb.bean.Btn;

import xu.test.moduledemo.sqlitedb.bean.Customer;
import xu.test.moduledemo.sqlitedb.bean.Product;
import xu.test.moduledemo.sqlitedb.bean.Unique;

/**
 * Created by 12852 on 2017/12/18.
 */

public class B {

    private Customer customer;

    private Unique unique;

    private List<Btn> btnList;

    private List<Product> productList;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Unique getUnique() {
        return unique;
    }

    public void setUnique(Unique unique) {
        this.unique = unique;
    }

    public List<Btn> getBtnList() {
        return btnList;
    }

    public void setBtnList(List<Btn> btnList) {
        this.btnList = btnList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
