package lk.ijse.gdse.footwear.bo;

import lk.ijse.gdse.footwear.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        CUSTOMER,PRODUCT,ORDER,SUPPLIER,EMPLOYEE,ORDER_DETAILS,PAYMENT,
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsBOImpl();
            default:
                return null;

        }
    }
}
