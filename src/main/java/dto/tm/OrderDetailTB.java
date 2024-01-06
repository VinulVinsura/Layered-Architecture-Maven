package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailTB {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
