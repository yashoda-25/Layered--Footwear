package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int user_id;
    private String name;
    private String user_name;
    private String email;
    private String password;
}
