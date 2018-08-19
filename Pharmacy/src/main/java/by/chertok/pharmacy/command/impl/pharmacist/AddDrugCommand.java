package by.chertok.pharmacy.command.impl.pharmacist;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class AddDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddDrugCommand.class);
    private static final String SUCCESS = "Drug added successfully";
    private static final String FAIL = "This drug is already exists";
    private static final String INVALID_PARAMETERS = "Invalid parameters";
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
        path.setUrl(PageStorage.ALTERNATING_DRUGS);

        try {
            Drug drug = new Drug(0);
            drug.setName(wrapper.getRequestParameter(AttributeName.NAME));
            drug.setDose(Integer.parseInt(wrapper.getRequestParameter(AttributeName.DOSE)));
            drug.setPrescription(Integer.parseInt(wrapper.getRequestParameter(AttributeName.PRESCRIPTION)));
            drug.setPrice(Double.parseDouble(wrapper.getRequestParameter(AttributeName.PRICE)));

            if (drugService.create(drug)) {
                wrapper.setRequestAttribute(AttributeName.INFO_MSG, SUCCESS);
                path.setUrl(PageStorage.START_PAGE);
                path.setForward(false);
            } else {
                wrapper.setRequestAttribute(AttributeName.INFO_MSG, FAIL);
                path.setForward(true);
            }
            return path;
        } catch (NumberFormatException e) {
            wrapper.setRequestAttribute(AttributeName.INFO_MSG, INVALID_PARAMETERS);
            path.setForward(true);
            return path;
        } catch (ServiceException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
