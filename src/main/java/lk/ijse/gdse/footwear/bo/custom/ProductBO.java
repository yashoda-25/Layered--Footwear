package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(ProductDTO productDTO) throws SQLException, ClassNotFoundException;
    ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException;
    boolean update(ProductDTO productDTO) throws SQLException, ClassNotFoundException;
    boolean delete(String productId) throws SQLException, ClassNotFoundException;
    ProductDTO findById(String selectedProId) throws SQLException, ClassNotFoundException;
    boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllProductsDesc() throws SQLException, ClassNotFoundException;
    ProductDTO findByProductionDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException;




}
