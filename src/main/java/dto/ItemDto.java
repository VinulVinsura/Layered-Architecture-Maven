package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ItemDto {
    private String code;
    private String description;
    private Double unitPrice;
    private int qty;

}
