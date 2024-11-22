package lk.ijse.gdse.footwear.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserDTO{
    private int user_id;
    private String name;
    private String user_name;
    private String email;
    private String password;
}
