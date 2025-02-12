package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.PaymentBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.PaymentDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.entity.Customer;
import lk.ijse.gdse.footwear.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public PaymentDTO findById(String selectedPaymentMethod) throws SQLException, ClassNotFoundException {
        PaymentDTO payment = paymentDAO.findById(selectedPaymentMethod);

        if (payment == null) {
            return null;
        }
        return new PaymentDTO(
                payment.getPaymentID(),
                payment.getAmount(),
                payment.getDiscount(),
                payment.getPaymentMethod(),
                payment.getDate(),
                payment.getOrderID()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    @Override
    public boolean save(PaymentDTO entity) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new PaymentDTO(entity.getPaymentID(),entity.getAmount(),entity.getDiscount(),entity.getPaymentMethod(),entity.getDate(),entity.getOrderID()));
    }
}
