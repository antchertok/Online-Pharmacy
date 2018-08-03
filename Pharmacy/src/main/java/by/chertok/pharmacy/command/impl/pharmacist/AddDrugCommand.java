package main.java.by.chertok.pharmacy.command.impl.pharmacist;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class AddDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private DrugService drugService;

    public AddDrugCommand(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * If successful, adds given drug to util, allowing it to be purchased
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        Path path = new Path();
        path.setUrl(Pages.ALTERNATING_DRUGS);

        try {
            Drug drug = new Drug(0);
            drug.setName(wrapper.getRequestParameter("name"));
            drug.setDose(Integer.parseInt(wrapper.getRequestParameter("dose")));
            drug.setPrescription(Integer.parseInt(wrapper.getRequestParameter("prescription")));
            drug.setPrice(Double.parseDouble(wrapper.getRequestParameter("price")));

            if (drugService.create(drug)) {
                wrapper.setRequestAttribute("infoMsg", "Drug added successfully.");
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute("infoMsg", "This drug is already exists.");
                path.setForward(true);
                return path;
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            wrapper.setRequestAttribute("errorMsg", "Invalid parameters");
            path.setForward(true);
            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
