package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgotPasswordModel {
    public boolean changedPassword(String username, String confirmNewPassword) throws SQLException {
        String sql = "UPDATE user SET password=? WHERE user_name=?";

        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, confirmNewPassword);
        pst.setString(2, username);

        return pst.executeUpdate() > 0;
    }
}
