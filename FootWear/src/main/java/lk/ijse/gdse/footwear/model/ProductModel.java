package lk.ijse.gdse.footwear.model;

import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {
    public String getNextProductId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select product_id from products order by product_id desc limit 1");

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
                "insert into products values (?,?,?,?)",
                productDTO.getProductId(),
                productDTO.getProductDescription(),
                productDTO.getQuantity(),
                productDTO.getPrice()
        );
    }

    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from products");

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        while (rst.next()) {
            ProductDTO productDTO = new ProductDTO(
                    rst.getString(1),  // Product ID
                    rst.getString(2),  // Product description
                    rst.getInt(3), // Qty
                    rst.getDouble(4)  // Price
            );
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    public ArrayList<String> getAllProductIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select product_id from products");

        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString(1));
        }

        return productIds;
    }

    public boolean deleteProduct(String productId) throws SQLException {
        String sql = "delete from products where product_id=?";
        return CrudUtil.execute(sql, productId);
    }

    public boolean updateProduct(ProductDTO productDTO) throws SQLException {
        return CrudUtil.execute(
                "update products set product_description=?, qty=?, price=? where product_id=?",
                productDTO.getProductDescription(),
                productDTO.getQuantity(),
                productDTO.getPrice(),
                productDTO.getProductId()

        );
    }

    public ProductDTO findById(String selectedProId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from products where product_id=?", selectedProId);

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

    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute("update products set qty = qty - ? where product_id =? ",
                orderDetailsDTO.getQuantity(), // Quantity to reduce
                orderDetailsDTO.getProductId() // Product ID
        );
    }
}
