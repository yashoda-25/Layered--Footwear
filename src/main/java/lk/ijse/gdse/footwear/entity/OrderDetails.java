package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetails {
    private String orderId;
    private String productId;
    private String productDescription;
    private int qty;
    private double price;
    private double total;
}
