package dao.Custom;

import dao.CrudDao;
import dto.CustomerDto;
import dto.tm.CustomerTB;
import entity.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerDao extends CrudDao<Customer> {
    CustomerDto searchCustomer(String id);
    Customer getLastCustomer();
}
