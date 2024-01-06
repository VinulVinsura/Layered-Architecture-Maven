package dto;


import dto.tm.OrderTB;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDto {
    private String orderId;
    private String date;
    private String custId;
    private List <OrderDetailDto> list;
}
