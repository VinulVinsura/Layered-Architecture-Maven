package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class OrderTB {
    private String orderId;
    private  String date;
    private String  customerId;
    private JFXButton button;

}
