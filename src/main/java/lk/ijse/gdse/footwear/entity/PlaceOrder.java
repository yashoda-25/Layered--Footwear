package lk.ijse.gdse.footwear.entity;

import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;

import java.sql.Date;
import java.util.ArrayList;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class  PlaceOrder {
    private String orderId;
    private String customerId;
    private Date orderDate;

    // A list of orderDetailsDTO objects, each representing an product in the order
    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
