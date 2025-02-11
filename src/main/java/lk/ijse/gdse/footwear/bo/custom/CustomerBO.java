package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllPhoneNos() throws SQLException, ClassNotFoundException;
    boolean delete(String customerId) throws SQLException, ClassNotFoundException;
    boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    CustomerDTO findById(String selectedPhoneNo) throws SQLException, ClassNotFoundException;
    CustomerDTO findCustomerByPhoneNumber(String phoneNo) throws SQLException, ClassNotFoundException;
}
