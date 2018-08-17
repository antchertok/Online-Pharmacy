package by.chertok.util;

import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.util.validator.ParameterValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


public class ValidatorTest {
    private ParameterValidator validator;
    private User user;

    @Before
    public void initUser() {
        validator = new ParameterValidator();

        user = new User(0);
        user.setLogin("Second");
        user.setPassword("qwerty2");
        user.setFirstName("Николай");
        user.setLastName("Богданов");
        user.setMail("second@tut.by");
    }

    @After
    public void clean(){
        validator = null;
        user = null;
    }


    @Test
    public void validateUser(){
        Assert.assertTrue(validator.isUserValid(user));
    }

    @Test
    public void validateFalse(){
        user.setMail("second@tut");
        Assert.assertFalse(validator.isUserValid(user));
    }
}
