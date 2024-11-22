package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import static lk.ijse.gdse.footwear.util.CrudUtil.execute;

public class PaymentModel {

    public PaymentDTO findById(String selectedPaymentMethod) throws SQLException {
        ResultSet rst = execute("SELECT * FROM payment WHERE pay_method =?", selectedPaymentMethod);

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


    public String getNextPaymentId() throws SQLException {
        ResultSet rst = execute("SELECT pay_id FROM Payment ORDER BY pay_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println("Payment id retrieved: " + lastId);

            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            System.out.println("New payment id: " + newIdIndex);

            return String.format("P%03d", newIdIndex);
        }
        System.out.println("No existing Payment IDs, returning P001");
        return "P001";
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        boolean isInserted = CrudUtil.execute(
                "INSERT INTO Payment (pay_id, amount, discount, pay_method, date, order_id) VALUES (?,?,?,?,?,?)",
                paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),
                paymentDTO.getDiscount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getDate(),
                paymentDTO.getOrderID()
        );

        // Return true if the insertion was successful
        return isInserted;
    }
}
