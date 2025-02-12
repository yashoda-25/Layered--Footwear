package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.entity.OrderDetails;


import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails, String> {
    boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
   // boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException;

}
