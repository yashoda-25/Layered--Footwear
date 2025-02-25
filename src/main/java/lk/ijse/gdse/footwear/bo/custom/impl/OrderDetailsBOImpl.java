package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAILS);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            if (orderDetailsDTO.getOrderId() == null || orderDetailsDTO.getProductId() == null) {
                System.out.println("Error: order_id or product_id is null.");
                return false;
            }

            System.out.println("Processing: " + orderDetailsDTO);
            boolean isOrderDetailsSaved = orderDetailsDAO.saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                System.out.println("Failed to save order details for: " + orderDetailsDTO.getProductId());
                return false;
            }

            boolean isProductUpdated = productDAO.reduceQty(orderDetailsDTO.getProductId(), orderDetailsDTO.getQty());
            if (!isProductUpdated) {
                System.out.println("Failed to update stock for productId: " + orderDetailsDTO.getProductId());
                return false;
            }
        }
        return true;

    }
}