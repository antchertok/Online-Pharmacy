package by.chertok.pharmacy.tag;

import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

public class DrugListingTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(DrugListingTag.class);
    private List<Drug> list;
    private int elementsOnPage;
    private Integer pageNumber;

    public void setList(List list) {
        this.list = list;
    }

    public void setElementsOnPage(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int doStartTag() throws JspTagException {

        if (list != null) {
            int amountOfRecords = (Integer) pageContext.getSession().getAttribute("amountOfRecords");
            int amountOfPages = (int) Math.ceil(amountOfRecords / (double) elementsOnPage);
            pageContext.setAttribute("amountOfPages", amountOfPages);
            if (pageNumber == null) {
                pageNumber = 1;
            }

            try {
                JspWriter out = pageContext.getOut();
                String disabling;
                User user = (User) pageContext.getSession().getAttribute("user");
                int name_column;
                int price_column;
                int button_column;
                Locale locale = pageContext.getResponse().getLocale();
                ResourceBundle bundle = ResourceBundle.getBundle("locale", locale);

                if (user != null && user.getRole().equals("pharmacist")) {
                    name_column = 3;
                    price_column = 3;
                    button_column = 4;
                } else {
                    name_column = 4;
                    price_column = 4;
                    button_column = 4;
                }

                for (Drug drug : list) {
                    out.write("<div class=\"panel panel-default\">");
                    out.write("<div class=\"panel-body\">");
                    out.write("<div class=\"row\" >");
                    out.write("<div class=\"col-sm-" + name_column + "\" style=\"display: inline-block\">"
                            + String.format("%s, %dмг", drug.getName(), drug.getDose()) + "</div>");
                    out.write("<div class=\"col-sm-" + price_column + "\" style=\"display: inline-block\">");
                    out.write("<label>" + String.format(locale, "%.2f "
                            + bundle.getString("label.currency"), drug.getPrice()) + "</label>");
                    out.write("</div>");

                    //Add drug
                    out.write("<div class=\"col-sm-" + button_column + "\" style=\"display: inline-block\" "
                            + "id=\"to-card-button\">");
                    out.write("<form name=\"AddToCardForm\" action=\"/controller\">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"add-to-card\"/>");
                    out.write("<input type=\"hidden\" name=\"price\" value=\"" + drug.getPrice() + "\"/>");
                    out.write("<input type=\"hidden\" name=\"drugId\" value=\"" + drug.getId() + "\"/>");
                    disabling = user == null
                            ? "disabled title=\"Please enter the system\"/>"
                            : "/>";
                    out.write("<input type=\"number\" id=\"amount\" class=\"amount-of-drug\" "
                            + "required min=\"1\" name=\"amount\" value=\"\" placeholder=\""
                            + bundle.getString("label.amount") + "\"\n" +
                            disabling);
                    out.write("<input type=\"submit\" name=\"submit\" class=\"submit-amount\""
                            + " value=\"" + bundle.getString("button.add-to-cart") + "\"\n" +
                            disabling);
                    out.write("</form></div>");

                    //Alternate drug
                    if (user != null && user.getRole().equals("pharmacist")) {
                        out.write("<div class=\"col-sm-2\" style=\"display: inline-block\">");
                        out.write("<form name=\"AlternateDrug\" action=\"/controller\">");
                        out.write("<input type=\"hidden\" name=\"command\" value=\"to-alternating-drug\"/>");
                        out.write("<input type=\"hidden\" name=\"drugId\" value=\"" + drug.getId() + "\"/>");
                        out.write("<input type=\"hidden\" name=\"name\" value=\"" + drug.getName() + "\"/>");
                        out.write("<input type=\"hidden\" name=\"price\" value=\"" + drug.getPrice() + "\"/>");
                        out.write("<input type=\"hidden\" name=\"prescription\" value=\"" + drug.getPrescription()
                                + "\"/>");
                        out.write("<input type=\"hidden\" name=\"dose\" value=\"" + drug.getDose() + "\"/>");
                        out.write("<input type=\"submit\" name=\"submit\" value=\""
                                + bundle.getString("locale.alternate") + "\"/>");
                        out.write("</form></div>");
                    }

                    out.write("</div></div></div>");
                }

                //Buttons
                out.write("<div class=\"back-forward\">");
                disabling = pageNumber == 1 ? "disabled/>" : "/>";

                out.write("<form name=\"Back\" class=\"page-control\" action=\"/controller\">");
                out.write("<input type=\"hidden\" name=\"command\" value=\"seek-drug\"/>");
                out.write("<input type=\"hidden\" name=\"name\" value=\""
                        + pageContext.getSession().getAttribute("name") + "\"/>");
                out.write("<input type=\"hidden\" name=\"pageNumber\" value=\"" + (pageNumber - 1) + "\"/>");
                out.write("<input type=\"hidden\" name=\"elements\" value=\"" + elementsOnPage + "\"/>");
                out.write("<input type=\"submit\" name=\"back\" value=\""
                        + bundle.getString("button.previous-page") + "\"" + disabling);
                out.write("</form>");
                out.write("<span><label>&nbsp" + pageNumber + "&nbsp</label></span>");
                disabling = pageNumber == amountOfPages ? "disabled/>" : "/>";

                out.write("<form name=\"Forward\" class=\"page-control\" action=\"/controller\">");
                out.write("<input type=\"hidden\" name=\"command\" value=\"seek-drug\"/>");
                out.write("<input type=\"hidden\" name=\"name\" value=\""
                        + pageContext.getSession().getAttribute("name") + "\"/>");
                out.write("<input type=\"hidden\" name=\"pageNumber\" value=\"" + (pageNumber + 1) + "\"/>");
                out.write("<input type=\"hidden\" name=\"elements\" value=\"" + elementsOnPage + "\"/>");
                out.write("<input type=\"submit\" name=\"back\" value=\""
                        + bundle.getString("button.next-page") + "\"" + disabling);
                out.write("</form>");
                out.write("</div>");
            } catch (IOException e) {
                LOGGER.error(e);
                throw new JspTagException(e.getMessage());
            }
        }

        return SKIP_BODY;
    }

}