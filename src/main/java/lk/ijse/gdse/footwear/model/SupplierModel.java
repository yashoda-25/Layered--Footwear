package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.dto.SupplierDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    /*public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println(("Supplier id retrieved: " + lastId));

            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            System.out.println(("Supplier id: " + newIdIndex));
            return String.format("C%03d", newIdIndex);
        }
        System.out.println(" No existing supplier IDs, returning S001");
        return "S001";
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Supplier (supplier_id, name, nic, address, email, contact_number) VALUES(?,?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getPhoneNo()
        );
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public SupplierDTO findById(String selectedSupId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE supplier_id=?", selectedSupId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Address
                    rst.getString(5),  // Email
                    rst.getString(6)   // PhoneNo
            );
        }
        return null;
    }


    public boolean deleteSupplier(String supplierId) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE supplier_id=?";
        return CrudUtil.execute(sql, supplierId);
    }

    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Supplier SET name=?, nic=?, address=?, email=?, contact_number=? WHERE supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getPhoneNo(),
                supplierDTO.getSupplierId()
        );
    }*/
}
