package main.java.by.chertok.pharmacy.command.impl.doctor;//package main.java.by.anton.apteka.command.impl.oldimpl;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.PrescriptionService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class ApprovePrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ApprovePrescriptionCommand.class);
    private PrescriptionService prescriptionService;

    public ApprovePrescriptionCommand(PrescriptionService prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    /**
     * If successful, marks a certain prescription as approved
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            long prescriptionId = Long.parseLong(wrapper.getRequestParameter("prescriptionId"));
            Optional<Prescription> prescription = prescriptionService.readById(prescriptionId);

            if(prescription.isPresent()){
                Prescription approvedPrescription = prescription.get();
                approvedPrescription.setApproved(true);
                prescriptionService.update(approvedPrescription);
                wrapper.setRequestAttribute("prescriptionControl", "Success");
            } else {
                wrapper.setRequestAttribute("prescriptionControl", "Failure");
            }

            return new Path(false, Pages.PRESCRIPTIONS);
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
