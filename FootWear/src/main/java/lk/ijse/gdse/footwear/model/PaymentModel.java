package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentModel {
    public PaymentDTO findById(String selectedPaymentMethod) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment where pay_method =?", selectedPaymentMethod);

        if (rst.next()) {
            return new PaymentDTO(
                    rst.getString(1),  // Payement ID
                    rst.getDouble(2),  // Amount
                    rst.getDouble(3),  // Discount
                    rst.getString(4),  // Payment Method
                    rst.getDate(5),  // Date
                    rst.getString(6)   // Order ID
            );
        }
        return null;
    }
}
