package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ForgotPasswordDAO {
    boolean changedPassword(String username, String confirmNewPassword) throws SQLException;

}
