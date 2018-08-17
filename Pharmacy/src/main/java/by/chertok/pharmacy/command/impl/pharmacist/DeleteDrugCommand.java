package by.chertok.pharmacy.command.impl.pharmacist;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class DeleteDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteDrugCommand.class);
    private static final String SUCCESS = "Drug deleted successfully";
    private static final String FAIL = "Failed to delete drug";
    private DrugService drugService;

    public DeleteDrugCommand(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * If successful, removes the drug from data storage
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            long drugId = Long.parseLong(wrapper.getRequestParameter(AttributeName.DRUG_ID));
            Path path = new Path();
            path.setUrl(PageStorage.ALTERNATING_DRUGS);

            if (drugService.delete(drugId)) {
                wrapper.setRequestAttribute(AttributeName.INFO_MSG, SUCCESS);
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute(AttributeName.INFO_MSG, FAIL);
                path.setForward(true);
                return path;
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
