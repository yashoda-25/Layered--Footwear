package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException;
  //  boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
}
