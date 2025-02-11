package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.OrderBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.OrderDAO;
import lk.ijse.gdse.footwear.dao.custom.PaymentDAO;
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

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextId();
    }



   /* @Override
    public boolean saveOrderWithPayment(PlaceOrderDTO placeOrderDTO, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        // Calculate the total amount and apply discount
       /* double totalAmount = 0.0;
        for (OrderDetailsDTO orderDetail : placeOrderDTO.getOrderDetailsDTOS()) {
            totalAmount += orderDetail.getPrice() * orderDetail.getQty();
        }

        // Apply discount to the total amount
        double discountedTotal = totalAmount * (1 - paymentDTO.getDiscount() / 100);

        // Update the paymentDTO with the correct amount and discounted total
        paymentDTO.setAmount(totalAmount);
        paymentDTO.setDiscount(paymentDTO.getDiscount());
        paymentDTO.setPaymentMethod(paymentDTO.getPaymentMethod());

        PlaceOrder placeOrder = new PlaceOrder(
            placeOrderDTO.getOrderId(),
            placeOrderDTO.getCustomerId(),
            placeOrderDTO.getOrderDate(),
            placeOrderDTO.getOrderDetailsDTOS()
        );

        Payment payment = new Payment(
                paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),
                paymentDTO.getDiscount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getDate(),
                paymentDTO.getOrderID()
        );
        return orderDAO.saveOrderWithPayment(placeOrderDTO,paymentDTO);
    }*/

    @Override
    public boolean saveOrderWithPayment(PlaceOrderDTO placeOrderDTO, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // Save each orders data in the 'orders' table
            boolean isOrderSaved = SQLUtil.execute(
                    "INSERT INTO Orders (order_id, customer_id, date) VALUES (?,?,?)",
                    placeOrderDTO.getOrderId(),
                    placeOrderDTO.getCustomerId(),
                    placeOrderDTO.getOrderDate()

            );
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

            // Save each order detail in the 'order_details' table
            for (OrderDetailsDTO orderDetailsDTO : placeOrderDTO.getOrderDetailsDTOS()) {
                boolean isOrderDetailsSaved = SQLUtil.execute(
                        "INSERT INTO OrderDetails (order_id, product_id, product_description, qty, price, total) VALUES (?,?,?,?,?,?)",
                        orderDetailsDTO.getOrderId(),
                        orderDetailsDTO.getProductId(),
                        orderDetailsDTO.getProductDescription(),
                        orderDetailsDTO.getQty(),
                        orderDetailsDTO.getPrice(),
                        orderDetailsDTO.getTotal()
                );
                System.out.println("Order details saved: " + isOrderDetailsSaved);

                if (!isOrderDetailsSaved) {
                    connection.rollback(); // Rollback transaction if any detail insertion fails
                    return false;
                }
            }

            // Commit the transaction if both the order and its details are successfully saved
            connection.commit();
            return true;

        } catch (Exception e) {
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
        //   String productId = ""; // Initialize productId variable

        ResultSet rst = SQLUtil.execute("SELECT product_id FROM products WHERE product_description = ?", selectedProductDesc);

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
