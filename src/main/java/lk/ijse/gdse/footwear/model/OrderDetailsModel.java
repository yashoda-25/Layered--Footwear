package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final ProductModel productModel = new ProductModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
                System.out.println("Error: order_id or product_id is null.");
                return false;
            }

            System.out.println("Processing: " + orderDetailsDTO);
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                System.out.println("Failed to save order details for: " + orderDetailsDTO.getProductId());
                return false;
            }

            boolean isProductUpdated = productModel.reduceQty(orderDetailsDTO);
            if (!isProductUpdated) {
                System.out.println("Failed to update stock for productId: " + orderDetailsDTO.getProductId());
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {

        if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
            System.out.println("Error: order_id or product_id is null.");
            return false;
        }

        String query = "INSERT INTO OrderDetails (order_id, product_id, product_description, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Execute the query with the provided DTO values
            boolean isSaved = CrudUtil.execute(
                    query,
                    orderDetailsDTO.getOrderId(),
                    orderDetailsDTO.getProductId(),
                    orderDetailsDTO.getProductDescription(),
                    orderDetailsDTO.getQty(),
                    orderDetailsDTO.getPrice(),
                    orderDetailsDTO.getTotal()
            );

            return isSaved; // Return the status of execution
        } catch (SQLException sqlException) {
            // Log SQL-related exceptions with a meaningful message
            System.err.println("Error saving order details to the database: " + sqlException.getMessage());
            sqlException.printStackTrace();
            return false; // Return false if an SQL exception occurs
        } catch (Exception exception) {
            // Log other unexpected exceptions
            System.err.println("Unexpected error while saving order details: " + exception.getMessage());
            exception.printStackTrace();
            return false; // Return false if a generic exception occurs
        }
    }
}

