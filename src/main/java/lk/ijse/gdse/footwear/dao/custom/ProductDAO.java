package lk.ijse.gdse.footwear.dao.custom;

import lk.ijse.gdse.footwear.dao.CrudDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.entity.Product;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<Product, String> {
  //  String getNextId() throws SQLException, ClassNotFoundException;
 //   boolean save(Product product) throws SQLException, ClassNotFoundException;
 //   ArrayList<Product> getAll() throws SQLException, ClassNotFoundException;
 //   boolean delete(String productId) throws SQLException, ClassNotFoundException;
 //   boolean update(Product product) throws SQLException;
    Product findById(String selectedProId) throws SQLException, ClassNotFoundException;
    boolean reduceQty(String productId, int qty) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllProductsDesc() throws SQLException, ClassNotFoundException;
    Product findByProductionDescripton(String selectedProductDesc) throws SQLException, ClassNotFoundException;



  /*  public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Products SET qty = qty - ? WHERE product_Id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailsDTO.getQty());
        pstm.setString(2, orderDetailsDTO.getProductId());
        return pstm.executeUpdate() > 0;
    } */



}
