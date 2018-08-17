package by.chertok.pharmacy.command.impl.customer;


import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class RemoveDrugFromCartCommand implements ICommand {
    private static final String PRICE_PATTERN = "%.2f";
    private static final String FAIL = "Failed to remove drug";

    /**
     * If successful, removes the drug from the current order
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        Order order = (Order) wrapper.getSessionAttribute(AttributeName.ORDER);
        double total = (Double) wrapper.getSessionAttribute(AttributeName.TOTAL);
        double price = Double.parseDouble(wrapper.getRequestParameter(AttributeName.PRICE));
        int amount = Integer.parseInt(wrapper.getRequestParameter(AttributeName.AMOUNT));
        Path path = new Path(true, PageStorage.ORDER);

        try {
            int drugsOrdered = (Integer) wrapper.getSessionAttribute(AttributeName.DRUGS_ORDERED);
            long drugId = Long.parseLong(wrapper.getRequestParameter(AttributeName.DRUG_ID));

            if (order.getDrugs().containsKey(drugId)) {
                order.getDrugs().remove(drugId);
                wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, --drugsOrdered);
                wrapper.setSessionAttribute(AttributeName.TOTAL,
                        Double.parseDouble(String.format(PRICE_PATTERN, total - price * amount)));
                wrapper.setSessionAttribute(AttributeName.ORDER, order);
            }
        } catch (NumberFormatException e) {
            wrapper.setRequestAttribute(AttributeName.ILLEGAL_DRUG_ID, FAIL);
        }
        return path;
    }
}