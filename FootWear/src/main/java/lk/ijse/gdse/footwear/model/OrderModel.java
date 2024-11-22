package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.OrderDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {

    // A references to the OrderDetailsModel to handle order details saving
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

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

    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // Saves the order details into the orders table
            boolean isOrderSaved = CrudUtil.execute(
                    "INSERT INTO orders (order_id, customer_id, qty, date) VALUES (?,?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getQuantity(),
                    orderDTO.getOrderDate()
                 //   orderDTO.getAmount(),
                 //   orderDTO.getDiscount(),
                  //  orderDTO.getPaymentMethod()
            );
            System.out.println("Order saved status: " + isOrderSaved);

            if (isOrderSaved) {
                // Saves the list of order detail
                boolean isOrderDetailsSaved = orderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                System.out.println("Order details saved status: " + isOrderDetailsSaved);

                if (isOrderDetailsSaved) {
                    // Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    System.out.println("Order Details saved");
                    return true;
                }
            }
            // Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            System.out.println("Rolling back due to failure in saving order details");
            return false;

        }catch (Exception e) {
            e.printStackTrace();
            // Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        }finally {
            // Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }
}
