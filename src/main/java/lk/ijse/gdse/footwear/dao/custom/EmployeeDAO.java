package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.entity.Employee;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee, String> {
  //  String getNextId() throws SQLException, ClassNotFoundException ;
  //  boolean save(Employee employee) throws SQLException, ClassNotFoundException;
  //  ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
    Employee findById(String selectedEmpId) throws SQLException, ClassNotFoundException;
  //  boolean update(Employee employee) throws SQLException, ClassNotFoundException;
 //   boolean delete(String employeeId) throws SQLException, ClassNotFoundException;
}
