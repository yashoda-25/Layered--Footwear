package lk.ijse.gdse.footwear.dto;

import lk.ijse.gdse.footwear.dto.tm.CartTM;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsDTO  {
    private String orderId;
    private String productId;
    private String productDescription;
    private int qty;
    private double price;
    private double total;

}
