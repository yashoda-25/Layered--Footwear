package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Supplier {
    private String supplierId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String phoneNo;
}
