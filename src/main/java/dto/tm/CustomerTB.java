package dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTB {
    private String id;
    private String name;
    private String address;
    private double salary;
    private Button button;

}
