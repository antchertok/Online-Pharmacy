package by.chertok.pharmacy.command.impl.customer;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Prescription;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.PrescriptionService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class RequestPrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(RequestPrescriptionCommand.class);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Failed";
    private PrescriptionService prescriptionService;

    public RequestPrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Sends new prescription to doctor for approving
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            Prescription prescription = new Prescription(0);
            prescription.setCustomerId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getId());
            prescription.setDrugId(Long.parseLong(wrapper.getRequestParameter(AttributeName.DRUG_ID)));
            prescription.setValidUntil(LocalDateTime.now());
            prescription.setDoctorId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getDoctorId());

            if (prescriptionService.create(prescription)) {
                wrapper.setRequestAttribute(AttributeName.PRESC_INFO_MSG, SUCCESS);
            } else {
                wrapper.setRequestAttribute(AttributeName.PRESC_INFO_MSG, FAIL);
            }

            return new Path(false, PageStorage.START_PAGE);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
