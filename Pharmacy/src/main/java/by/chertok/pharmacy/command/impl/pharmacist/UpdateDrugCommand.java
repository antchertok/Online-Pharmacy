package main.java.by.chertok.pharmacy.command.impl.pharmacist;
;
import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class UpdateDrugCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(UpdateDrugCommand.class);
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
            Drug drug = new Drug(Long.parseLong(wrapper.getRequestParameter("drugId")));

            drug.setName(wrapper.getRequestParameter("name"));
            drug.setDose(Integer.parseInt(wrapper.getRequestParameter("dose")));
            drug.setPrice(Double.parseDouble(wrapper.getRequestParameter("price")));
            drug.setPrescription(Integer.parseInt(wrapper.getRequestParameter("prescription")));
            Path path = new Path();
            path.setUrl(Pages.ALTERNATING_DRUGS);

            if (drugService.update(drug)) {
                wrapper.setRequestAttribute("infoMsg", "Drug updated successfully.");
                path.setForward(false);
            } else {
                wrapper.setRequestAttribute("infoMsg", "Failed to update drug.");
//                request.getRequestDispatcher("alteringDrugPage.jsp").forward(request, response);
                path.setForward(true);
            }

            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
