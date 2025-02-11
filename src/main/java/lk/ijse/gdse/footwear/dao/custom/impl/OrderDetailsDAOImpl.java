package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.entity.OrderDetails;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
      /*  for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
                System.out.println("Error: order_id or product_id is null.");
                return false;
               // throw new IllegalArgumentException("Error: order_id or product_id cannot be null.");
            }

            System.out.println("Processing: " + orderDetailsDTO);
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                System.out.println("Failed to save order details for: " + orderDetailsDTO.getProductId());
                return false;
            }

            boolean isProductUpdated = productDAO.reduceQty(orderDetailsDTO.getProductId(),orderDetailsDTO.getQty());
            if (!isProductUpdated) {
                System.out.println("Failed to update stock for productId: " + orderDetailsDTO.getProductId());
                return false;
            }
        }
        return true;*/
        return false;
    }

    @Override
    public boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {

       /* if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
           // throw new IllegalArgumentException("Error: order_id or product_id is null.");
            System.out.println("Error: order_id or product_id is null.");
            return false;
        }

        String query = "INSERT INTO OrderDetails (order_id, product_id, product_description, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Execute the query with the provided DTO values
            boolean isSaved = SQLUtil.execute(
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
        }*/
        String query = "INSERT INTO OrderDetails (order_id, product_id, product_description, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";
        return SQLUtil.execute(
                query,
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getProductId(),
                orderDetailsDTO.getProductDescription(),
                orderDetailsDTO.getQty(),
                orderDetailsDTO.getPrice(),
                orderDetailsDTO.getTotal()
        );
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
        /*ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

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
        return "O001";*/
    }
}
