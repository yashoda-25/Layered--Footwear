package lk.ijse.gdse.footwear.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsDTO {
    private String orderId;
    private String productId;
    private int quantity;
    private double price;
}
