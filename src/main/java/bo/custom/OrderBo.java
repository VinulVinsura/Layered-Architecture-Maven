package bo.custom;

import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo {
    List<Orders> getAllOrders();
    boolean placeOrder(OrderDto dto) throws SQLException;
    String lastOrder();
    
}
