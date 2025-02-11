package lk.ijse.gdse.footwear.bo.custom.impl;

import lk.ijse.gdse.footwear.bo.custom.SupplierBO;
import lk.ijse.gdse.footwear.dao.DAOFactory;
import lk.ijse.gdse.footwear.dao.SQLUtil;
import lk.ijse.gdse.footwear.dao.custom.SupplierDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.SupplierDTO;
import lk.ijse.gdse.footwear.entity.Customer;
import lk.ijse.gdse.footwear.entity.Supplier;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextId();
    }

    @Override
    public boolean save(SupplierDTO entity) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(entity.getSupplierId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }

    @Override
    public ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> supplierArrayList = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();

        for (Supplier supplier : supplierArrayList) {
            supplierDTOs.add(new SupplierDTO(
                    supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getNic(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getPhoneNo()
            ));
        }
        return supplierDTOs;
    }

    @Override
    public SupplierDTO findById(String selectedSupId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.findById(selectedSupId);
        return new SupplierDTO(
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getNic(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhoneNo()
        );
    }


    @Override
    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public boolean update(SupplierDTO entity) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(entity.getSupplierId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getPhoneNo()));
    }
}
