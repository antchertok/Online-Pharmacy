package main.java.by.chertok.pharmacy.command.impl.customer;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.AddDrugCommand;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrentOrderCommand implements ICommand {

    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private DrugService drugService;

    public CurrentOrderCommand(DrugService drugService){this.drugService = drugService;}

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
        try{
            Order order = (Order)wrapper.getSessionAttribute("order");
            Map<Drug, Integer> drugs = new HashMap<>();
            Map<Long, Integer> orderDrugs = order.getDrugs();
            Path path = new Path(true, Pages.PROFILE);

            for(Long drugId: orderDrugs.keySet()){
                Optional<Drug> drug = drugService.readById(drugId);
                if(drug.isPresent()){
                    drugs.put(drug.get(), orderDrugs.get(drugId));
                } else {
                    wrapper.setRequestAttribute("errMsg", "Failed to load order");
                    return path;
                }
            }

            wrapper.setRequestAttribute("currentOrder", drugs);
            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
