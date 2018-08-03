package main.java.by.chertok.pharmacy.command.impl.customer;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.AddDrugCommand;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.service.PrescriptionService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class AddToCardCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private DrugService drugService;
    private PrescriptionService prescriptionService;

    public AddToCardCommand(DrugService drugService, PrescriptionService prescriptionService) {
        this.drugService = drugService;
        this.prescriptionService = prescriptionService;
    }

    /**
     * If successful, adds given drug to user's order. Checks it's existence and
     * availability for current user
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            Order order = (Order) wrapper.getSessionAttribute("order");
            long drugId = Long.parseLong(wrapper.getRequestParameter("drugId"));
            long userId = ((User) wrapper.getSessionAttribute("user")).getId();
            int amount = Integer.parseInt(wrapper.getRequestParameter("amount"));
            int drugsOrdered = (Integer) wrapper.getSessionAttribute("drugsOrdered");
            double total = (Double)wrapper.getSessionAttribute("total");
            double price = Double.parseDouble(wrapper.getRequestParameter("price"));

            if (isAvailable(drugId, userId)) {
                order.addDrug(drugId, amount);
                wrapper.setSessionAttribute("order", order);// TODO: SHOULD I RENEW IT??!!
                wrapper.setSessionAttribute("drugsOrdered", ++drugsOrdered);
                wrapper.setSessionAttribute("total", total + price * amount);
                wrapper.setRequestAttribute("infoCardMsg", "Success");
            } else {
                wrapper.setRequestAttribute("infoCardMsg", "Drug is not available at the moment");
            }

        } catch (NumberFormatException e) {
            LOGGER.error(e);
            wrapper.setRequestAttribute("infoCardMsg", "Failed to add drug");
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
        return new Path(true, Pages.START_PAGE);
    }

    private boolean isAvailable(long drugId, long userId) throws ServiceException {
        Optional<Drug> drug = drugService.readById(drugId);
        boolean isAvailable = false;
        if (drug.isPresent()) {
            if (drug.get().getPrescription() == 1) {
                System.out.println(drug);
                isAvailable = prescriptionService.checkAvailability(drugId, userId);
            } else {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}
