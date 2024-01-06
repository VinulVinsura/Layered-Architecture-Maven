package bo.custom;

import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo {
    List<Customer> getAllCustomer() throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDto dto);
    boolean updateCustomer(CustomerDto dto);
    boolean deleteCustomer(String id);
    String getLastCustomerId();
}
