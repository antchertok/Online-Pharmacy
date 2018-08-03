package main.java.by.chertok.pharmacy.command.impl.customer;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class RemoveDrugFromCartCommand implements ICommand {

    /**
     * If successful, removes the drug from the current order
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    //TODO CLEAN
    @Override
    public Path execute(Wrapper wrapper) {
        Order order = (Order)wrapper.getSessionAttribute("order");
        double total = (Double)wrapper.getSessionAttribute("total");
        double price = Double.parseDouble(wrapper.getRequestParameter("price"));
        int amount = Integer.parseInt(wrapper.getRequestParameter("amount"));
        Path path = new Path(true, Pages.PROFILE);

        try{
            int drugsOrdered = (Integer)wrapper.getSessionAttribute("drugsOrdered");
            long drugId = Long.parseLong(wrapper.getRequestParameter("drugId"));
            if (order.getDrugs().containsKey(drugId)) {
                order.getDrugs().remove(drugId);
                wrapper.setSessionAttribute("drugsOrdered", --drugsOrdered);
                wrapper.setSessionAttribute("total", total - price * amount);
                wrapper.setSessionAttribute("order", order);
            }
//            return path;
        } catch (NumberFormatException e){
            wrapper.setRequestAttribute("illegalDrugId", "Failed to remove drug");
//            return path;
//            request.getRequestDispatcher("profile.jsp").forward(request,response);
        }
        return path;
    }
}