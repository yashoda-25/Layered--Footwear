package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.CustomerDAO;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.entity.Customer;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // C002, Last customer id
            System.out.println("Customer id retrieved: " + lastId); // Debug statement
            String substring = lastId.substring(1); // 002, Extract the numeric part
            int i = Integer.parseInt(substring); // 2, Convert the numeric part to integer
            int newIdIndex = i + 1; // 3, Increment the number by 1
            System.out.println("New customer id: " + newIdIndex);
            return String.format("C%03d", newIdIndex); // Return the new customer ID in  format Cnnn
        }
        System.out.println("No existing customer IDs, returning C001"); // Debug statement for empty table
        return "C001"; // Return the default customer ID if no data is found
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Customer VALUES (?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo()

        );
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");

        ArrayList<Customer> customerDTOArrayList = new ArrayList<>();

        while (rst.next()) {
                customerDTOArrayList.add(new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4), // Address
                    rst.getString(5),  // Email
                    rst.getString(6)   // Phone
                ));
        }
        return customerDTOArrayList;
    }

    @Override
    public ArrayList<String> getAllPhoneNos() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT contact_number FROM Customer");

        ArrayList<String> phoneNos = new ArrayList<>();

        while (rst.next()) {
            phoneNos.add(rst.getString(1)); //
        }

        return phoneNos;
    }


    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customer_id=?";
        return SQLUtil.execute(sql, customerId);
    }


    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Customer SET name =?, nic =?, address =?, email =?, contact_number =? WHERE customer_id =?",
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo(),
                entity.getCustomerId()
        );
    }

    @Override
    public Customer findById(String selectedPhoneNo) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE contact_number=?", selectedPhoneNo);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1), // Customer ID
                    rst.getString(2), // Name
                    rst.getString(3), // Nic
                    rst.getString(4), // Address
                    rst.getString(5), // Email
                    rst.getString(6) // PhoneNo
            );
        }
        return null;
    }


    @Override
    public Customer findCustomerByPhoneNumber(String phoneNo) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE contact_number =? ", phoneNo);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1), // Customer ID
                    rst.getString(2), // Name
                    rst.getString(3), // Nic
                    rst.getString(4), // Address
                    rst.getString(5), // Email
                    rst.getString(6) // PhoneNo
            );
        }
        return null;

    }
}
