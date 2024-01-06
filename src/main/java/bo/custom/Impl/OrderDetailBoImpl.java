package bo.custom.Impl;

import bo.custom.OrderDetailBo;
import dao.Custom.IMPL.OrderDetailDaoImpl;
import dao.Custom.OrderDetailDao;
import dto.OrderDetailDto;
import entity.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    private OrderDetailDao orderDetailDao=new OrderDetailDaoImpl();
    @Override
    public List<OrderDetailDto> getOrderDetail(String orderId) {
        List<OrderDetailDto> list=new ArrayList<>();
        for (OrderDetail o1:orderDetailDao.getOrderDetail(orderId)) {
            list.add(new OrderDetailDto(o1.getOrderId(),
                    o1.getItemCode(),
                    o1.getQty(),
                    o1.getUnitPrice()));
        }
        return list;
    }
}
