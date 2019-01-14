package com.ivahnenko.taxsystem.dao;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.ivahnenko.taxsystem.dao.UtilDAO;

/**
 * Test class for {@code UtilDAO}
 */
@RunWith(Parameterized.class)
public class UtilDAOEncodingPasswordTest {
    private String password;
    private String passwordHash;
    private boolean expected;

    public UtilDAOEncodingPasswordTest(String password, String passwordHash, boolean expected) {
        this.password = password;
        this.passwordHash = passwordHash;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {"password256", "cbd1899da8525137a9255548990090624d1c545a083a48828d85b8cb9c550f43", true},
                {"pAssWord256", "c50d20002a46d7af5b9a90e327cf146bbc591cf9a73ad69e9528f13735ba5f73", true},
                {"123123", "f4ffd35bd0671948f05b5ad32bef5eeb892eae0518aae4646988fc71554e1c09", false},
                {"anotherPassword", "f4ffd35bd0671948f05b5ad32bef5eeb892eae0518aae4646988fc71554e1c09", false},});
    }

    @Test
    public void getEncryptedPasswordTestWithParametersList() throws NoSuchAlgorithmException {
        boolean actual = passwordHash.equals(UtilDAO.getEncryptedPassword(password));
        assertEquals(expected, actual);
    }
}
