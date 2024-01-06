package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class PlaceOrderTB {
    private String Code;
    private  String descrip;
    private int qty;
    private double amount;
    private JFXButton button;
}
