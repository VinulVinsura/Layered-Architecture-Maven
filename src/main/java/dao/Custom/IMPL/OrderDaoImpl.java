package dao.Custom.IMPL;

import dao.Custom.HibernateUtil;
import db.DBconnection;
import dto.OrderDto;
import dao.Custom.OrderDetailDao;
import dao.Custom.OrderDao;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private OrderDetailDao orderDetailDao =new OrderDetailDaoImpl();

    @Override
    public boolean placeOrder(OrderDto dto) throws SQLException {
        boolean isOrderSave=false;
        Connection connection = null;
        try {
            connection=DBconnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql="INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement prestm=connection.prepareStatement(sql);
            prestm.setString(1,dto.getOrderId());
            prestm.setString(2, dto.getDate());
            prestm.setString(3,dto.getCustId());

            if (prestm.executeUpdate()>0){
                boolean isDetailSave= orderDetailDao.saveOrderDetail(dto.getList());
                if (isDetailSave){
                    connection.commit();
                    isOrderSave=true;

                }
            }

        } catch (Exception ex) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);

        }

        return isOrderSave;
    }

    @Override
    public OrderDto lastOrder() {
         String sql="SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = prestm.executeQuery();
            if (resultSet.next()){
                return new OrderDto(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public boolean save(Orders entity) {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        /* Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM customer");
        List list1 = query.list(); */      // Hibernate Use


        String sql="SELECT * FROM Orders";
        List<Orders> list=new ArrayList<>();
        PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet=prestm.executeQuery();
        while (resultSet.next()){
            Orders orders=new Orders(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            list.add(orders);
        }
        return list;
    }



    @Override
    public boolean update(Orders entity) {
        return false;
    }

    @Override
    public boolean delete(String data) {
        return false;
    }
}

