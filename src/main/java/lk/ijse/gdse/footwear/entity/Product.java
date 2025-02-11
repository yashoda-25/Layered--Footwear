package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    private String productId;
    private String productDescription;
    private int quantity;
    private double price;

    //  private ArrayList<ProductDetailsDTO> productDetailsDTOS;

}
