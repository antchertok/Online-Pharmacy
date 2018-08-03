package main.java.by.chertok.pharmacy.util.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class storing parameters and attributes from request and session for
 * their transmission to logic classes
 */
public class Wrapper {
    Map<String, Object> attributes = new HashMap();
    Map<String, String> parameters = new HashMap();
    Map<String, Object> sessionParameters = new HashMap();

    /**
     * Takes all attributes and parameters from session and request
     * @param request received {@link HttpServletRequest request}
     */
    public void extract(HttpServletRequest request){
        Enumeration attributes = request.getAttributeNames();
        String name;

        while(attributes.hasMoreElements()){
            name = (String)attributes.nextElement();
            this.attributes.put(name,request.getAttribute(name));
        }

        attributes = request.getParameterNames();
        while(attributes.hasMoreElements()){
            name = (String)attributes.nextElement();
            parameters.put(name, request.getParameter(name));
        }

        HttpSession session = request.getSession();
        attributes = session.getAttributeNames();

        while(attributes.hasMoreElements()){
            name = (String)attributes.nextElement();
            sessionParameters.put(name,session.getAttribute(name));
        }
    }

    /**
     * Sets or updates an attribute from request
     *
     * @param name name of attribute
     * @param value new value for the attribute
     */
    public void setRequestAttribute(String name, Object value) {
//        if(attributes.containsKey(name)){
////            attributes.put(name, value);
////       }
        attributes.put(name, value);
    }

    /**
     * Returns value of an attribute from request
     *
     * @param name name of the attribute
     * @return value of this attribute
     */
    public Object getRequestAttribute(String name){
//        if(attributes.containsKey(name)){
//            return attributes.get(name);
//        }
////        FIX IT!!!
//        throw new IllegalArgumentException();
        return attributes.get(name);
    }

    /**
     * Returns value of a certain parameter from request
     *
     * @param name name of the parameter
     * @return value of this parameter
     */
    public String getRequestParameter(String name){
//        if(parameters.containsKey(name)){
//            return parameters.get(name);
//        }
//        //        TODO FIX IT!!!
//        throw new IllegalArgumentException();
        return parameters.get(name);
    }

    /**
     * Sets an attribute from session
     *
     * @param name name of the attribute
     * @param value new value for this attribute
     */
    public void setSessionAttribute(String name, Object value){
//        if(sessionParameters.containsKey(name)){
//            sessionParameters.put(name, value);
//        }
        sessionParameters.put(name, value);
    }

    /**
     * Returns value of an attribute from session
     *
     * @param name name of the attribute
     * @return value of this attribute
     */
    public Object getSessionAttribute(String name){
//        if(sessionParameters.containsKey(name)){
//            return sessionParameters.get(name);
//        }
////        FIX IT!!!
//        throw new IllegalArgumentException();
        return sessionParameters.get(name);
    }

    /**
     * Transmits all attributes back to request and session
     *
     * @param request
     */
    public void updateRequest(HttpServletRequest request){
        for(String name: attributes.keySet()){
            request.setAttribute(name,attributes.get(name));
        }

        HttpSession session = request.getSession();

        for(String name: sessionParameters.keySet()){
            session.setAttribute(name, sessionParameters.get(name));
        }
    }
}
