package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.OrderBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.OrderDAO;
import lk.ijse.gdse.footwear.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.footwear.dao.custom.PaymentDAO;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.dto.PlaceOrderDTO;
import lk.ijse.gdse.footwear.entity.PlaceOrder;
import lk.ijse.gdse.footwear.entity.Payment;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    //OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAILS);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextId();
    }

    @Override
    public boolean saveOrderWithPayment(PlaceOrderDTO placeOrderDTO, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // Save each orders data in the 'orders' table
            boolean isOrderSaved = orderDAO.save(placeOrderDTO);
            System.out.println("Order saved: " + isOrderSaved);

            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            boolean isPaymentSaved = paymentDAO.save(paymentDTO);
            System.out.println("Payment saved: " + isPaymentSaved);
            if (!isPaymentSaved) {
                connection.rollback();
                return false;
            }

            System.out.println(placeOrderDTO.getOrderDetailsDTOS());

            // Save each order detail in the 'order_details' table
            for (OrderDetailsDTO orderDetailsDTO : placeOrderDTO.getOrderDetailsDTOS()) {
                boolean isOrderDetailsSaved = orderDAO.saveOrderDetails(orderDetailsDTO);
                System.out.println("Order details saved: " + isOrderDetailsSaved);

                if (!isOrderDetailsSaved) {
                    connection.rollback(); // Rollback transaction if any detail insertion fails
                    return false;
                }

                boolean isProductUpdated = productDAO.reduceQty(orderDetailsDTO.getProductId(), orderDetailsDTO.getQty());

                if (!isProductUpdated) {
                    connection.rollback(); // Rollback transaction if any detail insertion fails
                    return false;
                }
            }

            // Commit the transaction if both the order and its details are successfully saved
            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Transaction failed: " + e.getMessage());
            // Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }


    @Override
    public String getProductIdByDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException {
        return orderDAO.getProductIdByDescription(selectedProductDesc);
    }
}
