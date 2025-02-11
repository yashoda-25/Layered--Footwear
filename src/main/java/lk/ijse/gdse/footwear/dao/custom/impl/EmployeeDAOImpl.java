package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.EmployeeDAO;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.entity.Employee;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println("Employee id retrieved: " + lastId);

            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            System.out.println("New Employee Id: " + newIdIndex);

            return String.format("E%03d",newIdIndex);
        }
        System.out.println("No existing employee IDs, returning E001");
        return "E001";
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Employee (employee_id, name, nic, address, email, contact_number) VALUES (?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo()
        );
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");

        ArrayList<Employee> employeeDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            employeeDTOArrayList.add(new Employee(
                    rst.getString(1), // Employee id
                    rst.getString(2), // Name
                    rst.getString(3), // NIC
                    rst.getString(4), // Address
                    rst.getString(5), // Email
                    rst.getString(6) // Phone No
            ));
        }
        return employeeDTOArrayList;
    }

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    @Override
    public Employee findById(String selectedEmpId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee WHERE employee_id=?", selectedEmpId);

        if (rst.next()) {
            return new Employee(
                    rst.getString(1),  // Employee ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Address
                    rst.getString(5),  // Email
                    rst.getString(6)   // PhoneNo
            );
        }
        return null;
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Employee SET name=?, nic=?, address=?, email=?, contact_number=? WHERE employee_id=?",
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo(),
                entity.getEmployeeId()
        );
    }

    @Override
    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employee WHERE employee_id=?";
        return SQLUtil.execute(sql, employeeId);
    }
}
