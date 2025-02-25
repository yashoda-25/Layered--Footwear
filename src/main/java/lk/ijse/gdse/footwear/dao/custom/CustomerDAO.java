package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    ArrayList<String> getAllPhoneNos() throws SQLException, ClassNotFoundException;
    Customer findById(String selectedPhoneNo) throws SQLException, ClassNotFoundException;
    Customer findCustomerByPhoneNumber(String phoneNo) throws SQLException, ClassNotFoundException;


}
