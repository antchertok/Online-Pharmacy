package by.chertok.pharmacy.command.impl.common.all;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class SeekDrugsCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(SeekDrugsCommand.class);
    private static final String NOTHING_WAS_FOUND = "Nothing was found";
    private DrugService drugService;

    public SeekDrugsCommand(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * Sets a list of drugs with names containing the given string
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            String name = wrapper.getRequestParameter(AttributeName.NAME);
            int pageNumber = Integer.parseInt(wrapper.getRequestParameter(AttributeName.PAGE_NUMBER));
            int elementsOnPage = Integer.parseInt(wrapper.getRequestParameter(AttributeName.ELEMENTS));

            List<Drug> drugList = drugService.readForPage(name, pageNumber, elementsOnPage);
            int amountOfRecords = drugService.getAmountOfRecords(name);

            if (drugList.isEmpty()) {
                wrapper.setRequestAttribute(AttributeName.EMPTY_RESULT_MSG, NOTHING_WAS_FOUND);
            } else {
                wrapper.setSessionAttribute(AttributeName.NAME, name);
                wrapper.setSessionAttribute(AttributeName.PAGE_NUMBER, pageNumber);
                wrapper.setSessionAttribute(AttributeName.AMOUNT_OF_RECORDS, amountOfRecords);
                wrapper.setSessionAttribute("amountOfPages", (int) Math.ceil(amountOfRecords / (double) elementsOnPage));
                wrapper.setSessionAttribute(AttributeName.DRUG_LIST, drugList);
            }

            return new Path(true, PageStorage.START_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e);
            return new Path(false, PageStorage.ERROR);
        }

    }
}