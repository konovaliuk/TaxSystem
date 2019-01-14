package com.ivahnenko.taxsystem.controller.util;

import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.ivahnenko.taxsystem.controller.util.InputValidator;

/**
 * Test class for {@code InputValidator}
 */
@RunWith(Parameterized.class)
public class InputValidatorTest {
    private String entityName;
    private String inputName;
    private String inputValue;
    private boolean expectedResult;

    public InputValidatorTest(String inputName, String inputValue, String entityName, Boolean expectedResult) {
        this.entityName = entityName;
        this.inputName = inputName;
        this.inputValue = inputValue;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {"year", "2018", "taxReportForm", true},
                {"year", "201 8", "taxReportForm", false},
                {"year", "201y", "taxReportForm", false},
                {"mainActivityIncome", "213213", "taxReportForm", true},
                {"mainActivityIncome", "213213,00", "taxReportForm", true},
                {"mainActivityIncome", "213213.00", "taxReportForm", true},
                {"mainActivityIncome", "213213.00.00", "taxReportForm", false},
                {"mainActivityIncome", "21321 3", "taxReportForm", false},
                {"mainActivityIncome", "21321y", "taxReportForm", false},
                {"name", "Fictional corporation", "taxpayer", true},
                {"name", "Fictional_corporation", "taxpayer", false},
                {"name", "/Fictional corporation/", "taxpayer", false},
                {"name", "Вымышленная компания", "taxpayer", true},
                {"name", "Вымышленная компания2018", "taxpayer", true},
                {"name", "`Вымышленная компания2018'", "taxpayer", true},
                {"name", "`Вымышленная компания2018'", "taxpayer", true},
                {"registrationNumber", "KO321215", "taxpayer", true},
                {"registrationNumber", "21321215", "taxpayer", true},
                {"registrationNumber", "2132121 5", "taxpayer", false},
                {"registrationNumber", "registration", "taxpayer", false},
                {"name", "2018", "user", false},
                {"name", "Vladimir", "user", true},
                {"name", "Vladimir2018", "user", false},
                {"name", "Vladimir Vlad", "user", true},
                {"name", "Владимир", "user", true},
                {"name", "Владимир Влад", "user", true},
                {"name", "O`connor", "user", true},
                {"name", "O'connor", "user", true},
                {"username", "vladimir2018", "user", true},
                {"username", "vladimir 2018", "user", false},
                {"username", "vladimir_2018", "user", true},
                {"username", "Vladimir_2018", "user", false},
                {"username", "Vladi", "user", false},
                {"password", "Vladi", "user", false},
                {"password", "Vladimir", "user", true},
                {"password", "Vladimir_", "user", false},
                {"password", "Vladimir2018", "user", true},
                {"password", "vladimir/", "user", false},
                {"password", "vladimir'", "user", false},
                {"email", "email*", "user", false},
                {"email", "213213 *", "user", false},
                {"email", "email@.com", "user", true},
                {"email", "#!$%&'*+-/=?^_`{}|~@email.org", "user", true},
                {"email", "Abc\\@def\"@example.com", "user", true},
                {"email", "емейл@.com*", "user", true},
                {"email", "A@b@c@example.com", "user", true},});
    }

    @Test
    public void validateDifferentInputValues() {
        assertEquals(expectedResult, InputValidator.validate(inputName, inputValue, entityName));
    }
}
