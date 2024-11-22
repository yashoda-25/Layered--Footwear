package lk.ijse.gdse.footwear.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierTM {
    private String supplierId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String phoneNo;
}
