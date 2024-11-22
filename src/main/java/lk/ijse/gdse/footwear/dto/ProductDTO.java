package lk.ijse.gdse.footwear.dto;

import lombok.*;

import java.util.ArrayList;

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

  //  private ArrayList<ProductDetailsDTO> productDetailsDTOS;

}
