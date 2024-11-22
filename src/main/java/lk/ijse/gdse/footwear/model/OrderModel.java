package lk.ijse.gdse.footwear.model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.dto.PlaceOrderDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {

    private final PaymentModel paymentModel = new PaymentModel();
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println("Order id retrieved: " + lastId);

            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            System.out.println("New order id: " + newIdIndex);

            return String.format("O%03d", newIdIndex);
        }
        System.out.println("No existing Order IDs, returning O001");
        return "O001";
    }

    public boolean saveOrderWithPayment(PlaceOrderDTO placeOrderDTO, PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // Save each orders data in the 'orders' table
            boolean isOrderSaved = CrudUtil.execute(
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

            boolean isPaymentSaved = paymentModel.savePayment(paymentDTO);
            System.out.println("Payment saved: " + isPaymentSaved);
            if (!isPaymentSaved) {
                connection.rollback();
                return false;
            }

            // Save each order detail in the 'order_details' table
            for (OrderDetailsDTO orderDetailsDTO : placeOrderDTO.getOrderDetailsDTOS()) {
                boolean isOrderDetailsSaved = CrudUtil.execute(
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


    public String getProductIdByDescription(String selectedProductDesc) throws SQLException {
     //   String productId = ""; // Initialize productId variable

        ResultSet rst = CrudUtil.execute("SELECT product_id FROM products WHERE product_description = ?", selectedProductDesc);

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
