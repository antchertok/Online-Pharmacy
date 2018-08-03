package main.java.by.chertok.pharmacy.util.validator;

import main.java.by.chertok.pharmacy.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates parameters given by user during registration
 */
public class ParameterValidator {
    private static ParameterValidator instance = new ParameterValidator();

    private static final Pattern REGEX_LOGIN = Pattern.compile("\\w{5,20}");
    private static final Pattern REGEX_PASSWORD = Pattern.compile("\\w{5,45}");
    private static final Pattern REGEX_EMAIL = Pattern.compile("\\w{5,20}@\\w{3,8}\\.\\w{2,4}");
    private static final Pattern REGEX_NAME = Pattern.compile("[А-Яа-я]{3,20}");
    private static final Pattern REGEX_SURNAME = Pattern.compile("[А-Яа-я]{3,25}");

    private ParameterValidator() {}

    public ParameterValidator getInstance() {
        return instance;
    }

    /**
     * Checks if parameters are valid before insertion a new user into data storage
     *
     * @param user {@link User user} whose parameters are to check
     * @return true if parameters are valid
     */
    public static boolean isUserValid(User user) {
        return isValidateLogin(user.getLogin()) &&
                isValidatePassword(user.getPassword()) &&
                isValidateEmail(user.getMail()) &&
                isValidateName(user.getFirstName()) &&
                isValidateSurname(user.getLastName());
    }

    private static boolean isValidateLogin(String login) {
        return instance.checkString(login, REGEX_LOGIN);
    }

    private static boolean isValidatePassword(String password) {
        return instance.checkString(password, REGEX_PASSWORD);
    }

    private static boolean isValidateEmail(String email) {
        return instance.checkString(email, REGEX_EMAIL);
    }

    private static boolean isValidateName(String name) {
        return instance.checkString(name, REGEX_NAME);
    }

    private static boolean isValidateSurname(String surname) {
        return instance.checkString(surname, REGEX_SURNAME);
    }


    private boolean checkString(String checkingString, Pattern regex) {
        if (checkingString == null || checkingString.isEmpty() || regex == null) {
            return false;
        } else {
            Matcher matcher = regex.matcher(checkingString.trim());
            System.out.println(checkingString);
            return matcher.matches();
        }
    }

}
