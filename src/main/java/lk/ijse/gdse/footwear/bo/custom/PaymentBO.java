package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.entity.Payment;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {
    PaymentDTO findById(String selectedPaymentMethod) throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
}
