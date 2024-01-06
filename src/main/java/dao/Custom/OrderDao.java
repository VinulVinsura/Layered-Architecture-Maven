package dao.Custom;

import dao.CrudDao;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends CrudDao<Orders> {
    boolean placeOrder(OrderDto dto) throws SQLException;
    OrderDto lastOrder();

}
