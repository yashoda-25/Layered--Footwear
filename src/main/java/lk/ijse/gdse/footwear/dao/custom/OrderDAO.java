package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.PaymentDTO;
import lk.ijse.gdse.footwear.dto.PlaceOrderDTO;
import lk.ijse.gdse.footwear.entity.PlaceOrder;
import lk.ijse.gdse.footwear.entity.Payment;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<PlaceOrderDTO, String> {
    boolean saveOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
    String getProductIdByDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException;


}
