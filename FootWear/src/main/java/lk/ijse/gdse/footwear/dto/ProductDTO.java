package lk.ijse.gdse.footwear.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    private String productId;
    private String productDescription;
    private int quantity;
    private double price;

}
