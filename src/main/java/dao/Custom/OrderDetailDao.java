package dao.Custom;

import dao.CrudDao;
import dto.OrderDetailDto;
import entity.OrderDetail;

import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetail> {
    boolean saveOrderDetail(List<OrderDetailDto> list);
    List<OrderDetail> getOrderDetail(String OrderId);
}
