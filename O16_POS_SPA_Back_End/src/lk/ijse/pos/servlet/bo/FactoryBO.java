package lk.ijse.pos.servlet.bo;

import lk.ijse.pos.servlet.bo.custom.impl.CustomerBOImpl;

public class FactoryBO {
    private static FactoryBO factoryBO;

    private FactoryBO() {
    }

    public static FactoryBO getFactoryBO() {
        if (factoryBO == null) {
            return factoryBO = new FactoryBO();
        } else {
            return factoryBO;
        }
    }
    public SuperBO getInstance(BOTypes types) {
        switch (types) {
            case Customer:
                return new CustomerBOImpl();
            case Item:
                return null;
            default:
                return null;
        }
    }
}
