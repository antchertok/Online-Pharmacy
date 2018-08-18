package by.chertok.pharmacy.instance;

import by.chertok.pharmacy.service.*;
import by.chertok.pharmacy.service.impl.*;

/**
 * Auxiliary class providing access to service objects
 */
public class ServiceKeeper {
    private DrugService drugService;
    private OrderService orderService;
    private PrescriptionService prescriptionService;
    private UserService userService;

    private ServiceKeeper(DaoKeeper daoKeeper) {
        drugService = new DrugServiceImpl(daoKeeper.getDrugDao());
        orderService = new OrderServiceImpl(daoKeeper.getOrderDao());
        prescriptionService = new PrescriptionServiceImpl(daoKeeper.getPrescriptionDao());
        userService = new UserServiceImpl(daoKeeper.getUserDao());
    }

    public DrugService getDrugService() {
        return drugService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PrescriptionService getPrescriptionService() {
        return prescriptionService;
    }

    public UserService getUserService() {
        return userService;
    }

    public static ServiceKeeper getInstance() {
        return ServiceInstanceHolder.INSTANCE;
    }

    private static class ServiceInstanceHolder {
        private static final ServiceKeeper INSTANCE = new ServiceKeeper(DaoKeeper.getInstance());
    }
}