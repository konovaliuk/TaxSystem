package com.ivahnenko.taxsystem.dao;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ivahnenko.taxsystem.model.User;

/**
 * Test class for {@code UtilDAO}
 */

public class UtilDAOTest {

    @Mock
    private ResultSet resultSet;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUser() throws SQLException {
        int id = 1;
        String name = "someName";
        String surname = "someSurname";
        String patronymic = "somepatronymic";
        String email = "someEmail";
        String username = "someUsername";
        String userType = User.Type.CLIENT.toString();
        int deletetion_mark_false = 0;

        User userExpected = new User.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setUsername(username)
                .setType(User.Type.valueOf(userType))
                .setDeleted(false)
                .build();

        when(resultSet.getInt("u_id")).thenReturn(id);
        when(resultSet.getString("u_name")).thenReturn(name);
        when(resultSet.getString("u_surname")).thenReturn(surname);
        when(resultSet.getString("u_patronymic")).thenReturn(patronymic);
        when(resultSet.getString("u_email")).thenReturn(email);
        when(resultSet.getString("u_username")).thenReturn(username);
        when(resultSet.getString("u_t_code")).thenReturn(userType);
        when(resultSet.getInt("u_deletion_mark")).thenReturn(deletetion_mark_false);

        User userActual = UtilDAO.createUser(resultSet);

        assertEquals(userExpected, userActual);
    }

    @Test
    public void getEncryptedPasswordInputSameShouldReturnTrue() {
        String strBeforeEncryption = "mypassword";
        String strAfterEncryption = "89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8";

        assertEquals(strAfterEncryption, UtilDAO.getEncryptedPassword(strBeforeEncryption));
    }

    @Test
    public void getEncryptedPasswordIputNotSameShouldReturnFalse() {
        String strBeforeEncryption = "mypassword";
        String strAfterEncryption = "89e01536ac207279409d4de1e5253e01f4a1769e696db0d6062ca9b8f56767c8";

        assertEquals(strAfterEncryption, UtilDAO.getEncryptedPassword(strBeforeEncryption));
    }

}
