package lk.ijse.gdse.footwear.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductTM {
    private String productId;
    private String productDescription;
    private int quantity;
    private double price;

}
