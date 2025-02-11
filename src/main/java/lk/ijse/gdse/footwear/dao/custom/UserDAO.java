package lk.ijse.gdse.footwear.dao.custom;

import javafx.scene.control.Alert;
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserDAO {
    boolean saveUser(UserDTO userDTO) throws SQLException;
}
