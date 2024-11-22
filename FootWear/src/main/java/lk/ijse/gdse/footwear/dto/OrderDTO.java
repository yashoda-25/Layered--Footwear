package lk.ijse.gdse.footwear.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private int quantity;
    //private Double amount;
    //private Double discount;
    //private String PaymentMethod;

    // A list of orderDetailsDTO objects, each representing an product in the order
    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;

}
