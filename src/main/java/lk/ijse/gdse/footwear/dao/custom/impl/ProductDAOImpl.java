package lk.ijse.gdse.footwear.dao.custom.impl;

import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.entity.Product;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT product_id FROM Products ORDER BY product_id DESC LIMIT 1");

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

    @Override
    public boolean save(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Products (product_id, product_description, qty, price) VALUES (?,?,?,?)",
                entity.getProductId(),
                entity.getProductDescription(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    @Override
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Products");

        ArrayList<Product> productDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            productDTOArrayList.add(new Product(
                    rst.getString(1),  // Product ID
                    rst.getString(2),  // Product description
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            ));

        }
        return productDTOArrayList;
    }

    @Override
    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Products WHERE product_id=?";
        return SQLUtil.execute(sql, productId);
    }

    @Override
    public boolean update(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Products SET product_description=?, qty=?, price=? WHERE product_id=?",
                entity.getProductDescription(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getProductId()

        );
    }

    @Override
    public Product findById(String selectedProId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Products WHERE product_id=?", selectedProId);

        if (rst.next()) {
            return new Product(
                    rst.getString(1),  // Product ID
                    rst.getString(2),  // Product description
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            );
        }
        return null;
    }


    @Override
    public boolean reduceQty(String productId, int qty) throws SQLException, ClassNotFoundException {
        // Execute SQL query to update the item quantity in the database
        return SQLUtil.execute("UPDATE Products SET qty = qty - ? WHERE product_id =? ", qty, productId);
             //   orderDetailsDTO.getQty(),  // Quantity to reduce
              //  orderDetailsDTO.getProductId() // Product ID

    }

    @Override
    public ArrayList<String> getAllProductsDesc() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT product_description FROM Products");

        ArrayList<String> productDescriptions = new ArrayList<>();

        while (rst.next()) {
            productDescriptions.add(rst.getString(1));
        }
        return productDescriptions;
    }

    @Override
    public Product findByProductionDescription(String selectedProductDesc) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT product_id, product_description, qty, price FROM Products WHERE product_description=?", selectedProductDesc);

        if (rst.next()) {
            return new Product(
                    rst.getString(1), // Product ID
                    rst.getString(2), // Product Desc
                    rst.getInt(3), // Qty
                    rst.getDouble(4) // Price

            );
        }
        return null;
    }
}
