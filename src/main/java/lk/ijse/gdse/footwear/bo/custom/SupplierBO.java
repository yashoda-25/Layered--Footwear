package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException;
    SupplierDTO findById(String selectedSupId) throws SQLException, ClassNotFoundException;
    boolean delete(String supplierId) throws SQLException, ClassNotFoundException;
    boolean update(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
}
