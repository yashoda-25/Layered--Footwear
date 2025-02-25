package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.entity.Product;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<Product, String> {
    Product findById(String selectedProId) throws SQLException, ClassNotFoundException;
    boolean reduceQty(String productId, int qty) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllProductsDesc() throws SQLException, ClassNotFoundException;
    Product findByProductionDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException;

}
