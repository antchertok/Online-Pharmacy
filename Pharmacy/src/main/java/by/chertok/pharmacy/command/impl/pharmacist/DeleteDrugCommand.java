package main.java.by.chertok.pharmacy.command.impl.pharmacist;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class DeleteDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteDrugCommand.class);
    private DrugService drugService;

    public DeleteDrugCommand(DrugService drugService){this.drugService = drugService;}

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
        try{
            long drugId = Long.parseLong(wrapper.getRequestParameter("drugId"));
            Path path = new Path();
            path.setUrl(Pages.ALTERNATING_DRUGS);

            if (drugService.delete(drugId)) {
                wrapper.setRequestAttribute("infoMsg", "Drug deleted successfully.");
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute("infoMsg", "Failed to delete drug.");
                path.setForward(true);
                return path;
            }
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
