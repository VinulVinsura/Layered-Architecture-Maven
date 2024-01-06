package bo.custom.Impl;

import bo.custom.CustomerBo;
import dao.Custom.CustomerDao;
import dao.Custom.IMPL.CustomerDaoIMPL;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao =new CustomerDaoIMPL();
    @Override
    public List<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        return customerDao.getAll();
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return customerDao.save(new Customer(dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        return customerDao.update(new Customer(dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }

    @Override
    public String getLastCustomerId() {
        if (customerDao.getLastCustomer() !=null){
            String id= customerDao.getLastCustomer().getId();
            int num= Integer.parseInt(id.split("C")[1]);
            num++;
            return String.format("C%03d",num);
        }else {
            return "C001";
        }
    }


}
