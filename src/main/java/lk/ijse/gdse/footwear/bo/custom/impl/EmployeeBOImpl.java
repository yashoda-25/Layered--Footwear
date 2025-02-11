package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.EmployeeBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.EmployeeDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.entity.Customer;
import lk.ijse.gdse.footwear.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    @Override
    public boolean save(EmployeeDTO entity) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(entity.getEmployeeId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employeeArrayList = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employeeArrayList) {
            employeeDTOs.add(new EmployeeDTO(
                    employee.getEmployeeId(), // Employee id
                    employee.getName(), // Name
                    employee.getNic(), // NIC
                    employee.getAddress(), // Address
                    employee.getEmail(), // Email
                    employee.getPhoneNo() // Phone No
            ));
        }
        return employeeDTOs;
    }

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllEmployeeIds();
    }

    @Override
    public EmployeeDTO findById(String selectedEmpId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.findById(selectedEmpId);
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getNic(),
                employee.getAddress(),
                employee.getEmail(),
                employee.getPhoneNo()
        );
    }

    @Override
    public boolean update(EmployeeDTO entity) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(entity.getEmployeeId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }

    @Override
    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }
}
