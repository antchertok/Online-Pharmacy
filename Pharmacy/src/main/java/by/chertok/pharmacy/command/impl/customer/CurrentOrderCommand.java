package by.chertok.pharmacy.command.impl.customer;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.command.impl.pharmacist.AddDrugCommand;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrentOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private static final String FAIL = "Failed to load order";
    private DrugService drugService;

    public CurrentOrderCommand(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * If successful, sets the list of drugs from the current order into {@link Wrapper wrapper}
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
            Map<Drug, Integer> drugs = new HashMap<>();
            Map<Long, Integer> orderDrugs = order.getDrugs();
            Path path = new Path(true, PageStorage.PROFILE);

            for (Long drugId : orderDrugs.keySet()) {
                Optional<Drug> drug = drugService.readById(drugId);
                if (drug.isPresent()) {
                    drugs.put(drug.get(), orderDrugs.get(drugId));
                } else {
                    wrapper.setRequestAttribute(AttributeName.ERROR_MSG, FAIL);
                    return path;
                }
            }

            wrapper.setRequestAttribute(AttributeName.CURRENT_ORDER, drugs);
            return path;
        } catch (ServiceException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
