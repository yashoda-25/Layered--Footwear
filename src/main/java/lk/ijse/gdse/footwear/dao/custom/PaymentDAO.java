package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.entity.Payment;


import java.sql.ResultSet;
import java.sql.SQLException;



public interface PaymentDAO extends CrudDAO<PaymentDTO, String> {
    PaymentDTO findById(String selectedPaymentMethod) throws SQLException, ClassNotFoundException;
  //  String getNextId() throws SQLException, ClassNotFoundException;
   // boolean save(Payment payment) throws SQLException, ClassNotFoundException;
}