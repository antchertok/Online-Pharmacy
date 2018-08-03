package main.java.by.chertok.pharmacy.command.impl.common.all;


import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class SeekDrugsCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(SeekDrugsCommand.class);
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
        try{
            String name = wrapper.getRequestParameter("name");
//            name = name == null ? "" : name;
            int pageNumber = Integer.parseInt(wrapper.getRequestParameter("pageNumber"));
            int elementsOnPage = Integer.parseInt(wrapper.getRequestParameter("elements"));

            List<Drug> drugList = drugService.readForPage(name, pageNumber, elementsOnPage);
            int amountOfRecords = drugService.getAmountOfRecords(name);

            if (drugList.isEmpty()) {
                wrapper.setRequestAttribute("emptyResultMsg", "Nothing was found");
                wrapper.setSessionAttribute("drugList", null);
            } else {
                wrapper.setSessionAttribute("name", name);
                wrapper.setSessionAttribute("pageNumber", pageNumber);
                wrapper.setSessionAttribute("amountOfRecords", amountOfRecords);
                wrapper.setSessionAttribute("drugList", drugList);
            }

            return new Path(true, Pages.START_PAGE);
        } catch(ServiceException | NumberFormatException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e);
            return new Path(false, Pages.ERROR);
        }

    }
}