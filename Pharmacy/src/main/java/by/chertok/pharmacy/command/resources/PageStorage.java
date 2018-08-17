package by.chertok.pharmacy.command.resources;

public interface PageStorage {
    String ERROR = "jsp/error.jsp";
    String START_PAGE = "jsp/index.jsp";
    String ALTERNATING_DRUGS = "jsp/drug-alternate.jsp";
    String PROFILE = "jsp/profile.jsp";
    String LOGIN = "jsp/login.jsp";
    String PRESCRIPTIONS = "jsp/prescriptions.jsp";
    String ORDER = "/controller?command=current-order";
    String LIST_PRESCRIPTIONS = "/controller?command=list-prescriptions";
}
