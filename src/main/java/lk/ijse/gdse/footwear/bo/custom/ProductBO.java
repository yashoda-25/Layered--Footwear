package lk.ijse.gdse.footwear.bo.custom;

import lk.ijse.gdse.footwear.bo.SuperBO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

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
    ProductDTO findByProductionDescripton(String selectedProductDesc) throws SQLException, ClassNotFoundException;

  /*  public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Products SET qty = qty - ? WHERE product_Id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailsDTO.getQty());
        pstm.setString(2, orderDetailsDTO.getProductId());
        return pstm.executeUpdate() > 0;
    } */



}
