package by.chertok.pharmacy.command.impl.pharmacist;

import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToAddDrugPage implements ICommand {

    /**
     * Sends to drugs alternating page to add a new drug
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        return new Path(true, PageStorage.ALTERNATING_DRUGS);
    }
}
