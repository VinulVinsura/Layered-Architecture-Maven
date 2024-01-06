package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemTB {
    private String code;
    private String description;
    private Double unitPrice;
    private int qty;
    private JFXButton button;
}
