package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.SupplierDTO;
import lk.ijse.gdse.footwear.entity.Supplier;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier, String> {
   // String getNextId() throws SQLException;
  //  boolean save(Supplier supplier) throws SQLException;
  //  ArrayList<Supplier> getAll() throws SQLException;
    Supplier findById(String selectedSupId) throws SQLException, ClassNotFoundException;
   // boolean delete(String supplierId) throws SQLException, ClassNotFoundException;
   // boolean update(Supplier supplier) throws SQLException, ClassNotFoundException;
}
