package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.entity.Employee;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee, String> {
    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
    Employee findById(String selectedEmpId) throws SQLException, ClassNotFoundException;

}
