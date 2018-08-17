package by.chertok.pharmacy.command.impl.doctor;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Prescription;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.PrescriptionService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class ApprovePrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ApprovePrescriptionCommand.class);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Failed";
    private PrescriptionService prescriptionService;

    public ApprovePrescriptionCommand(PrescriptionService prescriptionService) {
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
        try {
            long prescriptionId = Long.parseLong(wrapper.getRequestParameter(AttributeName.PRESCRIPTION_ID));
            Optional<Prescription> prescription = prescriptionService.readById(prescriptionId);

            if (prescription.isPresent()) {
                Prescription approvedPrescription = prescription.get();
                approvedPrescription.setApproved(true);
                prescriptionService.update(approvedPrescription);
                wrapper.setRequestAttribute(AttributeName.PRESC_CONTROL, SUCCESS);
            } else {
                wrapper.setRequestAttribute(AttributeName.PRESC_CONTROL, FAIL);
            }

            return new Path(false, PageStorage.LIST_PRESCRIPTIONS);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
