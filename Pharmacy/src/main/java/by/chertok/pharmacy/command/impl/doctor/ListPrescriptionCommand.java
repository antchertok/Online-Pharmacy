package by.chertok.pharmacy.command.impl.doctor;

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

import java.util.List;

public class ListPrescriptionCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ListPrescriptionCommand.class);
    private static final String FAIL = "There are no any prescriptions at the moment";
    private PrescriptionService prescriptionService;

    public ListPrescriptionCommand(PrescriptionService prescriptionService) {
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
        try {
            long doctorId = ((User) wrapper.getSessionAttribute(AttributeName.USER)).getId();
            List<Prescription> prescriptions = prescriptionService.readByDoctorId(doctorId);
            Path path = new Path();
            path.setForward(true);

            if (!prescriptions.isEmpty()) {
                wrapper.setRequestAttribute(AttributeName.PRESCRIPTIONS, prescriptions);
                path.setUrl(PageStorage.PRESCRIPTIONS);
            } else {
                wrapper.setRequestAttribute(AttributeName.EMPTY_RESULT_MSG, FAIL);
                path.setUrl(PageStorage.PROFILE);
            }
            return path;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
