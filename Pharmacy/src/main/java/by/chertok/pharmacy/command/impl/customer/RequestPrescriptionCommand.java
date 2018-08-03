package main.java.by.chertok.pharmacy.command.impl.customer;//package main.java.by.anton.apteka.command.impl.oldimpl;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.service.PrescriptionService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class RequestPrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(RequestPrescriptionCommand.class);
    private PrescriptionService prescriptionService;

    public RequestPrescriptionCommand(PrescriptionService prescriptionService,
                                      DrugService drugService){
        this.prescriptionService = prescriptionService;
    }
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            Prescription prescription = new Prescription(0);
            prescription.setCustomerId(((User)wrapper.getSessionAttribute("user")).getId());
            prescription.setDrugId(Long.parseLong(wrapper.getRequestParameter("drugId")));
            prescription.setValidUntil(LocalDateTime.now());
            prescription.setDoctorId(((User)wrapper.getSessionAttribute("user")).getDoctorId());

            if(prescriptionService.create(prescription)){
                wrapper.setRequestAttribute("prescInfoMsg", "Success");
            } else {
                wrapper.setRequestAttribute("prescInfoMsg", "Failed");
            }

            return new Path(false, Pages.START_PAGE);
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
