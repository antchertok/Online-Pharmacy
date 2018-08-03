package main.java.by.chertok.pharmacy.instance;

import main.java.by.chertok.pharmacy.service.*;
import main.java.by.chertok.pharmacy.service.impl.*;

/**
 * Auxiliary class providing access to service objects
 */
public class ServiceInstance {

    private DrugService drugService;
    private OrderService orderService;
    private PrescriptionService prescriptionService;
    private UserService userService;

    private ServiceInstance(DaoInstance daoInstance) {
        drugService = new DrugServiceImpl(daoInstance.getDrugDao());
        orderService = new OrderServiceImpl(daoInstance.getOrderDao());
        prescriptionService = new PrescriptionServiceImpl(daoInstance.getPrescriptionDao());
        userService = new UserServiceImpl(daoInstance.getUserDao());
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

    public static synchronized ServiceInstance getInstance() {

//        if (ServiceInstanceHolder.INSTANCE == null) {
//            initServiceInstance(DaoInstance.getInstance());
//        }
        return ServiceInstanceHolder.INSTANCE;
    }

    public static void initServiceInstance(DaoInstance daoInstance) {
        ServiceInstanceHolder.INSTANCE = new ServiceInstance(daoInstance);
    }

    private static class ServiceInstanceHolder {
        private static ServiceInstance INSTANCE;
    }
}
