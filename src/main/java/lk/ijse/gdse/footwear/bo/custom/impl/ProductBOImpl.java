package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.ProductBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.ProductDAO;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.OrderDetailsDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.entity.Customer;
import lk.ijse.gdse.footwear.entity.Product;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return productDAO.getNextId();
    }

    @Override
    public boolean save(ProductDTO entity) throws SQLException, ClassNotFoundException {
        return productDAO.save(new Product(entity.getProductId(),entity.getProductDescription(),entity.getQuantity(),entity.getPrice()));
    }

    @Override
    public ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Product> productsArrayList = productDAO.getAll();
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : productsArrayList) {
            productDTOs.add(new ProductDTO(
                    product.getProductId(),
                    product.getProductDescription(),
                    product.getQuantity(),
                    product.getPrice()

            ));
        }
        return productDTOs;
    }

    @Override
    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return productDAO.delete(productId);
    }

    @Override
    public boolean update(ProductDTO entity) throws SQLException, ClassNotFoundException {
        return productDAO.update(new Product(entity.getProductId(),entity.getProductDescription(),entity.getQuantity(),entity.getPrice()));
    }

    @Override
    public ProductDTO findById(String selectedProId) throws SQLException, ClassNotFoundException {
        Product product = productDAO.findById(selectedProId);
        return new ProductDTO(
                product.getProductId(),
                product.getProductDescription(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        // Execute SQL query to update the item quantity in the database
        return productDAO.reduceQty(orderDetailsDTO.getProductId(), orderDetailsDTO.getQty());
    }

    @Override
    public ArrayList<String> getAllProductsDesc() throws SQLException, ClassNotFoundException {
        return productDAO.getAllProductsDesc();
    }

    @Override
    public ProductDTO findByProductionDescripton(String selectedProductDesc) throws SQLException, ClassNotFoundException {
        Product product = productDAO.findByProductionDescripton(selectedProductDesc);

        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getProductId(),
                product.getProductDescription(),
                product.getQuantity(),
                product.getPrice()
        );
    }
}
