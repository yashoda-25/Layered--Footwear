package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.dto.PlaceOrderDTO;
import lk.ijse.gdse.footwear.entity.Payment;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean saveOrderWithPayment(PlaceOrderDTO placeOrderDTO, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    String getProductIdByDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException;
}
