package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {
    public String getNextProductId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT product_id FROM Products ORDER BY product_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println("Product id retrieved: " + lastId);

            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            System.out.println("New Product Id: " + newIdIndex);

            return String.format("P%03d",newIdIndex);
        }
        System.out.println("No existing product IDs, returning P001");
        return "P001";
    }

    public boolean saveProduct(ProductDTO productDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Products (product_id, product_description, qty, price) VALUES (?,?,?,?)",
                productDTO.getProductId(),
                productDTO.getProductDescription(),
                productDTO.getQuantity(),
                productDTO.getPrice()
        );
    }

    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Products");

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        while (rst.next()) {
            ProductDTO productDTO = new ProductDTO(
                    rst.getString(1),  // Product ID
                    rst.getString(2),  // Product description
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            );
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    public boolean deleteProduct(String productId) throws SQLException {
        String sql = "DELETE FROM Products WHERE product_id=?";
        return CrudUtil.execute(sql, productId);
    }

    public boolean updateProduct(ProductDTO productDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Products SET product_description=?, qty=?, price=? WHERE product_id=?",
                productDTO.getProductDescription(),
                productDTO.getQuantity(),
                productDTO.getPrice(),
                productDTO.getProductId()

        );
    }

    public ProductDTO findById(String selectedProId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Products WHERE product_id=?", selectedProId);

        if (rst.next()) {
            return new ProductDTO(
                    rst.getString(1),  // Product ID
                    rst.getString(2),  // Product description
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            );
        }
        return null;
    }

  /*  public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Products SET qty = qty - ? WHERE product_Id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailsDTO.getQty());
        pstm.setString(2, orderDetailsDTO.getProductId());
        return pstm.executeUpdate() > 0;
    } */


    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute("UPDATE Products SET qty = qty - ? WHERE product_id =? ",
                orderDetailsDTO.getQty(),  // Quantity to reduce
                orderDetailsDTO.getProductId() // Product ID
        );
    }


    public ArrayList<String> getAllProductsDesc() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT product_description FROM Products");

        ArrayList<String> productDescriptions = new ArrayList<>();

        while (rst.next()) {
            productDescriptions.add(rst.getString(1));
        }
        return productDescriptions;
    }


    public ProductDTO findByProductionDescripton(String selectedProductDesc) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT product_id, product_description, qty, price FROM Products WHERE product_description=?", selectedProductDesc);

        if (rst.next()) {
            return new ProductDTO(
                    rst.getString(1), // Product ID
                    rst.getString(2), // Product Desc
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            );
        }
        return null;
    }
}
