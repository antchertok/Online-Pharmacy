package main.java.by.chertok.pharmacy.command.impl.doctor;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.PrescriptionService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class ListPrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ListPrescriptionCommand.class);
    private PrescriptionService prescriptionService;

    public ListPrescriptionCommand(PrescriptionService prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    /**
     * If successful, sets the list of prescriptions addressed to a certain doctor
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            long doctorId = ((User) wrapper.getSessionAttribute("user")).getId();
            List<Prescription> prescriptions = prescriptionService.readByDoctorId(doctorId);

            if (!prescriptions.isEmpty()) {
                wrapper.setRequestAttribute("prescriptions", prescriptions);
            } else {
                wrapper.setRequestAttribute("emptyResultMsg", "Nothing was found");
            }
            return new Path(true, Pages.PRESCRIPTIONS);
        } catch(ServiceException e){
            LOGGER.error(e);
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
