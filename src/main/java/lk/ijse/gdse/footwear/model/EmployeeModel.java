package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    /*public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");

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

    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Employee (employee_id, name, nic, address, email, contact_number) VALUES (?,?,?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getAddress(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNo()
        );
    }

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee");

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1), // Employee id
                    rst.getString(2), // Name
                    rst.getString(3), // NIC
                    rst.getString(4), // Address
                    rst.getString(5), // Email
                    rst.getString(6) // Phone No
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public ArrayList<String> getAllEmployeeIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM Employee");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    public EmployeeDTO findById(String selectedEmpId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE employee_id=?", selectedEmpId);

        if (rst.next()) {
            return new EmployeeDTO(
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

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Employee SET name=?, nic=?, address=?, email=?, contact_number=? WHERE employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getAddress(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNo(),
                employeeDTO.getEmployeeId()
        );
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employee_id=?";
        return CrudUtil.execute(sql, employeeId);
    }*/
}