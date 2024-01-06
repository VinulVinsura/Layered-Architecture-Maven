package bo.custom.Impl;

import bo.custom.OrderBo;
import dao.Custom.IMPL.OrderDaoImpl;
import dao.Custom.OrderDao;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    OrderDao orderDao=new OrderDaoImpl();

    @Override
    public List<Orders> getAllOrders() {
        try {
            return orderDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean placeOrder(OrderDto dto) throws SQLException {
        return orderDao.placeOrder(dto);
    }

    @Override
    public String lastOrder() {
        if (orderDao.lastOrder() !=null){
            String id= orderDao.lastOrder().getOrderId();
            int num= Integer.parseInt(id.split("D")[1]);
            num++;
            return String.format("D%03d",num);
        }else {
            return "D001";
        }

    }


}

