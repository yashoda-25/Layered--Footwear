package lk.ijse.gdse.footwear.dto;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {
    private String paymentID;
    private double amount;
    private double discount;
    private String paymentMethod;
    private Date date;
    private String orderID;

}
