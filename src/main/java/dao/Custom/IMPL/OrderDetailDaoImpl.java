package dao.Custom.IMPL;

import db.DBconnection;
import dto.OrderDetailDto;
import dao.Custom.OrderDetailDao;
import entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean saveOrderDetail(List<OrderDetailDto> list) {
        boolean isSave=false;
        for (OrderDetailDto dto:list) {
            System.out.println("bingumi");
            String sql="INSERT INTO orderdetail VALUES(?,?,?,?)";
            try {
                PreparedStatement prestm = DBconnection.getInstance().getConnection().prepareStatement(sql);
                prestm.setString(1,dto.getOrderId());
                prestm.setString(2,dto.getItemCode());
                prestm.setInt(3,dto.getQty());
                prestm.setDouble(4,dto.getUnitPrice());
                if (prestm.executeUpdate()>0){
                    isSave =true;
                }


            } catch (SQLException e) {
                System.out.println("Vinul");
            } catch (ClassNotFoundException e) {
                System.out.println("Sewwandi");
            }
        }
        return isSave;
    }

    @Override
    public List<OrderDetail> getOrderDetail(String orderId) {
        String sql="SELECT * FROM orderdetail WHERE orderId=?";
        List<OrderDetail> list =new ArrayList<>();
        try {
           PreparedStatement prestm= DBconnection.getInstance().getConnection().prepareStatement(sql);
           prestm.setString(1,orderId);
           ResultSet resultSet=prestm.executeQuery();
           while (resultSet.next()){
               OrderDetail od1=new OrderDetail(resultSet.getString(1),
                       resultSet.getString(2),
                       resultSet.getInt(3),
                       resultSet.getDouble(4));
               list.add(od1);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean save(OrderDetail entity) {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(OrderDetail entity) {
        return false;
    }

    @Override
    public boolean delete(String data) {
        return false;
    }
}
