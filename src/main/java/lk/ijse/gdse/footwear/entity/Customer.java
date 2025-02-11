package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Customer {
    private String customerId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String phoneNo;
}
