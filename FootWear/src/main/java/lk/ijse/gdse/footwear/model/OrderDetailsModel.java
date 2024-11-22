package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {

    private final ProductModel productModel = new ProductModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        // Iterate through each order detail in the list
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            // Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // Updates the item quantity in the stock for the corresponding order detail
            boolean isProductUpdated = productModel.reduceQty(orderDetailsDTO);
            if (!isProductUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true;  // Return true if all order details are saved and item quantities updated successfully
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute("insert into orderDetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getProductId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }


}
