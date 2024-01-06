package dao.Custom.IMPL;

import com.jfoenix.controls.JFXButton;
import dao.Custom.HibernateUtil;
import db.DBconnection;
import dto.CustomerDto;
import dto.OrderDto;
import dto.tm.CustomerTB;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import dao.Custom.CustomerDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoIMPL implements CustomerDao {

    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override
    public Customer getLastCustomer() {
        String sql="SELECT * FROM customer ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = prestm.executeQuery();
            if (resultSet.next()){
                return new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(Customer entity) {
        if (entity!=null) {

            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            session.close();
            return true;
        }
        return false;

    }

    @Override
    public List<Customer> getAll() {
        String sql="SELECT * FROM Customer";
        List<Customer> tbList= new ArrayList<>();
        try {
            PreparedStatement prstm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet=prstm.executeQuery();

            while (resultSet.next()){
                Customer c1=new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );
                tbList.add(c1);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return tbList;
    }

    @Override
    public boolean update(Customer entity) {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getId());
        customer.setName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setSalary(entity.getSalary());
        session.save(customer);
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(String data) {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class,data));
        transaction.commit();
        return true;
    }
}
