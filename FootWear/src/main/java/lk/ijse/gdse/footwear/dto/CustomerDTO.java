package lk.ijse.gdse.footwear.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String customerId;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String phoneNo;
}
