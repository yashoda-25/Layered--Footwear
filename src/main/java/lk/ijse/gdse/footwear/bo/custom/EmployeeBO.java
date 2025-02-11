package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
    EmployeeDTO findById(String selectedEmpId) throws SQLException, ClassNotFoundException;
    boolean update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    boolean delete(String employeeId) throws SQLException, ClassNotFoundException;
}
