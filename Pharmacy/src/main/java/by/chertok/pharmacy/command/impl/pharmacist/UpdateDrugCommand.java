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

public class UpdateDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(UpdateDrugCommand.class);
    private static final String FAIL = "Failed to update drug";
    private DrugService drugService;

    public UpdateDrugCommand(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * If successful, updates information about given drug
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            Drug drug = new Drug(Long.parseLong(wrapper.getRequestParameter(AttributeName.DRUG_ID)));

            drug.setName(wrapper.getRequestParameter(AttributeName.NAME));
            drug.setDose(Integer.parseInt(wrapper.getRequestParameter(AttributeName.DOSE)));
            drug.setPrice(Double.parseDouble(wrapper.getRequestParameter(AttributeName.PRICE)));
            drug.setPrescription(Integer.parseInt(wrapper.getRequestParameter(AttributeName.PRESCRIPTION)));
            Path path = new Path();

            if (drugService.update(drug)) {
                path.setUrl(PageStorage.START_PAGE);
                path.setForward(false);
            } else {
                wrapper.setRequestAttribute(AttributeName.INFO_MSG, FAIL);
                path.setUrl(PageStorage.ALTERNATING_DRUGS);
                path.setForward(true);
            }

            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
