package by.chertok.pharmacy.command.impl.customer;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.command.impl.pharmacist.AddDrugCommand;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import by.chertok.pharmacy.service.PrescriptionService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class AddToCardCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private static final String SUCCESS = "Success";
    private static final String NOT_AVAILABLE = "Drug is not available at the moment";
    private static final String PRICE_PATTERN = "%.2f";
    private static final String FAILED_TO_ADD = "Failed to add drug";
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
            Order order = (Order) wrapper.getSessionAttribute(AttributeName.ORDER);
            long drugId = Long.parseLong(wrapper.getRequestParameter(AttributeName.DRUG_ID));
            long userId = ((User) wrapper.getSessionAttribute(AttributeName.USER)).getId();
            int amount = Integer.parseInt(wrapper.getRequestParameter(AttributeName.AMOUNT));
            int drugsOrdered = (Integer) wrapper.getSessionAttribute(AttributeName.DRUGS_ORDERED);
            double total = (Double) wrapper.getSessionAttribute(AttributeName.TOTAL);
            double price = Double.parseDouble(wrapper.getRequestParameter(AttributeName.PRICE));

            if (isAvailable(drugId, userId)) {
                if (order.getDrugs().containsKey(drugId)) {
                    order.addDrug(drugId, order.getDrugs().get(drugId) + amount);
                } else {
                    order.addDrug(drugId, amount);
                    wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, ++drugsOrdered);
                }
                wrapper.setSessionAttribute(AttributeName.ORDER, order);
                wrapper.setSessionAttribute(AttributeName.TOTAL,
                        Double.parseDouble(String.format(PRICE_PATTERN, total + price * amount)));
                wrapper.setRequestAttribute(AttributeName.INFO_CART_MSG, SUCCESS);
            } else {
                wrapper.setRequestAttribute(AttributeName.INFO_PRESCRIPTION, NOT_AVAILABLE);
                wrapper.setRequestAttribute(AttributeName.DRUG_ID_FOR_PRESC, drugId);
            }

        } catch (NumberFormatException e) {
            LOGGER.error(e);
            wrapper.setRequestAttribute(AttributeName.INFO_CART_MSG, FAILED_TO_ADD);
        } catch (ServiceException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
        return new Path(true, PageStorage.START_PAGE);
    }

    private boolean isAvailable(long drugId, long userId) throws ServiceException {
        Optional<Drug> drug = drugService.readById(drugId);
        boolean isAvailable = false;
        if (drug.isPresent()) {
            if (drug.get().getPrescription() == 1) {
                isAvailable = prescriptionService.checkAvailability(drugId, userId);
            } else {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}
