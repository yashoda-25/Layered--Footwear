package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.SupplierDAO;
import lk.ijse.gdse.footwear.dto.SupplierDTO;
import lk.ijse.gdse.footwear.entity.Supplier;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println(("Supplier id retrieved: " + lastId));

            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            System.out.println(("Supplier id: " + newIdIndex));
            return String.format("S%03d", newIdIndex);
        }
        System.out.println(" No existing supplier IDs, returning S001");
        return "S001";
    }

    @Override
    public boolean save(Supplier entity ) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Supplier (supplier_id, name, nic, address, email, contact_number) VALUES(?,?,?,?,?,?)",
                entity.getSupplierId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo()
        );
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");

        ArrayList<Supplier> supplierDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            supplierDTOArrayList.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return supplierDTOArrayList;
    }

    @Override
    public Supplier findById(String selectedSupId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE supplier_id=?", selectedSupId);

        if (rst.next()) {
            return new Supplier(
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


    @Override
    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Supplier WHERE supplier_id=?";
        return SQLUtil.execute(sql, supplierId);
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Supplier SET name=?, nic=?, address=?, email=?, contact_number=? WHERE supplier_id=?",
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhoneNo(),
                entity.getSupplierId()
        );
    }
}
