package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.OrderDAO;
import lk.ijse.gdse.footwear.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.entity.OrderDetails;


import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {



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
    }


    @Override
    public boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
            System.out.println("Error: order_id or product_id is null.");
            return false;
        }

        String query = "INSERT INTO OrderDetails (order_id, product_id, product_description, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Execute the query with the provided DTO values
            return SQLUtil.execute(
                    query,
                    orderDetailsDTO.getOrderId(),
                    orderDetailsDTO.getProductId(),
                    orderDetailsDTO.getProductDescription(),
                    orderDetailsDTO.getQty(),
                    orderDetailsDTO.getPrice(),
                    orderDetailsDTO.getTotal()
            );


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
