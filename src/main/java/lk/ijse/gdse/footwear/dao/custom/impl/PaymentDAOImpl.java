package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.PaymentDAO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.entity.Payment;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public PaymentDTO findById(String selectedPaymentMethod) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment WHERE pay_method =?", selectedPaymentMethod);

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

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT pay_id FROM Payment ORDER BY pay_id DESC LIMIT 1");
       // ResultSet rst = SQLUtil.execute("SELECT pay_id FROM Payment ORDER BY LENGTH(pay_id) DESC, pay_id DESC LIMIT 1");

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

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(PaymentDTO entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Payment (pay_id, amount, discount, pay_method, date, order_id) VALUES (?,?,?,?,?,?)",
                entity.getPaymentID(),
                entity.getAmount(),
                entity.getDiscount(),
                entity.getPaymentMethod(),
                entity.getDate(),
                entity.getOrderID()
        );
    }

    @Override
    public boolean update(PaymentDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }


}
