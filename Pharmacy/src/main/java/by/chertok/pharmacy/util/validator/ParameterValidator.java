package by.chertok.pharmacy.util.validator;

import by.chertok.pharmacy.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates parameters given by user during registration
 */
public class ParameterValidator {
    private static final Pattern REGEX_LOGIN = Pattern.compile("\\w{5,20}");
    private static final Pattern REGEX_PASSWORD = Pattern.compile("\\w{5,45}");
    private static final Pattern REGEX_EMAIL = Pattern.compile("\\w{5,20}@\\w{3,8}\\.\\w{2,4}");
    private static final Pattern REGEX_NAME = Pattern.compile("[А-Яа-я]{3,20}");
    private static final Pattern REGEX_SURNAME = Pattern.compile("[А-Яа-я]{3,25}");

    /**
     * Checks if parameters are valid before insertion a new user into data storage
     *
     * @param user {@link User user} whose parameters are to check
     * @return true if parameters are valid
     */
    public boolean isUserValid(User user) {
        return isLoginValid(user.getLogin())
                && isPasswordValid(user.getPassword())
                && isEmailValid(user.getMail())
                && isFirstNameValid(user.getFirstName())
                && isLastNameValid(user.getLastName());
    }

    private boolean isLoginValid(String login) {
        return checkString(login, REGEX_LOGIN);
    }

    private boolean isPasswordValid(String password) {
        return checkString(password, REGEX_PASSWORD);
    }

    private boolean isEmailValid(String email) {
        return checkString(email, REGEX_EMAIL);
    }

    private boolean isFirstNameValid(String name) {
        return checkString(name, REGEX_NAME);
    }

    private boolean isLastNameValid(String surname) {
        return checkString(surname, REGEX_SURNAME);
    }


    private boolean checkString(String checkingString, Pattern regex) {
        if (checkingString == null || checkingString.isEmpty() || regex == null) {
            return false;
        } else {
            Matcher matcher = regex.matcher(checkingString.trim());
            return matcher.matches();
        }
    }

}
