package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

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

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNo()

        );
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4), // Address
                    rst.getString(5),  // Email
                    rst.getString(6)   // Phone
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public ArrayList<String> getAllPhoneNos() throws SQLException {
        ResultSet rst = CrudUtil.execute("select contact_number from customer");

        ArrayList<String> phoneNos = new ArrayList<>();

        while (rst.next()) {
            phoneNos.add(rst.getString(1)); //
        }

        return phoneNos;
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        String sql = "delete from customer where customer_id=?";
        return CrudUtil.execute(sql, customerId);
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "update customer set name =?, nic =?, address =?, email =?, contact_number =? where customer_id =?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNo(),
                customerDTO.getCustomerId()
        );
    }

    public CustomerDTO findById(String selectedPhoneNo) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where contact_number=?", selectedPhoneNo);

        if (rst.next()) {
            return new CustomerDTO(
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

