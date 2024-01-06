package bo.custom;

import dto.OrderDetailDto;
import entity.OrderDetail;

import java.util.List;

public interface OrderDetailBo {
    List<OrderDetailDto> getOrderDetail(String OrderId);
}
