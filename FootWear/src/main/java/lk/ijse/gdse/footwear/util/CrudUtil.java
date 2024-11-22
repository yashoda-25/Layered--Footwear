package lk.ijse.gdse.footwear.util;

import lk.ijse.gdse.footwear.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    // SQL query එකක් execute කිරීම සඳහා generic method එකක්. (Create, Read, Update, Delete) with the database.
    public static <T>T execute(String sql,Object... obj) throws SQLException {
        // A generic method to execute SQL queries, with a flexible return type `T`.
        // It accepts an SQL statement and a variable number of parameters (obj) to insert into the SQL query.
        // Database connection එකක් ගන්නවා.
        Connection connection = DBConnection.getInstance().getConnection();

        // Prepared statement එකට SQL query එක set කරනවා.
        PreparedStatement pst = connection.prepareStatement(sql);
        // Iterates through the variable arguments (obj) and sets them in the prepared statement in place of the placeholders (?) in the SQL query.
        // The loop starts from 0, but `setObject` expects positions starting from 1, hence (i + 1).

        // SQL query එකේ placeholders (?) replace කිරීමට argument set කරනවා.
        for (int i=0;i<obj.length;i++){
            pst.setObject((i+1),obj[i]); // SQL query එකේ (i + 1) ස්ථානයට object එක set කරනවා.
        }

        // SQL query එක SELECT නම්, result එකක් ලැබෙන විධියට execute කිරීම.
        if (sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        }else {
            // SELECT query නොවේ නම්, INSERT/UPDATE/DELETE වගේ query එකක් ලෙස execute කිරීම.
            int i = pst.executeUpdate();
            boolean isSaved = i >0; // Row එකක් update වූ විට true otherwise false.
            return (T) ((Boolean) isSaved);
        }
    }
}
