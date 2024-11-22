package lk.ijse.gdse.footwear.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SupplierDTO {
    private String supplierId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String phoneNo;
}
