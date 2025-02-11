package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.custom.ForgotPasswordDAO;
import lk.ijse.gdse.footwear.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
    public boolean changedPassword(String username, String confirmNewPassword) throws SQLException {
        String sql = "UPDATE user SET password=? WHERE user_name=?";

        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, confirmNewPassword);
        pst.setString(2, username);

        return pst.executeUpdate() > 0;
    }
}
