package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.bo.custom.CustomerBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.custom.CustomerDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    @Override
    public boolean save(CustomerDTO entity) throws SQLException, ClassNotFoundException {
       /* Customer customer = new Customer(
                entity.getCustomerId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo()
        );
        return customerDAO.save(customer);*/
       return customerDAO.save(new Customer(entity.getCustomerId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerArrayList = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customerArrayList) {
            customerDTOs.add(new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getNic(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getPhoneNo()
            ));
        }
        return customerDTOs;
    }

    @Override
    public ArrayList<String> getAllPhoneNos() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllPhoneNos();
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean update(CustomerDTO entity) throws SQLException, ClassNotFoundException {
       /* Customer customer = new Customer(
                entity.getCustomerId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo()
        );
        return customerDAO.update(customer);*/
        return customerDAO.update(new Customer(entity.getCustomerId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }

    @Override
    public CustomerDTO findById(String selectedPhoneNo) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.findById(selectedPhoneNo);
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getNic(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhoneNo()
        );
    }

    @Override
    public CustomerDTO findCustomerByPhoneNumber(String phoneNo) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.findCustomerByPhoneNumber(phoneNo);
        if (customer == null) {
            return null; // or throw an exception
        }
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getNic(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhoneNo()
        );
    }
}
