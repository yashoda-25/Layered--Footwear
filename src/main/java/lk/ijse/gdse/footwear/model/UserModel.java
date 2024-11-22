package lk.ijse.gdse.footwear.model;


import javafx.scene.control.Alert;
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserModel {
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        String sql = "INSERT INTO user (name, user_name, email, password) VALUES(?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        if (connection == null) {
            new Alert(Alert.AlertType.ERROR, "Fail to connect to the database").show();
            return false;
        }

        PreparedStatement pst = connection.prepareStatement(sql);
     //   pst.setObject(1,userDTO.getUser_id()); // Set user_id
        pst.setObject(1, userDTO.getName()); // Set name
        pst.setObject(2, userDTO.getUser_name()); // Set user_name
        pst.setObject(3, userDTO.getEmail()); // Set email
        pst.setObject(4, userDTO.getPassword()); // Set password

        return pst.executeUpdate() > 0;
    }


}
