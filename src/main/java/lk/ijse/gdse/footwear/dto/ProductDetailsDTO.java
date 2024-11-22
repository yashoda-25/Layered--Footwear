package lk.ijse.gdse.footwear.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ProductDetailsDTO {
    private String inventoryId;
    private String inventoryDescription;
    private int quantity;
    private double price;

}
