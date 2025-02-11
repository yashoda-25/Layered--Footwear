package lk.ijse.gdse.footwear.dao;

import lk.ijse.gdse.footwear.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        CUSTOMER,PRODUCT,ORDER,SUPPLIER,EMPLOYEE,ORDER_DETAILS,PAYMENT
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case SUPPLIER:
                return new  SupplierDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            default:
                return null;

        }
    }
}
